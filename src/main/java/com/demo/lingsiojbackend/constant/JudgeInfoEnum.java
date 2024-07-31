package com.demo.lingsiojbackend.constant;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum JudgeInfoEnum {

    ACCEPTED("Accepted", "成功"),
    WRONG_ANSWER("Wrong Answer", "答案错误"),
    COMPILE_ERROR("Compile Error", "编译错误"),
    MEMORY_LIMIT_EXCEEDED("Memory Limit Exceeded", "内存超限"),
    TIME_LIMIT_EXCEEDED("Time Limit Exceeded", "时间超限"),
    RUNTIME_ERROR("Runtime Error", "运行时错误"),
    PRESENTATION_ERROR("Presentation Error", "格式错误"),
    OUTPUT_LIMIT_EXCEEDED("Output Limit Exceeded", "输出超限"),
    SYSTEM_ERROR("System Error", "系统错误"),
    WAITING("Waiting", "等待中"),
    DANGEROUS_ERROR("Dangerous Error", "危险操作");



    private final String text;

    private final String value;

    JudgeInfoEnum(String text, String value) {
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
    public static JudgeInfoEnum getEnumByValue(String value) {
        for (JudgeInfoEnum judgeInfoEnum : values()) {
            if (judgeInfoEnum.value.equals(value)) {
                return judgeInfoEnum;
            }
        }
        return null;
    }
}
