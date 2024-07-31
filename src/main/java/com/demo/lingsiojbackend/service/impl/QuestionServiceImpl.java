package com.demo.lingsiojbackend.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import com.demo.lingsiojbackend.entity.domain.Question;
import com.demo.lingsiojbackend.entity.queation.QuestionPage;
import com.demo.lingsiojbackend.entity.queation.AddQuestionParam;
import com.demo.lingsiojbackend.entity.queation.QuestionDetail;
import com.demo.lingsiojbackend.entity.queation.UpdateQuestionParam;
import com.demo.lingsiojbackend.entity.vo.QuestionVO;
import com.demo.lingsiojbackend.exception.CustomException;
import com.demo.lingsiojbackend.service.QuestionService;
import com.demo.lingsiojbackend.mapper.QuestionMapper;
import com.demo.lingsiojbackend.utils.QuestionUtil;
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
     * @param questionPage 分页参数
     * @return 题目列表
     */
    @Override
    public List<QuestionVO> getQuestionList(QuestionPage questionPage) {
        if (questionPage.getPageSize() == null || questionPage.getPageNum() == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }

        Page<Question> page = new Page<>(questionPage.getPageNum(), questionPage.getPageSize());
        List<Question> questionList = this.lambdaQuery()
                .eq(questionPage.getQuestionIndex() != null, Question::getId, questionPage.getQuestionIndex())
                .like(StringUtils.isNotBlank(questionPage.getKeyword()), Question::getTitle, questionPage.getKeyword())
                .or()
                .like(StringUtils.isNotBlank(questionPage.getKeyword()), Question::getContent, questionPage.getKeyword())
                .or()
                .like(StringUtils.isNotBlank(questionPage.getKeyword()), Question::getTags, questionPage.getKeyword())
                .page(page)
                .getRecords();

        return QuestionUtil.questionList2QueationVOList(questionList);
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
            throw new CustomException(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage());
        }
    }


    /**
     * 更新题目
     * @param updateQuestionParam 更新题目参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuestion(UpdateQuestionParam updateQuestionParam) {
        if (updateQuestionParam == null || updateQuestionParam.getId() == null
                || StringUtils.isBlank(updateQuestionParam.getTitle())
                || StringUtils.isBlank(updateQuestionParam.getContent())) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }
        Question question = this.lambdaQuery()
                .eq(Question::getId, updateQuestionParam.getId())
                .one();
        if (question == null) {
            throw new CustomException(ErrorCodeEnum.INFO_NOT_EXIST.getCode(), ErrorCodeEnum.INFO_NOT_EXIST.getMessage());
        }
        Question updatedQuestion = QuestionUtil.updateQuestionParam2Question(updateQuestionParam);
        try {
            this.updateById(updatedQuestion);
        } catch (Exception e) {
            throw new CustomException(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage());
        }
    }



    /**
     * 删除题目
     * @param id 题目id
     */
    @Override
    public void deleteQuestion(Integer id) {
        if (id == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }
        Question question = this.lambdaQuery()
                .eq(Question::getId, id)
                .one();
        if (question == null) {
            throw new CustomException(ErrorCodeEnum.INFO_NOT_EXIST.getCode(), ErrorCodeEnum.INFO_NOT_EXIST.getMessage());
        }
        try {
            this.removeById(id);
        } catch (Exception e) {
            throw new CustomException(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage());
        }
    }


}




