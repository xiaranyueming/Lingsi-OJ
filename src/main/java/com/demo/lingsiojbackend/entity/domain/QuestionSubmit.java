package com.demo.lingsiojbackend.entity.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 题目提交表
 * @TableName question_submit
 */
@TableName(value ="question_submit")
@Data
public class QuestionSubmit implements Serializable {
    /**
     * 索引
     */
    @TableId(type = IdType.AUTO)
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
    private Integer questionId;

    /**
     * 提交题目的用户id
     */
    private Integer userId;

    /**
     * 判题状态
     */
    private Integer status;

    /**
     * 判题信息
     */
    private String judgeInfo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDelete;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}