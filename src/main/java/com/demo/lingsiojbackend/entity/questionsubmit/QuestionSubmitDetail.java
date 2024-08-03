package com.demo.lingsiojbackend.entity.questionsubmit;

import com.demo.lingsiojbackend.entity.queation.AdminQuestionDetail;
import com.demo.lingsiojbackend.entity.vo.UserVO;
import lombok.Data;

@Data
public class QuestionSubmitDetail {
    /**
     * 索引
     */
    private Integer id;

    /**
     * 代码
     */
    private String code;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 题目id
     */
    private AdminQuestionDetail adminQuestionDetail;

    /**
     * 提交题目的用户id
     */
    private UserVO userVO;

    /**
     * 判题状态
     */
    private Integer status;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;
}
