package com.demo.lingsiojbackend.judge.strategy;

import com.demo.lingsiojbackend.entity.questionsubmit.JudgeInfo;

public interface JudgeStrategy {

    JudgeInfo doJudge(JudgeContext judgeContext);
}
