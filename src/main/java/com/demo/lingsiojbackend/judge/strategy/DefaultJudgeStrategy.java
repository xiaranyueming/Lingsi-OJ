package com.demo.lingsiojbackend.judge.strategy;

import cn.hutool.json.JSONUtil;
import com.demo.lingsiojbackend.constant.JudgeInfoEnum;
import com.demo.lingsiojbackend.entity.questionsubmit.JudgeInfo;

public class DefaultJudgeStrategy implements JudgeStrategy {


    /**
     * 执行判题
     * @param judgeContext 判题上下文
     * @return 判题结果
     */
    @Override
    public JudgeInfo doJudge(JudgeContext judgeContext) {
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setTime(judgeContext.getJudgeInfo().getTime());
        judgeInfo.setMemory(judgeContext.getJudgeInfo().getMemory());
        JudgeInfoEnum judgeInfoEnum = JudgeInfoEnum.ACCEPTED;
        // 判断输入输出是否一一对应
        if (judgeContext.getInputList().size() != judgeContext.getOutputList().size()) {
            judgeInfoEnum = JudgeInfoEnum.WRONG_ANSWER;
            judgeInfo.setMessage(judgeInfoEnum.getValue());
            return judgeInfo;
        }
        for (int i = 0; i < judgeContext.getOutputList().size(); i++) {
            if (!judgeContext.getOutputList().get(i).equals(judgeContext.getJudgeCase().get(i).getOutput())) {
                judgeInfoEnum = JudgeInfoEnum.WRONG_ANSWER;
                judgeInfo.setMessage(judgeInfoEnum.getValue());
                return judgeInfo;
            }
        }
        // 判断是否超时
        if (judgeContext.getJudgeInfo().getTime() > judgeContext.getJudgeConfig().getTimeLimit()) {
            judgeInfoEnum = JudgeInfoEnum.TIME_LIMIT_EXCEEDED;
            judgeInfo.setMessage(judgeInfoEnum.getValue());
            return judgeInfo;
        }
        // 判断是否超内存
        if (judgeContext.getJudgeInfo().getMemory() > judgeContext.getJudgeConfig().getMemoryLimit()) {
            judgeInfoEnum = JudgeInfoEnum.MEMORY_LIMIT_EXCEEDED;
            judgeInfo.setMessage(judgeInfoEnum.getValue());
            return judgeInfo;
        }
        return judgeInfo;
    }
}
