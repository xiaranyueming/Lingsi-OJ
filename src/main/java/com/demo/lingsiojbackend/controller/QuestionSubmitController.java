package com.demo.lingsiojbackend.controller;

import com.demo.lingsiojbackend.annotation.Auth;
import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import com.demo.lingsiojbackend.constant.LanguageEnum;
import com.demo.lingsiojbackend.entity.questionsubmit.AddQuestionSubmitParam;
import com.demo.lingsiojbackend.entity.questionsubmit.QuestionSubmitDetail;
import com.demo.lingsiojbackend.entity.questionsubmit.QuestionSubmitPage;
import com.demo.lingsiojbackend.entity.vo.QuestionSubmitVO;
import com.demo.lingsiojbackend.logic.JudgeLogic;
import com.demo.lingsiojbackend.service.QuestionSubmitService;
import com.demo.lingsiojbackend.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submit")
@RequiredArgsConstructor
@Tag(name = "题目提交管理")
@Slf4j
public class QuestionSubmitController {

    private final QuestionSubmitService questionSubmitService;

    private final JudgeLogic judgeLogic;


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
        judgeLogic.addQuestionSubmit(addQuestionSubmitParam);
        return Result.success();
    }


    /**
     * 删除题目提交
     * @param id 题目提交id
     * @return 删除结果
     */
    @DeleteMapping("/question/{id}")
    @Operation(summary = "删除题目提交")
    @Auth
    public Result deleteQuestionSubmit(@PathVariable("id") Integer id) {
        if (id == null) {
            return Result.fail(ErrorCodeEnum.PARAM_ERROR);
        }
        questionSubmitService.deleteQuestionSubmit(id);
        return Result.success();
    }


    /**
     * 获取语言列表
     * @return 语言列表
     */
    @GetMapping("/language")
    @Operation(summary = "获取语言列表")
    public Result getLanguageList() {
        return Result.success(LanguageEnum.getValues());
    }


    /**
     * 获取题目提交详情
     * @param id 题目提交id
     * @return 题目提交详情
     */
    @GetMapping("/question/{id}")
    @Operation(summary = "获取题目提交详情")
    public Result getQuestionSubmitDetail(@PathVariable("id") Integer id) {
        if (id == null) {
            return Result.fail(ErrorCodeEnum.PARAM_ERROR);
        }
        QuestionSubmitDetail questionSubmitDetail = questionSubmitService.getQuestionSubmitDetail(id);
        return Result.success(questionSubmitDetail);
    }

}
