package com.demo.lingsiojbackend.service;

import com.demo.lingsiojbackend.entity.domain.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.lingsiojbackend.entity.queation.QuestionPage;
import com.demo.lingsiojbackend.entity.questionsubmit.AddQuestionSubmitParam;
import com.demo.lingsiojbackend.entity.questionsubmit.QuestionSubmitDetail;
import com.demo.lingsiojbackend.entity.questionsubmit.QuestionSubmitPage;
import com.demo.lingsiojbackend.entity.vo.QuestionSubmitVO;

import java.util.List;

/**
* @author sh
* @description 针对表【question_submit(题目提交表)】的数据库操作Service
* @createDate 2024-07-29 08:55:33
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {


    /**
     * 分页获取题目提交列表
     * @param questionSubmitPage  分页参数
     * @return 题目提交列表
     */
    List<QuestionSubmitVO> getQuestionSubmitListByPage(QuestionSubmitPage questionSubmitPage);


    /**
     * 提交题目
     * @param addQuestionSubmitParam 提交题目参数
     */
    Integer addQuestionSubmit(AddQuestionSubmitParam addQuestionSubmitParam);


    /**
     * 删除题目提交
     * @param id 题目提交id
     */
    void deleteQuestionSubmit(Integer id);


    /**
     * 获取题目提交详情
     * @param id 题目提交id
     * @return 题目提交详情
     */
    QuestionSubmitDetail getQuestionSubmitDetail(Integer id);
}
