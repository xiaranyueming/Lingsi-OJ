package com.demo.lingsiojbackend.config;

import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import com.demo.lingsiojbackend.entity.dto.PageDTO;
import com.demo.lingsiojbackend.entity.vo.QuestionVO;
import com.demo.lingsiojbackend.service.QuestionService;
import com.demo.lingsiojbackend.utils.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
