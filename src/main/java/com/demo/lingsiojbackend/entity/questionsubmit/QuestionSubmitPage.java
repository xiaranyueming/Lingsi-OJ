package com.demo.lingsiojbackend.entity.questionsubmit;

import lombok.Data;

@Data
public class QuestionSubmitPage {
    private Integer pageNum;
    private Integer pageSize;
    private String language;
    private Integer questionIndex;
}
