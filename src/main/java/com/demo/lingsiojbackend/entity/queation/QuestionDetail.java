package com.demo.lingsiojbackend.entity.queation;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionDetail implements Serializable {
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
     * 判题配置
     */
    private JudgeConfig judgeConfig;

    /**
     * 提交数
     */
    private Integer submitNum;

    /**
     * 通过数
     */
    private Integer acceptNum;

    /**
     * 创建用户id
     */
    private Integer userId;
}
