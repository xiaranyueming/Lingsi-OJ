package com.demo.lingsiojbackend.judge.codesandbox;

import com.demo.lingsiojbackend.judge.codesandbox.impl.ExampleCodeSandBox;
import com.demo.lingsiojbackend.judge.codesandbox.impl.RemoteCodeSandBox;

public class CodeSandBoxFactory {

    public static CodeSandBox newInstance(String type) {
        return switch (type) {
            case "example" -> new ExampleCodeSandBox();
            case "remote" -> new RemoteCodeSandBox();
            default -> new ExampleCodeSandBox();
        };
    }
}
