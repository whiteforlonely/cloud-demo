package com.ake.demo.servicea;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Properties;

@Slf4j
@SpringBootTest
class ServiceaApplicationTests {

    @Test
    void contextLoads() throws NacosException {
        Properties properties = new Properties();
        properties.setProperty("namespace", "ef09c6fc-f58b-4225-93b7-367362eb6314");
        properties.setProperty("serverAddr", "localhost:8848");
        NamingService namingService = NamingFactory.createNamingService(properties);
        log.info("[all instance] {}", namingService.getAllInstances("servicea", "LOCAL"));
    }

}
