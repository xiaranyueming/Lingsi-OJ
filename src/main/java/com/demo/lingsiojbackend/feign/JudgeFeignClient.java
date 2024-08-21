package com.demo.lingsiojbackend.feign;

import com.demo.lingsiojbackend.judge.codesandbox.entity.ExecuteCodeRequest;
import com.demo.lingsiojbackend.judge.codesandbox.entity.ExecuteCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "judge", url = "http://localhost:8081")
public interface JudgeFeignClient {

    @PostMapping("/judge/do")
    ExecuteCodeResponse doJudge(@RequestBody ExecuteCodeRequest executeCodeRequest);
}
