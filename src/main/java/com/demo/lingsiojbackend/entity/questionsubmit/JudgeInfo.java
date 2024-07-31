package com.demo.lingsiojbackend.entity.questionsubmit;

import lombok.Data;

import java.io.Serializable;

/**
 * 评测信息
 */
@Data
public class JudgeInfo implements Serializable {

    /**
     * 执行信息
     */
    private String message;

    /**
     * 消耗内存
     */
    private Long memory;

    /**
     * 消耗时间
     */
    private Long time;
}
