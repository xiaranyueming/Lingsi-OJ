package com.demo.lingsiojbackend.judge.codesandbox.impl;

import com.demo.lingsiojbackend.judge.codesandbox.CodeSandBox;
import com.demo.lingsiojbackend.judge.codesandbox.entity.ExecuteCodeRequest;
import com.demo.lingsiojbackend.judge.codesandbox.entity.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RemoteCodeSandBox implements CodeSandBox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("远程代码沙箱");
        return null;
    }
}
