package com.demo.lingsicodesandbox.sandbox;

import com.demo.lingsicodesandbox.entity.ExecuteCodeRequest;
import com.demo.lingsicodesandbox.entity.ExecuteCodeResponse;

public interface CodeSandBox {


    /**
     * 执行代码
     * @param executeCodeRequest 请求参数
     * @return 执行结果
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
