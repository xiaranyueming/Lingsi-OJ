package com.demo.lingsiojbackend.entity.vo;

import com.demo.lingsiojbackend.entity.questionsubmit.JudgeInfo;
import lombok.Data;

@Data
public class QuestionSubmitVO {
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
    private QuestionVO questionVO;

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
