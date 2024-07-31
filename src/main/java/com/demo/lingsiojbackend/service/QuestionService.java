package com.demo.lingsiojbackend.service;

import com.demo.lingsiojbackend.entity.domain.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.lingsiojbackend.entity.queation.QuestionPage;
import com.demo.lingsiojbackend.entity.queation.AddQuestionParam;
import com.demo.lingsiojbackend.entity.queation.QuestionDetail;
import com.demo.lingsiojbackend.entity.queation.UpdateQuestionParam;
import com.demo.lingsiojbackend.entity.vo.QuestionVO;

import java.util.List;

/**
* @author sh
* @description 针对表【question(题目表)】的数据库操作Service
* @createDate 2024-07-29 08:55:33
*/
public interface QuestionService extends IService<Question> {

    /**
     * 分页获取题目列表
     * @param questionPage 分页参数
     * @return 题目列表
     */
    List<QuestionVO> getQuestionList(QuestionPage questionPage);


    /**
     * 获取题目详情
     * @param id 题目id
     * @return 题目详情
     */
    QuestionDetail getQuestionDetail(Integer id);


    /**
     * 添加题目
     *
     * @param addQuestionParam 添加题目参数
     */
    void addQuestion(AddQuestionParam addQuestionParam);


    /**
     * 更新题目
     * @param updateQuestionParam 更新题目参数
     */
    void updateQuestion(UpdateQuestionParam updateQuestionParam);


    /**
     * 删除题目
     * @param id 题目id
     */
    void deleteQuestion(Integer id);
}
