package com.demo.lingsiojbackend.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class QuestionVO {
    /**
     * 索引
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 标签
     */
    private List<String> tags;
}
