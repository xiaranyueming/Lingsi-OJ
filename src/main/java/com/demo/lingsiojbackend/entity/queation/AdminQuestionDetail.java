package com.demo.lingsiojbackend.entity.queation;


import lombok.Data;

import java.util.List;

@Data
public class AdminQuestionDetail {
    /**
     * 索引
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 判题用例
     */
    private List<JudgeCase> judgeCase;

    /**
     * 判题配置
     */
    private JudgeConfig judgeConfig;

    /**
     * 创建用户id
     */
    private Integer userId;
}
