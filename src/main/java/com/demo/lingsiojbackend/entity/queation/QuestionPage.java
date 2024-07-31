package com.demo.lingsiojbackend.entity.queation;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuestionPage implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private String keyword;
    private Integer questionIndex;
}
