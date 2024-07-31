package com.demo.lingsiojbackend.controller;

import com.demo.lingsiojbackend.annotation.Auth;
import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import com.demo.lingsiojbackend.entity.dto.PageDTO;
import com.demo.lingsiojbackend.entity.queation.AddQuestionParam;
import com.demo.lingsiojbackend.entity.queation.QuestionDetail;
import com.demo.lingsiojbackend.entity.vo.QuestionVO;
import com.demo.lingsiojbackend.service.QuestionService;
import com.demo.lingsiojbackend.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@Tag(name = "题目管理")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;


    /**
     * 分页获取题目列表
     * @param pageDTO 分页参数
     * @return 题目列表
     */
    @PostMapping("/page/list")
    @Operation(summary = "分页获取题目列表")
    public Result getQuestionList(@RequestBody PageDTO pageDTO) {
        if (pageDTO.getPageSize() == null || pageDTO.getPageNum() == null) {
            return Result.fail(ErrorCodeEnum.PARAM_ERROR);
        }
        List<QuestionVO> list = questionService.getQuestionList(pageDTO);
        return Result.success(list);
    }


    /**
     * 获取题目详情
     * @param id 题目id
     * @return 题目详情
     */
    @GetMapping("/detail/{id}")
    @Operation(summary = "获取题目详情")
    public Result getQuestionDetail(@PathVariable("id") Integer id) {
        if (id == null) {
            return Result.fail(ErrorCodeEnum.PARAM_ERROR);
        }
        QuestionDetail questionDetail = questionService.getQuestionDetail(id);
        return Result.success(questionDetail);
    }


    /**
     * 添加题目
     * @param addQuestionParam 添加题目参数
     * @return 添加结果
     */
    @PostMapping("/add")
    @Operation(summary = "添加题目")
    @Auth
    public Result addQuestion(@RequestBody AddQuestionParam addQuestionParam) {
        if (addQuestionParam == null || StringUtils.isBlank(addQuestionParam.getTitle())
                || StringUtils.isBlank(addQuestionParam.getContent())) {
            return Result.fail(ErrorCodeEnum.PARAM_ERROR);
        }
        questionService.addQuestion(addQuestionParam);
        return Result.success();
    }
}