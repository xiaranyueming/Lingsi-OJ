package com.demo.lingsiojbackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.lingsiojbackend.entity.domain.Question;
import com.demo.lingsiojbackend.service.QuestionService;
import com.demo.lingsiojbackend.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author sh
* @description 针对表【question(题目表)】的数据库操作Service实现
* @createDate 2024-07-29 08:55:33
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




