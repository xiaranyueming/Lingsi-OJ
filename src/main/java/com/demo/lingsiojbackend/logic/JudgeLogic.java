package com.demo.lingsiojbackend.logic;

import com.demo.lingsiojbackend.entity.questionsubmit.AddQuestionSubmitParam;
import com.demo.lingsiojbackend.judge.JudgeService;
import com.demo.lingsiojbackend.service.QuestionSubmitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class JudgeLogic {

    private final QuestionSubmitService questionSubmitService;

    private final JudgeService judgeService;


    /**
     * 添加题目提交
     * @param addQuestionSubmitParam 添加题目提交参数
     */
    public void addQuestionSubmit(AddQuestionSubmitParam addQuestionSubmitParam) {
        Integer questionSubmitId = questionSubmitService.addQuestionSubmit(addQuestionSubmitParam);
        judgeService.doJudge(questionSubmitId);
    }
}
