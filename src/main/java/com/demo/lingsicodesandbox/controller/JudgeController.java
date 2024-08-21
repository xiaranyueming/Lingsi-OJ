package com.demo.lingsicodesandbox.controller;

import com.demo.lingsicodesandbox.entity.ExecuteCodeRequest;
import com.demo.lingsicodesandbox.entity.ExecuteCodeResponse;
import com.demo.lingsicodesandbox.sandbox.CodeSandBox;
import com.demo.lingsicodesandbox.sandbox.SandBoxFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/judge")
public class JudgeController {

    @PostMapping("/do")
    public ExecuteCodeResponse doJudge(@RequestBody ExecuteCodeRequest executeCodeRequest) {
        if (executeCodeRequest == null || executeCodeRequest.getCode() == null) {
            return null;
        }
        CodeSandBox codeSandBox = SandBoxFactory.getSandBox(executeCodeRequest.getLanguage());
        if (codeSandBox == null) {
            return null;
        }
        return codeSandBox.executeCode(executeCodeRequest);
    }
}
