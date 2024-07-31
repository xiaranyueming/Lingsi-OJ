package com.demo.lingsiojbackend.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum LanguageEnum {

    JAVA("Java", "java"),
    C("C", "c"),
    CPP("C++", "c++"),
    PYTHON("Python", "python"),
    JAVASCRIPT("JavaScript", "javascript"),
    GOLANG("Golang", "golang");

    private final String text;

    private final String value;

    LanguageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }


    /**
     * 获取值列表
     * @return 值列表
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).toList();
    }


    /**
     * 获取文本
     * @param value 值
     * @return 文本
     */
    public static LanguageEnum getEnumByValue(String value) {
        for (LanguageEnum languageEnum : values()) {
            if (languageEnum.value.equals(value)) {
                return languageEnum;
            }
        }
        return null;
    }
}
