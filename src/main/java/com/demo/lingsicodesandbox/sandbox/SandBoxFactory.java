package com.demo.lingsicodesandbox.sandbox;

public class SandBoxFactory {

    public static CodeSandBox getSandBox(String language) {
        if (language == null) {
            return null;
        }
        switch (language) {
            case "java":
                return new JavaNativeCodeSandBox();
            default:
                return null;
        }
    }

}
