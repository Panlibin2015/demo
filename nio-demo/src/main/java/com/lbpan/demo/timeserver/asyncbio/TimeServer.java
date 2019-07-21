package com.lbpan.demo.timeserver.asyncbio;

import com.lbpan.demo.timeserver.bio.TimeServerHandler;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 伪异步IO
 * 早期Tomcat 使用该模型
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (Exception e) {

            }
        }

        ServerSocket server = null;

        try {
            server = new ServerSocket(port);
            System.out.println("This time server is start in port:" + port);
            Socket socket = null;
            TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50, 10000);//创建I/O线程池

            while (true) {
                socket = server.accept();
                singleExecutor.execute(new TimeServerHandler(socket));
            }


        } catch (Exception e) {


        }
    }
}
