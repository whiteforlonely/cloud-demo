package com.ake.chat.security.demo;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2025/5/16 20:24
 */
public class HmacTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        String secretKey = "secretKey";
        byte[] keyBytes = secretKey.getBytes();
        // 创建一个密钥
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "HmacSHA256");

        // 创建一个mac对象
        Mac mac = Mac.getInstance("HmacSHA256");
        // 初始化mac
        mac.init(keySpec);

        // 消息
        String message = "hello world";
        byte[] messageBytes = message.getBytes();
        byte[] digest = mac.doFinal(messageBytes);

        // 将消息转成十六进制字符串
        String result = String.format("%0"+digest.length*2+"x", new BigInteger(1, digest));
        System.out.println("HMAC("+message+") = " + result);
    }
}
