package com.demo.lingsiojbackend.entity.queation;

import lombok.Data;

import java.io.Serializable;

/**
 * 判题用例
 */
@Data
public class JudgeCase implements Serializable {

    /**
     * 输入
     */
    private String input;

    /**
     * 输出
     */
    private String output;
}
