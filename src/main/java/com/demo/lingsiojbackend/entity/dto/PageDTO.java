package com.demo.lingsiojbackend.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PageDTO implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private String keyword;
    private Integer questionIndex;
}
