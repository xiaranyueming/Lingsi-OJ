package com.demo.lingsiojbackend.controller;

import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import com.demo.lingsiojbackend.entity.queation.QuestionPage;
import com.demo.lingsiojbackend.entity.questionsubmit.AddQuestionSubmitParam;
import com.demo.lingsiojbackend.entity.questionsubmit.QuestionSubmitPage;
import com.demo.lingsiojbackend.entity.vo.QuestionSubmitVO;
import com.demo.lingsiojbackend.service.QuestionSubmitService;
import com.demo.lingsiojbackend.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/submit")
@RequiredArgsConstructor
@Tag(name = "题目提交管理")
public class QuestionSubmitController {

    private final QuestionSubmitService questionSubmitService;


    /**
     * 分页获取题目提交列表
     *
     * @param questionSubmitPage 分页参数
     * @return 题目提交列表
     */
    @PostMapping("/list")
    @Operation(summary = "分页获取题目提交列表")
    public Result getQuestionSubmitListByPage(@RequestBody QuestionSubmitPage questionSubmitPage) {
        if (questionSubmitPage.getPageSize() == null || questionSubmitPage.getPageNum() <= 0 || questionSubmitPage.getPageSize() <= 0) {
            return Result.fail(ErrorCodeEnum.PARAM_ERROR);
        }
        List<QuestionSubmitVO> list = questionSubmitService.getQuestionSubmitListByPage(questionSubmitPage);
        return Result.success(list);
    }


    /**
     * 提交题目
     * @param addQuestionSubmitParam 提交题目参数
     * @return 提交结果
     */
    @PostMapping("/question")
    @Operation(summary = "提交题目")
    public Result submitQuestion(@RequestBody AddQuestionSubmitParam addQuestionSubmitParam) {
        if (addQuestionSubmitParam.getQuestionId() == null || addQuestionSubmitParam.getUserId() == null
            || StringUtils.isBlank(addQuestionSubmitParam.getCode()) || StringUtils.isBlank(addQuestionSubmitParam.getLanguage())) {
            return Result.fail(ErrorCodeEnum.PARAM_ERROR);
        }
        questionSubmitService.addQuestionSubmit(addQuestionSubmitParam);
        return Result.success();
    }
}
