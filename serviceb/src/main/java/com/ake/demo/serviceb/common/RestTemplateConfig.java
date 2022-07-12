package com.ake.demo.serviceb.common;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 这边要注意的是，一定要加上对应的依赖，在现在的这个版本来处理的时候，是要这样子的。
 * 并在在restTemplate bean上面添加对应的@LoadBalanced注解，这样子就可以通过
 * 服务名称调用服务
 * <dependency>
 *   <groupId>org.springframework.cloud</groupId>
 *   <artifactId>spring-cloud-starter-loadbalancer</artifactId>
 * </dependency>
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
