package com.demo.lingsicodesandbox.utils;

import cn.hutool.core.util.CharsetUtil;
import com.demo.lingsicodesandbox.entity.ExecuteMessage;
import lombok.extern.log4j.Log4j;
import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RunProcessUtil {

    public static ExecuteMessage runProcess(Process process, String opName) throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ExecuteMessage executeMessage = new ExecuteMessage();
        int exitValue = process.waitFor();
        System.out.println("exitValue: " + exitValue);
        executeMessage.setExitValue(exitValue);
        // 正常退出
        if (exitValue == 0) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            System.out.println(stringBuilder);
            executeMessage.setMessage(stringBuilder.toString());
            System.out.println(opName + "成功！");
        } else {
            BufferedReader bufferedReader = null;
            if ("编译".equals(opName)) {
                bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), CharsetUtil.GBK));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8));
            }
            StringBuilder stringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
            System.out.println(stringBuilder);
            executeMessage.setMessage(stringBuilder.toString());
            System.out.println(opName + "失败！");
        }
        stopWatch.stop();
        executeMessage.setTime(stopWatch.getTotalTimeMillis());
        return executeMessage;
    }
}
