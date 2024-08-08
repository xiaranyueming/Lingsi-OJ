package com.demo.lingsiojbackend.judge.strategy;

public class StrategyFactory {

    public static JudgeStrategy getStrategy(String language) {
        switch (language) {
            case "java":
                return new JavaJudgeStrategy();
            default:
                return new DefaultJudgeStrategy();
        }
    }
}
