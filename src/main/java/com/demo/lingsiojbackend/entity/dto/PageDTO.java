package com.demo.lingsiojbackend.entity.dto;

import lombok.Data;

@Data
public class PageDTO {
    private Integer pageNum;
    private Integer pageSize;
    private String keyword;
}
