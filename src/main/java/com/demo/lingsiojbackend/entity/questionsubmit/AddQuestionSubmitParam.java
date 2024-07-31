package com.demo.lingsiojbackend.entity.questionsubmit;

import lombok.Data;

@Data
public class AddQuestionSubmitParam {
    /**
     * 代码
     */
    private String code;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 题目id
     */
    private Integer questionId;

    /**
     * 提交题目的用户id
     */
    private Integer userId;
}
