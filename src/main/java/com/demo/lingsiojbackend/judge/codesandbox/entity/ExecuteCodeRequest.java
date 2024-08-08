package com.demo.lingsiojbackend.judge.codesandbox.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExecuteCodeRequest {
    private String code;

    private List<String> input;

    private String language;
}
