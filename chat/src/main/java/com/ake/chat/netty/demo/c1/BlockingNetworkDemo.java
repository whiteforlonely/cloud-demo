package com.ake.chat.netty.demo.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2025/5/7 14:32
 */
public class BlockingNetworkDemo {

    private void startServer(int port) {
        // 创建一个监听端口，用于接收客户端链接过来的socket请求
        try (ServerSocket serverSocket = new ServerSocket(port)){
            //  等待客户端的链接，并且是处理多个链接的。
            while (true) {
                // 这是一个阻塞的方法，直到有链接请求进来
                Socket socket = serverSocket.accept();
                // 链接请求进来后，可以获取其对应的socket，并且socket中有对应的inputstream和outputstream流可以交换数据
                // TODO 或许这边应该是有一个对应的线程池，这个线程池是一致的监听者客户端发送过来的socket的，并且阔这他对应的业务逻辑
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                while (true) {
                    // 这样子的行为看起来是可行的，但其实这个不是高效的，可以对其进行封装
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 这个本身也是阻塞型的，所以才可以这样子处理的吧，当链接越来越多的时候，这种阻塞确实是一种资源的浪费
                    String request, response;
                    // 真的是太久没有写java代码，现在都快忘了怎么写了，这个是非常恐怖的事情了。
                    // 独立客户端请求过来的数据流
                    while ((request = in.readLine()) != null) {
                        if (request.isEmpty()) {
                            break;
                        }
                        System.out.println("Server received: " + request);
                        response = processRequest(request);
                        out.println(response);
                        out.flush();
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error occurred while processing request: " + e.getMessage());
        }


    }

    private String processRequest(String request) {
        return "Hello, client";
    }

    private void startClient(String host, int port, String message) {
        try (Socket socket = new Socket(host, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            for (int i = 0; i < 10; i++) {
                out.println(message);
                out.flush();
                String response = in.readLine();
                System.out.println("Client received "+ i +": " + response);
            }

        } catch (IOException e) {
            System.out.println("Error occurred while processing request: " +  e.getMessage());
        }
    }

    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> {
            BlockingNetworkDemo server = new BlockingNetworkDemo();
            server.startServer(8080);
        });
        // 启动服务线程
        serverThread.start();

        // 创建多个的客户端进行连接
        for (int i = 0; i < 1; i++) {
            final int i1 = i;
            Thread clientThread = new Thread(() -> {
                BlockingNetworkDemo client = new BlockingNetworkDemo();
                client.startClient("localhost", 8080,  "Hello, server" + i1);
            });
            clientThread.start();
        }
    }
}
