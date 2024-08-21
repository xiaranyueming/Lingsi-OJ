package com.demo.lingsiojbackend.utils;

import lombok.Data;

import java.util.List;

@Data
public class PageUtil<T> {
    private long total;
    private List<T> list;
}
