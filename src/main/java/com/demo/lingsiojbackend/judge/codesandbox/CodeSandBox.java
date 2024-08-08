package com.demo.lingsiojbackend.judge.codesandbox;

import com.demo.lingsiojbackend.judge.codesandbox.entity.ExecuteCodeRequest;
import com.demo.lingsiojbackend.judge.codesandbox.entity.ExecuteCodeResponse;

public interface CodeSandBox {


    /**
     * 执行代码
     * @param executeCodeRequest 请求参数
     * @return 执行结果
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
