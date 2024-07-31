package com.demo.lingsiojbackend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.lingsiojbackend.annotation.Auth;
import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import com.demo.lingsiojbackend.entity.domain.Question;
import com.demo.lingsiojbackend.entity.dto.PageDTO;
import com.demo.lingsiojbackend.entity.queation.AddQuestionParam;
import com.demo.lingsiojbackend.entity.queation.QuestionDetail;
import com.demo.lingsiojbackend.entity.vo.QuestionVO;
import com.demo.lingsiojbackend.exception.CustomException;
import com.demo.lingsiojbackend.service.QuestionService;
import com.demo.lingsiojbackend.mapper.QuestionMapper;
import com.demo.lingsiojbackend.utils.QuestionUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author sh
* @description 针对表【question(题目表)】的数据库操作Service实现
* @createDate 2024-07-29 08:55:33
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{


    /**
     * 分页获取题目列表
     * @param pageDTO 分页参数
     * @return 题目列表
     */
    @Override
    public List<QuestionVO> getQuestionList(PageDTO pageDTO) {
        if (pageDTO.getPageSize() == null || pageDTO.getPageNum() == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }

        Page<Question> page = new Page<>(pageDTO.getPageNum(), pageDTO.getPageSize());
        List<QuestionVO> questionList = null;
        if (pageDTO.getQuestionIndex() != null) {
            List<Question> records = this.lambdaQuery()
                    .eq(Question::getId, pageDTO.getQuestionIndex())
                    .page(page)
                    .getRecords();
            questionList = QuestionUtil.questionList2QueationVOList(records);
            return questionList;
        } else if (StringUtils.isBlank(pageDTO.getKeyword())) {
            List<Question> records = this.lambdaQuery()
                    .page(page)
                    .getRecords();
            questionList = QuestionUtil.questionList2QueationVOList(records);
        } else {
            List<Question> records = this.lambdaQuery()
                    .like(Question::getTitle, pageDTO.getKeyword())
                    .or()
                    .like(Question::getContent, pageDTO.getKeyword())
                    .or()
                    .like(Question::getTags, pageDTO.getKeyword())
                    .page(page)
                    .getRecords();
            questionList = QuestionUtil.questionList2QueationVOList(records);
        }
        return questionList;
    }


    /**
     * 获取题目详情
     * @param id 题目id
     * @return 题目详情
     */
    @Override
    public QuestionDetail getQuestionDetail(Integer id) {
        if (id == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }
        Question question = this.lambdaQuery()
                .eq(Question::getId, id)
                .one();
        if (question == null) {
            throw new CustomException(ErrorCodeEnum.INFO_NOT_EXIST.getCode(), ErrorCodeEnum.INFO_NOT_EXIST.getMessage());
        }
        return QuestionUtil.question2QuestionDetail(question);
    }


    /**
     * 添加题目
     *
     * @param addQuestionParam 添加题目参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addQuestion(AddQuestionParam addQuestionParam) {
        if (addQuestionParam == null || StringUtils.isBlank(addQuestionParam.getTitle())
                || StringUtils.isBlank(addQuestionParam.getContent())) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }
        Question question = QuestionUtil.addQuestionParam2Question(addQuestionParam);
        try {
            this.save(question);
        } catch (Exception e) {
            System.out.println(e);
            throw new CustomException(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage());
        }
    }


}




