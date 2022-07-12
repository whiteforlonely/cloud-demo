package com.ake.demo.servicea.common.starter;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.NacosNamingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class NacosStarter implements CommandLineRunner {

    @Autowired
    NacosConfigProperties nacosConfigProperties;

    private Map<String, Integer> instanceMap;

    @Override
    public void run(String... args) throws Exception {

        // 配置对应的serverId
        Properties properties = new Properties();
        properties.setProperty("serverAddr", nacosConfigProperties.getServerAddr());
        properties.setProperty("namespace", nacosConfigProperties.getNamespace());
        log.info("=============> properties ===============> {}", properties);
        NacosNamingService nacosNamingService = (NacosNamingService) NamingFactory.createNamingService(properties);
        nacosNamingService.subscribe("servicea", nacosConfigProperties.getGroup(), event -> {
            log.info("=============> nacos substribe event===============>{}", instanceMap);
            if (event instanceof NamingEvent) {
                NamingEvent namingEvent = (NamingEvent) event;
                List<Instance> instanceList = namingEvent.getInstances();
                if (instanceMap == null) {
                    instanceMap = new HashMap<>();
                    // 说明是第一次创建了自己
                    for (Instance instance : namingEvent.getInstances()) {
                        instanceMap.put(instance.getInstanceId(), (int) (Math.random()*100));
                    }
                    return;
                }
                Set<String> keys = new HashSet<>();
                for (Instance instance : instanceList) {
                    keys.add(instance.getInstanceId());
                }
                if (instanceList.size() > instanceMap.size()) {
                    // 说明是新增节点
                    keys.removeAll(instanceMap.keySet());
                    log.info("NacosStarter, add new instance: {}", keys);
//                    for (Instance instance : instanceList) {
//                        if (keys.contains(instance.getInstanceId())) {
//                            instance.addMetadata("serverId", String.valueOf((int) (Math.random() * 100)));
//                        }
//                    }

                } else if (instanceMap.size() > instanceList.size()){
                    Set<String> removedInstanceSet = new HashSet<>(instanceMap.keySet());
                    removedInstanceSet.removeAll(keys);
                    log.info("NacosStarter, remove instance: {}", removedInstanceSet);
                }
                // 清空，组装数据
                instanceMap.clear();
                for (Instance instance : namingEvent.getInstances()) {
                    instanceMap.put(instance.getInstanceId(), (int) (Math.random()*100));
                }
//                log.info("NacosStarter, instances={}", namingEvent.getInstances());
            }

        });
    }
}
