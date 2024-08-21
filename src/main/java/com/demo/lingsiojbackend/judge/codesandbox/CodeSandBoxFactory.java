package com.demo.lingsiojbackend.judge.codesandbox;

import com.demo.lingsiojbackend.judge.codesandbox.impl.ExampleCodeSandBox;
import com.demo.lingsiojbackend.judge.codesandbox.impl.RemoteCodeSandBox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CodeSandBoxFactory {

    private final RemoteCodeSandBox remoteCodeSandBox;

    public CodeSandBox newInstance(String type) {
        return switch (type) {
            case "example" -> new ExampleCodeSandBox();
            case "remote" -> remoteCodeSandBox;
            default -> new ExampleCodeSandBox();
        };
    }
}
