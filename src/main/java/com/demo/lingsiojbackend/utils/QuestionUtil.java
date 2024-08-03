package com.demo.lingsiojbackend.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.demo.lingsiojbackend.entity.domain.Question;
import com.demo.lingsiojbackend.entity.queation.*;
import com.demo.lingsiojbackend.entity.vo.QuestionVO;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionUtil {

    /**
     * 将题目列表转换为题目VO列表
     * @param questionList 题目列表
     * @return 题目VO列表
     */
    public static List<QuestionVO> questionList2QueationVOList(List<Question> questionList) {
        return questionList.stream().map(question -> {
            JSONArray jsonArray = JSONUtil.parseArray(question.getTags());
            List<String> tags = jsonArray.toList(String.class);
            QuestionVO questionVO = new QuestionVO();
            questionVO.setId(question.getId());
            questionVO.setTitle(question.getTitle());
            questionVO.setTags(tags);
            questionVO.setSubmitNum(question.getSubmitNum());
            questionVO.setAcceptNum(question.getAcceptNum());
            return questionVO;
        }).collect(Collectors.toList());
    }


    /**
     * 将题目实体转换为题目详情实体
     * @param question 题目实体
     * @return 题目详情实体
     */
    public static QuestionDetail question2QuestionDetail(Question question) {
        QuestionDetail questionDetail = new QuestionDetail();
        questionDetail.setId(question.getId());
        questionDetail.setTitle(question.getTitle());
        questionDetail.setContent(question.getContent());
        // 将 tags 字符串转换为 List
        JSONArray jsonArray = JSONUtil.parseArray(question.getTags());
        List<String> tags = jsonArray.toList(String.class);
        questionDetail.setTags(tags);
        // 将 judgeConfig 字符串转换为 JudgeConfig 对象
        JudgeConfig judgeConfig = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class);
        questionDetail.setJudgeConfig(judgeConfig);
        questionDetail.setSubmitNum(question.getSubmitNum());
        questionDetail.setAcceptNum(question.getAcceptNum());
        questionDetail.setUserId(question.getUserId());

        return questionDetail;
    }


    /**
     * 将题目实体转换为后台题目详情实体
     * @param question  题目实体
     * @return  AdminQuestionDetail
     */
    public static AdminQuestionDetail question2AdminQuestionDetail(Question question) {
        AdminQuestionDetail adminQuestionDetail = new AdminQuestionDetail();
        adminQuestionDetail.setId(question.getId());
        adminQuestionDetail.setTitle(question.getTitle());
        adminQuestionDetail.setContent(question.getContent());
        // 将 tags 字符串转换为 List
        JSONArray jsonArray = JSONUtil.parseArray(question.getTags());
        List<String> tags = jsonArray.toList(String.class);
        adminQuestionDetail.setTags(tags);
        // 将 judgeCase 字符串转换为 List
        JSONArray judgeCaseArray = JSONUtil.parseArray(question.getJudgeCase());
        List<JudgeCase> judgeCaseList = judgeCaseArray.toList(JudgeCase.class);
        adminQuestionDetail.setJudgeCase(judgeCaseList);
        // 将 judgeConfig 字符串转换为 JudgeConfig 对象
        JudgeConfig judgeConfig = JSONUtil.toBean(question.getJudgeConfig(), JudgeConfig.class);
        adminQuestionDetail.setJudgeConfig(judgeConfig);
        adminQuestionDetail.setUserId(question.getUserId());

        return adminQuestionDetail;
    }


    /**
     * 将 AddQuestionParam 转换为 Question
     * @param addQuestionParam AddQuestionParam
     * @return Question
     */
    public static Question addQuestionParam2Question(AddQuestionParam addQuestionParam) {
        Question question = new Question();
        question.setTitle(addQuestionParam.getTitle());
        question.setContent(addQuestionParam.getContent());
        // 将 tags List 转换为字符串
        String tags = JSONUtil.toJsonStr(addQuestionParam.getTags());
        question.setTags(tags);
        question.setAnswer(addQuestionParam.getAnswer());
        // 将judgeCase List 转换为字符串、
        String judgeCase = JSONUtil.toJsonStr(addQuestionParam.getJudgeCase());
        question.setJudgeCase(judgeCase);
        // 将 judgeConfig 对象转换为字符串
        String judgeConfig = JSONUtil.toJsonStr(addQuestionParam.getJudgeConfig());
        question.setJudgeConfig(judgeConfig);
        question.setUserId(addQuestionParam.getUserId());

        return question;
    }


    /**
     * 将 UpdateQuestionParam 转换为 Question
     * @param updateQuestionParam UpdateQuestionParam
     * @return Question
     */
    public static Question updateQuestionParam2Question(UpdateQuestionParam updateQuestionParam) {
        Question question = new Question();
        question.setId(updateQuestionParam.getId());
        question.setTitle(updateQuestionParam.getTitle());
        question.setContent(updateQuestionParam.getContent());
        // 将 tags List 转换为字符串
        String tags = JSONUtil.toJsonStr(updateQuestionParam.getTags());
        question.setTags(tags);
        question.setAnswer(updateQuestionParam.getAnswer());
        // 将judgeCase List 转换为字符串
        String judgeCase = JSONUtil.toJsonStr(updateQuestionParam.getJudgeCase());
        question.setJudgeCase(judgeCase);
        // 将 judgeConfig 对象转换为字符串
        String judgeConfig = JSONUtil.toJsonStr(updateQuestionParam.getJudgeConfig());
        question.setJudgeConfig(judgeConfig);
        question.setUserId(updateQuestionParam.getUserId());

        return question;
    }
}
