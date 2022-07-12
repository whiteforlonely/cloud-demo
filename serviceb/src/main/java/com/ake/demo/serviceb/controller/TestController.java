package com.ake.demo.serviceb.controller;

import cn.hutool.json.JSONUtil;
import com.ake.demo.serviceb.common.AppConfig;
import com.ake.demo.serviceb.model.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    AppConfig appConfig;
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/home")
    public String testHome(){
        log.info("[test] akeConfig: {}", JSONUtil.toJsonStr(appConfig));
        UserDTO user = new UserDTO();
        user.setId(20);
        user.setUserEmail("695774301@qq.com");
        user.setUserNick("testUserName");
        return restTemplate.postForObject("http://servicea/test/home", user, String.class);
    }
}
