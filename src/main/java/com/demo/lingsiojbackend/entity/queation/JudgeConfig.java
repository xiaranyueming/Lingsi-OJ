package com.demo.lingsiojbackend.entity.queation;

import lombok.Data;

import java.io.Serializable;

/**
 * 判题配置类
 */
@Data
public class JudgeConfig implements Serializable {

    /**
     * 时间限制 ms
     */
    private Long timeLimit;

    /**
     * 内存限制 kb
     */
    private Long memoryLimit;

    /**
     * 栈限制 kb
     */
    private Long stackLimit;
}
