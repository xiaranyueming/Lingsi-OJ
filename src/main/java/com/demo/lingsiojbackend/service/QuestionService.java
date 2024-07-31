package com.demo.lingsiojbackend.service;

import com.demo.lingsiojbackend.entity.domain.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.lingsiojbackend.entity.dto.PageDTO;
import com.demo.lingsiojbackend.entity.queation.AddQuestionParam;
import com.demo.lingsiojbackend.entity.queation.QuestionDetail;
import com.demo.lingsiojbackend.entity.vo.QuestionVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author sh
* @description 针对表【question(题目表)】的数据库操作Service
* @createDate 2024-07-29 08:55:33
*/
public interface QuestionService extends IService<Question> {

    /**
     * 分页获取题目列表
     * @param pageDTO 分页参数
     * @return 题目列表
     */
    List<QuestionVO> getQuestionList(PageDTO pageDTO);


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
}
