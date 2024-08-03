package com.demo.lingsiojbackend.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import com.demo.lingsiojbackend.constant.LanguageEnum;
import com.demo.lingsiojbackend.entity.domain.Question;
import com.demo.lingsiojbackend.entity.domain.QuestionSubmit;
import com.demo.lingsiojbackend.entity.domain.User;
import com.demo.lingsiojbackend.entity.questionsubmit.AddQuestionSubmitParam;
import com.demo.lingsiojbackend.entity.questionsubmit.JudgeInfo;
import com.demo.lingsiojbackend.entity.questionsubmit.QuestionSubmitDetail;
import com.demo.lingsiojbackend.entity.questionsubmit.QuestionSubmitPage;
import com.demo.lingsiojbackend.entity.vo.QuestionSubmitVO;
import com.demo.lingsiojbackend.entity.vo.QuestionVO;
import com.demo.lingsiojbackend.entity.vo.UserVO;
import com.demo.lingsiojbackend.exception.CustomException;
import com.demo.lingsiojbackend.mapper.QuestionMapper;
import com.demo.lingsiojbackend.mapper.UserMapper;
import com.demo.lingsiojbackend.service.QuestionSubmitService;
import com.demo.lingsiojbackend.mapper.QuestionSubmitMapper;
import com.demo.lingsiojbackend.utils.QuestionSubmitUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author sh
* @description 针对表【question_submit(题目提交表)】的数据库操作Service实现
* @createDate 2024-07-29 08:55:33
*/
@Service
@RequiredArgsConstructor
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{

    private final UserMapper userMapper;

    private final QuestionMapper questionMapper;


    /**
     * 将题目提交列表转换为题目提交VO列表
     * @param questionSubmitList 题目提交列表
     * @return 题目提交VO列表
     */
    public List<QuestionSubmitVO> getQuestionSubmitList(List<QuestionSubmit> questionSubmitList) {
        if (questionSubmitList == null || questionSubmitList.isEmpty()) {
            return new ArrayList<>();
        }
        // 将题目提交列表中的用户id和questionId提取出来
        List<Integer> userIds = new ArrayList<>();
        List<Integer> questionIds = new ArrayList<>();
        questionSubmitList.forEach(questionSubmit -> {
            userIds.add(questionSubmit.getUserId());
            questionIds.add(questionSubmit.getQuestionId());
        });
        // 批量查询用户信息，将其转换为map，key为id，value为UserVO
        Map<Integer, UserVO> userVOMap = userMapper.selectBatchIds(userIds)
                .stream()
                .collect(Collectors.toMap(User::getId, user -> BeanUtil.copyProperties(user, UserVO.class)));
        // 批量查询题目信息，将其转换为map，key为id，value为QuestionVO
        Map<Integer, QuestionVO> questionVOMap = questionMapper.selectBatchIds(questionIds)
                .stream()
                .collect(Collectors.toMap(Question::getId, question -> {
                    QuestionVO questionVO = BeanUtil.copyProperties(question, QuestionVO.class);
                    // 将题目的标签转换为List
                    List<String> list = JSONUtil.parseArray(question.getTags()).toList(String.class);
                    questionVO.setTags(list);
                    return questionVO;
                }));

        return questionSubmitList.stream().map(questionSubmit -> {
            QuestionSubmitVO questionSubmitVO = BeanUtil.copyProperties(questionSubmit, QuestionSubmitVO.class);
            // 将 judgeInfo json转换为 JudgeInfo 对象
            questionSubmitVO.setJudgeInfo(JSONUtil.toBean(questionSubmit.getJudgeInfo(), JudgeInfo.class));
            // 从map中获取用户信息
            UserVO userVO = userVOMap.get(questionSubmit.getUserId());
            if (userVO != null) {
                questionSubmitVO.setUserVO(userVO);
            }
            // 从map中获取题目信息
            QuestionVO questionVO = questionVOMap.get(questionSubmit.getQuestionId());
            if (questionVO != null) {
                questionSubmitVO.setQuestionVO(questionVO);
            }
            return questionSubmitVO;
        }).toList();
    }


    /**
     * 分页获取题目提交列表
     * @param questionSubmitPage  分页参数
     * @return 题目提交列表
     */
    @Override
    public List<QuestionSubmitVO> getQuestionSubmitListByPage(QuestionSubmitPage questionSubmitPage) {
        if (questionSubmitPage == null || questionSubmitPage.getPageNum() <= 0 || questionSubmitPage.getPageSize() <= 0) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }

        Page<QuestionSubmit> page = new Page<>(questionSubmitPage.getPageNum(), questionSubmitPage.getPageSize());
        List<QuestionSubmit> questionSubmitList = this.lambdaQuery()
                .eq(questionSubmitPage.getQuestionIndex() != null, QuestionSubmit::getQuestionId,
                        questionSubmitPage.getQuestionIndex())
                .like(StringUtils.isNotBlank(questionSubmitPage.getLanguage()), QuestionSubmit::getLanguage,
                        questionSubmitPage.getLanguage())
                .page(page)
                .getRecords();
        return getQuestionSubmitList(questionSubmitList);
    }



    /**
     * 提交题目
     * @param addQuestionSubmitParam 提交题目参数
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addQuestionSubmit(AddQuestionSubmitParam addQuestionSubmitParam) {
        if (addQuestionSubmitParam == null || addQuestionSubmitParam.getQuestionId() == null
                || addQuestionSubmitParam.getUserId() == null || StringUtils.isBlank(addQuestionSubmitParam.getCode())
                || StringUtils.isBlank(addQuestionSubmitParam.getLanguage())) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }

        LanguageEnum languageEnum = LanguageEnum.getEnumByValue(addQuestionSubmitParam.getLanguage());
        if (languageEnum == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }
        QuestionSubmit questionSubmit = BeanUtil.copyProperties(addQuestionSubmitParam, QuestionSubmit.class);
        try {
            this.save(questionSubmit);
            // 修改题目的提交次数
            Question question = questionMapper.selectById(addQuestionSubmitParam.getQuestionId());
            question.setSubmitNum(question.getSubmitNum() + 1);
            questionMapper.updateById(question);
        } catch (Exception e) {
            throw new CustomException(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage());
        }
    }



    /**
     * 删除题目提交
     * @param id 题目提交id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteQuestionSubmit(Integer id) {
        if (id == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }
        try {
            this.removeById(id);
        } catch (Exception e) {
            throw new CustomException(ErrorCodeEnum.SYSTEM_ERROR.getCode(), ErrorCodeEnum.SYSTEM_ERROR.getMessage());
        }
    }



    /**
     * 获取题目提交详情
     * @param id 题目提交id
     * @return 题目提交详情
     */
    @Override
    public QuestionSubmitDetail getQuestionSubmitDetail(Integer id) {
        if (id == null) {
            throw new CustomException(ErrorCodeEnum.PARAM_ERROR.getCode(), ErrorCodeEnum.PARAM_ERROR.getMessage());
        }
        QuestionSubmit questionSubmit = this.getById(id);
        if (questionSubmit == null) {
            throw new CustomException(ErrorCodeEnum.INFO_NOT_EXIST.getCode(), ErrorCodeEnum.INFO_NOT_EXIST.getMessage());
        }
        User user = userMapper.selectById(questionSubmit.getUserId());
        if (user == null) {
            throw new CustomException(ErrorCodeEnum.INFO_NOT_EXIST.getCode(), ErrorCodeEnum.INFO_NOT_EXIST.getMessage());
        }
        Question question = questionMapper.selectById(questionSubmit.getQuestionId());
        if (question == null) {
            throw new CustomException(ErrorCodeEnum.INFO_NOT_EXIST.getCode(), ErrorCodeEnum.INFO_NOT_EXIST.getMessage());
        }
        return QuestionSubmitUtil.questionSubmit2QuestionSubmitDetail(questionSubmit, user, question);
    }
}




