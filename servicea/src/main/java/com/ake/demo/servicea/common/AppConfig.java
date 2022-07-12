package com.ake.demo.servicea.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "ake")
public class AppConfig {

    private String home;
    private List<Integer> serverId;
}
