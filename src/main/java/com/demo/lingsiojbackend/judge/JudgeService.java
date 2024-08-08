package com.demo.lingsiojbackend.judge;

public interface JudgeService {


    /**
     * 执行代码
     * @param questionSubmitId 题目提交id
     */
    void doJudge(Integer questionSubmitId);
}
