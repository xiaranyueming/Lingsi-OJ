package com.demo.lingsicodesandbox.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {
    private String message;

    private Integer status;

    private List<String> output;

    private JudgeInfo judgeInfo;
}
