package com.demo.lingsiojbackend.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class QuestionVO implements Serializable {
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

    /**
     * 提交数
     */
    private Integer submitNum;

    /**
     * 通过数
     */
    private Integer acceptNum;
}
