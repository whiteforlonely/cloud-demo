package com.ake.demo.servicea.controller;

import cn.hutool.json.JSONUtil;
import com.ake.demo.servicea.common.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    AppConfig appConfig;

    @GetMapping("/home")
    public String testHome(){
        log.info("[test] akeConfig: {}", JSONUtil.toJsonStr(appConfig));
        return appConfig.getHome();
    }
}
