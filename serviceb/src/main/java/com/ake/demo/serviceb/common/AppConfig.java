package com.ake.demo.serviceb.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ake")
public class AppConfig {

    private String home = "CHINA";
}
