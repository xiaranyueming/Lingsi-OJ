package com.demo.lingsiojbackend.judge.strategy;

import com.demo.lingsiojbackend.entity.domain.Question;
import com.demo.lingsiojbackend.entity.queation.JudgeCase;
import com.demo.lingsiojbackend.entity.queation.JudgeConfig;
import com.demo.lingsiojbackend.entity.questionsubmit.JudgeInfo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JudgeContext {
    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCase;

    private JudgeConfig judgeConfig;

    private Question question;
}
