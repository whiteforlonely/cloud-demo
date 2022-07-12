package com.ake.demo.servicea.controller;

import cn.hutool.json.JSONUtil;
import com.ake.demo.servicea.common.AppConfig;
import com.ake.demo.servicea.model.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    AppConfig appConfig;

    @PostMapping("/home")
    public String testHome(@RequestBody UserDTO user){
        log.info("[test] akeConfig: {}", JSONUtil.toJsonStr(user));
        return appConfig.getHome();
    }
}
