package com.demo.lingsiojbackend.judge.codesandbox.impl;

import com.demo.lingsiojbackend.entity.questionsubmit.JudgeInfo;
import com.demo.lingsiojbackend.judge.codesandbox.CodeSandBox;
import com.demo.lingsiojbackend.judge.codesandbox.entity.ExecuteCodeRequest;
import com.demo.lingsiojbackend.judge.codesandbox.entity.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ExampleCodeSandBox implements CodeSandBox {


    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMemory(500L);
        judgeInfo.setTime(500L);
        ExecuteCodeResponse executeCodeResponse = ExecuteCodeResponse.builder()
                .output(List.of("5"))
                .judgeInfo(judgeInfo)
                .build();
        log.info("示例代码沙箱");
        return executeCodeResponse;
    }
}
