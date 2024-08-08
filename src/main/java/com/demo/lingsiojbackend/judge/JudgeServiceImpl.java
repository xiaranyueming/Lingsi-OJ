package com.demo.lingsiojbackend.judge;

import cn.hutool.json.JSONUtil;
import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import com.demo.lingsiojbackend.constant.QuestionSubmitEnum;
import com.demo.lingsiojbackend.entity.domain.Question;
import com.demo.lingsiojbackend.entity.domain.QuestionSubmit;
import com.demo.lingsiojbackend.entity.queation.JudgeCase;
import com.demo.lingsiojbackend.entity.queation.JudgeConfig;
import com.demo.lingsiojbackend.entity.questionsubmit.JudgeInfo;
import com.demo.lingsiojbackend.exception.CustomException;
import com.demo.lingsiojbackend.judge.codesandbox.CodeSandBox;
import com.demo.lingsiojbackend.judge.codesandbox.CodeSandBoxFactory;
import com.demo.lingsiojbackend.judge.codesandbox.entity.ExecuteCodeRequest;
import com.demo.lingsiojbackend.judge.codesandbox.entity.ExecuteCodeResponse;
import com.demo.lingsiojbackend.judge.strategy.JudgeContext;
import com.demo.lingsiojbackend.judge.strategy.JudgeStrategy;
import com.demo.lingsiojbackend.judge.strategy.StrategyFactory;
import com.demo.lingsiojbackend.service.QuestionService;
import com.demo.lingsiojbackend.service.QuestionSubmitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class JudgeServiceImpl implements JudgeService {

    private final QuestionService questionService;

    private final QuestionSubmitService questionSubmitService;

    @Value("${judge.type}")
    private String type;

    /**
     * 执行代码
     * @param questionSubmitId 题目提交id
     */
    @Override
    public void doJudge(Integer questionSubmitId) {
        if (questionSubmitId == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), "题目id不能为空");
        }
        // 获取题目提交信息
        QuestionSubmit questionSubmit = questionSubmitService.lambdaQuery()
                .eq(QuestionSubmit::getId, questionSubmitId)
                .one();
        if (questionSubmit == null) {
            throw new CustomException(ErrorCodeEnum.INFO_NOT_EXIST.getCode(), "题目提交不存在");
        }
        // 获取题目信息
        Question question = questionService.lambdaQuery()
                .eq(Question::getId, questionSubmit.getQuestionId())
                .one();
        if (question == null) {
            throw new CustomException(ErrorCodeEnum.INFO_NOT_EXIST.getCode(), "题目不存在");
        }
        // 判断判题状态
        if (!Objects.equals(questionSubmit.getStatus(), QuestionSubmitEnum.WAITING.getValue())) {
            throw new CustomException(ErrorCodeEnum.SYSTEM_ERROR.getCode(), "题目正在判题");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitEnum.RUNNING.getValue());
        boolean b = questionSubmitService.updateById(questionSubmitUpdate);
        if (!b) {
            throw new CustomException(ErrorCodeEnum.SYSTEM_ERROR.getCode(), "更新题目提交状态失败");
        }
        // 获取代码沙箱
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        // 获取输入用例
        List<JudgeCase> judgeCaseList = JSONUtil.toList(question.getJudgeCase(), JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).toList();
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(questionSubmit.getCode())
                .language(questionSubmit.getLanguage())
                .input(inputList)
                .build();
        // 执行代码
        ExecuteCodeResponse executeCodeResponse = codeSandBox.executeCode(executeCodeRequest);
        // 判断执行结果
        if (executeCodeResponse == null) {
            throw new CustomException(ErrorCodeEnum.SYSTEM_ERROR.getCode(), "执行代码失败");
        }
        // 判断判题结果
        JudgeContext judgeContext = JudgeContext.builder()
                .judgeInfo(executeCodeResponse.getJudgeInfo())
                .inputList(inputList)
                .outputList(executeCodeResponse.getOutput())
                .judgeCase(judgeCaseList)
                .judgeConfig(JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class))
                .question(question)
                .build();
        JudgeStrategy judgeStrategy = StrategyFactory.getStrategy(questionSubmit.getLanguage());
        JudgeInfo judgeInfo = judgeStrategy.doJudge(judgeContext);
        // 更新题目提交状态
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitEnum.SUCCESS.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        b = questionSubmitService.updateById(questionSubmitUpdate);
        if (!b) {
            throw new CustomException(ErrorCodeEnum.SYSTEM_ERROR.getCode(), "更新题目提交状态失败");
        }
    }
}
