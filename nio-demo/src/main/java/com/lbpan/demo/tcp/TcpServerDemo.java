package com.lbpan.demo.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class TcpServerDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        List<Socket> socketList = new LinkedList<>();
        //        可见backlog在Linux 2.2之后表示的是已完成三次握手但还未被应用程序accept的队列长度。Mac 默认是50

        ServerSocket serverSocket = new ServerSocket(9098, 100);
        Socket socket = null;
//        serverSocket.accept();
        while (true) {
//            System.out.println("监听端口:9098");
//            socket = serverSocket.accept();
//            socketList.add(socket);
//            System.out.println(socketList.size());
        }
    }

}
