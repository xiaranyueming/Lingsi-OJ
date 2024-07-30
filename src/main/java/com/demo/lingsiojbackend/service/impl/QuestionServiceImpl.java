package com.demo.lingsiojbackend.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.lingsiojbackend.constant.ErrorCodeEnum;
import com.demo.lingsiojbackend.entity.domain.Question;
import com.demo.lingsiojbackend.entity.dto.PageDTO;
import com.demo.lingsiojbackend.entity.vo.QuestionVO;
import com.demo.lingsiojbackend.exception.CustomException;
import com.demo.lingsiojbackend.service.QuestionService;
import com.demo.lingsiojbackend.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author sh
* @description 针对表【question(题目表)】的数据库操作Service实现
* @createDate 2024-07-29 08:55:33
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{


    /**
     * 将题目列表转换为题目VO列表
     * @param questionList 题目列表
     * @return 题目VO列表
     */
    private List<QuestionVO> questionList2QueationVOList(List<Question> questionList) {
        return questionList.stream().map(question -> {
            JSONArray jsonArray = JSONUtil.parseArray(question.getTags());
            List<String> tags = jsonArray.toList(String.class);
            QuestionVO questionVO = new QuestionVO();
            questionVO.setId(question.getId());
            questionVO.setTitle(question.getTitle());
            questionVO.setTags(tags);
            return questionVO;
        }).collect(Collectors.toList());
    }


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
        if ("".equals(pageDTO.getKeyword())) {
            List<Question> records = this.lambdaQuery()
                    .page(page)
                    .getRecords();
            questionList = questionList2QueationVOList(records);
        } else {
            List<Question> records = this.lambdaQuery()
                    .like(Question::getTitle, pageDTO.getKeyword())
                    .or()
                    .like(Question::getContent, pageDTO.getKeyword())
                    .page(page)
                    .getRecords();
            questionList = questionList2QueationVOList(records);
        }
        return questionList;
    }
}




