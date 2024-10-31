package com.ake.demo.jvm;

import com.ake.demo.jvm.oom.HeapOOMDemo;
import com.ake.demo.jvm.oom.OOMObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

@SpringBootTest
class JvmApplicationTests {

    @Test
    void contextLoads() {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setUseCache(false);
            enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> proxy.invoke(obj, args1));
            enhancer.create();
        }
    }

}
