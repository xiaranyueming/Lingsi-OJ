package com.demo.lingsiojbackend.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum QuestionSubmitEnum {

    WAITING("WAITING", 0),
    RUNNING("RUNNING", 1),
    SUCCESS("SUCCESS", 2),
    FAILED("FAILED", 3);

    private final String text;

    private final Integer value;

    QuestionSubmitEnum(String text, Integer value) {
        this.text = text;
        this.value = value;
    }


    /**
     * 获取值列表
     * @return 值列表
     */
    public static List<Integer> getValues() {
        return Arrays.stream(values()).map(item -> item.value).toList();
    }


    /**
     * 获取文本
     * @param value 值
     * @return 文本
     */
    public static QuestionSubmitEnum getEnumByValue(Integer value) {
        for (QuestionSubmitEnum questionSubmitEnum : values()) {
            if (questionSubmitEnum.value.equals(value)) {
                return questionSubmitEnum;
            }
        }
        return null;
    }
}
