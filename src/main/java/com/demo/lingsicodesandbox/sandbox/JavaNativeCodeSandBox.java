package com.demo.lingsicodesandbox.sandbox;

import cn.hutool.core.io.FileUtil;
import cn.hutool.dfa.WordTree;
import com.demo.lingsicodesandbox.constant.JudgeInfoEnum;
import com.demo.lingsicodesandbox.entity.ExecuteCodeRequest;
import com.demo.lingsicodesandbox.entity.ExecuteCodeResponse;
import com.demo.lingsicodesandbox.entity.ExecuteMessage;
import com.demo.lingsicodesandbox.entity.JudgeInfo;
import com.demo.lingsicodesandbox.utils.RunProcessUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JavaNativeCodeSandBox implements CodeSandBox {

    private static final String GLOBAL_DIR_NAME = "tempCode";

    private static final String GLOBAL_JAVA_FILE_NAME = "Main.java";

    private static final Long TIME_OUT = 10000L;

    private static final List<String> BLOCKED_COMMANDS = List.of("rm", "rmdir", "del", "mv", "cp", "mvn",
            "gradle", "npm", "yarn", "cnpm", "wget", "curl", "git", "svn", "docker", "file", "File", "files", "exec");

    private static final WordTree WORD_TREE = new WordTree();

    static {
        WORD_TREE.addWords(BLOCKED_COMMANDS);
    }

    // 超时标志
    public volatile static boolean TIME_OUT_FLAG = false;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        String code = executeCodeRequest.getCode();
        List<String> inputList = executeCodeRequest.getInput();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        JudgeInfo judgeInfo = new JudgeInfo();

        // 黑名单检测
        if (WORD_TREE.isMatch(code)) {
            executeCodeResponse.setStatus(2);
            executeCodeResponse.setMessage("代码中包含非法命令");
            judgeInfo.setMessage(JudgeInfoEnum.DANGEROUS_ERROR.getValue());
            executeCodeResponse.setJudgeInfo(judgeInfo);
            return executeCodeResponse;
        }

        String globalPath = System.getProperty("user.dir");
        String codeDirPath = globalPath + File.separator + GLOBAL_DIR_NAME;
        if (!FileUtil.exist(codeDirPath)) {
            FileUtil.mkdir(codeDirPath);
        }
        String codeFilePath = codeDirPath + File.separator + UUID.randomUUID() + File.separator + GLOBAL_JAVA_FILE_NAME;
        File codeFile = FileUtil.writeString(code, codeFilePath, StandardCharsets.UTF_8);
        // 编译
        String compileCommand = String.format("javac -encoding utf-8 %s", codeFile.getAbsolutePath());
        System.out.println(compileCommand);
        try {
            Process process = Runtime.getRuntime().exec(compileCommand);
            ExecuteMessage executeMessage = RunProcessUtil.runProcess(process, "编译");
            if (executeMessage.getExitValue() != 0) {
                executeCodeResponse.setStatus(2);
                executeCodeResponse.setMessage(executeMessage.getMessage());
                judgeInfo.setMessage(JudgeInfoEnum.COMPILE_ERROR.getValue());
                judgeInfo.setTime(executeMessage.getTime());
                executeCodeResponse.setJudgeInfo(judgeInfo);
                return executeCodeResponse;
            }
        } catch (Exception e) {
            return getErrorExecuteCodeResponse(e);
        }

        // 执行
        executeCodeResponse.setStatus(2);
        List<String> outputList = new ArrayList<>();
        long maxTime = 0L;
        for (String input : inputList) {
            // 加入内存限制
            String executeCommand = String.format("java -Xmx256m -Dfile.encoding=utf-8 -cp %s Main %s",
                    codeFile.getParent(), input);
            try {
                Process process = Runtime.getRuntime().exec(executeCommand);
                // 超时处理
                new Thread(() -> {
                    try {
                        Thread.sleep(TIME_OUT);
                        if (process.isAlive()) {
                            process.destroy();
                            TIME_OUT_FLAG = true;
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                // 判断是否超时
                if (TIME_OUT_FLAG) {
                    executeCodeResponse.setStatus(2);
                    executeCodeResponse.setMessage("执行超时");
                    judgeInfo.setMessage(JudgeInfoEnum.TIME_LIMIT_EXCEEDED.getValue());
                    executeCodeResponse.setJudgeInfo(judgeInfo);
                    return executeCodeResponse;
                }

                ExecuteMessage executeMessage = RunProcessUtil.runProcess(process, "执行");
                if (executeMessage.getExitValue() == 0) {
                    outputList.add(executeMessage.getMessage());
                } else {
                    executeCodeResponse.setMessage(executeMessage.getMessage());
                    judgeInfo.setMessage(JudgeInfoEnum.RUNTIME_ERROR.getValue());
                }
                Long time = executeMessage.getTime();
                if (time != null) {
                    maxTime = Math.max(maxTime, time);
                }
            } catch (Exception e) {
                return getErrorExecuteCodeResponse(e);
            }
        }
        if (outputList.size() == inputList.size()) {
            executeCodeResponse.setStatus(3);
            judgeInfo.setMessage(JudgeInfoEnum.WRONG_ANSWER.getValue());
        }
        executeCodeResponse.setOutput(outputList);
        judgeInfo.setTime(maxTime);
        judgeInfo.setMemory(0L);
        judgeInfo.setMessage(JudgeInfoEnum.ACCEPTED.getValue());
        executeCodeResponse.setJudgeInfo(judgeInfo);


        // 清理文件
        if (FileUtil.exist(codeFile.getParent())) {
            FileUtil.del(codeFile.getParent());
        }
        return executeCodeResponse;
    }

    private ExecuteCodeResponse getErrorExecuteCodeResponse(Throwable e) {
        return ExecuteCodeResponse.builder()
                .output(new ArrayList<>())
                .message(e.getMessage())
                .status(2)
                .build();
    }
}
