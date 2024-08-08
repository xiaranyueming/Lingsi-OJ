package com.demo.lingsiojbackend.judge.codesandbox.entity;

import com.demo.lingsiojbackend.entity.questionsubmit.JudgeInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExecuteCodeResponse {
    private String message;

    private List<String> output;

    private JudgeInfo judgeInfo;
}
