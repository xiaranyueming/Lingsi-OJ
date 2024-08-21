package com.demo.lingsiojbackend.judge.codesandbox.impl;

import com.demo.lingsiojbackend.exception.CustomException;
import com.demo.lingsiojbackend.feign.JudgeFeignClient;
import com.demo.lingsiojbackend.judge.codesandbox.CodeSandBox;
import com.demo.lingsiojbackend.judge.codesandbox.entity.ExecuteCodeRequest;
import com.demo.lingsiojbackend.judge.codesandbox.entity.ExecuteCodeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RemoteCodeSandBox implements CodeSandBox {

    private final JudgeFeignClient judgeFeignClient;

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("远程代码沙箱");
        return judgeFeignClient.doJudge(executeCodeRequest);
    }
}
