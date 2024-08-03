package com.demo.lingsiojbackend.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.demo.lingsiojbackend.entity.domain.Question;
import com.demo.lingsiojbackend.entity.domain.QuestionSubmit;
import com.demo.lingsiojbackend.entity.domain.User;
import com.demo.lingsiojbackend.entity.queation.AdminQuestionDetail;
import com.demo.lingsiojbackend.entity.questionsubmit.JudgeInfo;
import com.demo.lingsiojbackend.entity.questionsubmit.QuestionSubmitDetail;
import com.demo.lingsiojbackend.entity.vo.UserVO;

public class QuestionSubmitUtil {

    /**
     * 将题目提交实体转换为题目提交详情实体
     * @param questionSubmit 题目提交实体
     * @return 题目提交详情实体
     */
    public static QuestionSubmitDetail questionSubmit2QuestionSubmitDetail(QuestionSubmit questionSubmit, User user, Question question) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitDetail questionSubmitDetail = BeanUtil.copyProperties(questionSubmit, QuestionSubmitDetail.class);
        // 将 judgeInfo 字符串转换为 JudgeInfo 对象
        JudgeInfo judgeInfo = JSONUtil.toBean(questionSubmit.getJudgeInfo(), JudgeInfo.class);
        questionSubmitDetail.setJudgeInfo(judgeInfo);
        // 设置用户信息
        questionSubmitDetail.setUserVO(BeanUtil.copyProperties(user, UserVO.class));
        // 设置题目信息
        AdminQuestionDetail adminQuestionDetail = QuestionUtil.question2AdminQuestionDetail(question);
        questionSubmitDetail.setAdminQuestionDetail(adminQuestionDetail);

        return questionSubmitDetail;
    }
}
