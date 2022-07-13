package com.ake.demo.servicea.starterup;

import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.*;

@Slf4j
@Component
public class RegisterServiceStarter implements CommandLineRunner {

    @Autowired
    NacosConfigProperties nacosConfigProperties;

    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${server.port}")
    private int serverPort;

    @Override
    public void run(String... args) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("namespace", nacosConfigProperties.getNamespace());
        properties.setProperty("serverAddr", nacosConfigProperties.getServerAddr());
        NamingService namingService = NamingFactory.createNamingService(properties);
        Instance instance = new Instance();
        instance.setEnabled(true);
        instance.setServiceName(applicationName);
//        instance.setInstanceId("1");
        Map<String, String> metadata = new HashMap<>();
        metadata.put("serverId", "1");
        instance.setMetadata(metadata);
        instance.setIp(InetAddress.getLocalHost().getHostAddress());
        instance.setPort(serverPort);
        namingService.registerInstance(applicationName, "LOCAL", instance);
        log.info("Register service ok!");
    }

    private void getChildren(int code, List<RestModel> list, Map<Integer, TempModel> data){

        RestModel restModel = new RestModel();
        restModel.setCode(code);
        list.add(restModel);
        TempModel model= data.get(code);
        if (null != model) {
            if (model.getSubCode1() != 0) {
                if (restModel.getChildren() == null) restModel.setChildren(new ArrayList<>());
                getChildren(model.getSubCode1(), restModel.getChildren(), data);
            }
            if (model.getSubCode2() != 0) {
                if (restModel.getChildren() == null) restModel.setChildren(new ArrayList<>());
                getChildren(model.getSubCode2(), restModel.getChildren(), data);
            }
            if (model.getSubCode3() != 0) {
                if (restModel.getChildren() == null) restModel.setChildren(new ArrayList<>());
                getChildren(model.getSubCode3(), restModel.getChildren(), data);
            }
        }
    }

    @Data
    public static class TempModel{
        int code;
        int subCode1;
        int subCode2;
        int subCode3;

        public TempModel(int code, int code1, int code2, int code3) {
            this.code = code;
            this.subCode1 = code1;
            this.subCode2 = code2;
            this.subCode3 = code3;
        }

        List<TempModel> children;
    }

    @Data
    public static class RestModel{
        int code;
        List<RestModel> children;
    }

    public static void main(String[] args) {
        TempModel model1 = new TempModel(1, 2, 3, 4);
        TempModel model2 = new TempModel(2, 3, 5, 6);
        TempModel model3 = new TempModel(3, 7, 8, 0);
        TempModel model4 = new TempModel(4, 0, 0,0);
        TempModel model5 = new TempModel(7, 9, 10, 0);
        TempModel model6 = new TempModel(9, 11, 12, 13);
        TempModel model7 = new TempModel(10, 13, 14, 15);

        Map<Integer, TempModel> tempModelMap = new HashMap<>();
        tempModelMap.put(model1.getCode(), model1);
        tempModelMap.put(model2.getCode(), model2);
        tempModelMap.put(model3.getCode(), model3);
        tempModelMap.put(model4.getCode(), model4);
        tempModelMap.put(model5.getCode(), model5);
        tempModelMap.put(model6.getCode(), model6);
        tempModelMap.put(model7.getCode(), model7);

        List<RestModel> list = new ArrayList<>();
        new RegisterServiceStarter().getChildren(1, list, tempModelMap);
        System.out.println(JSONUtil.toJsonPrettyStr(list));
    }
}
