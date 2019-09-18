package com.lbpan.demo.bio;

import java.io.IOException;
import java.net.Socket;

public class Client {

    private static Socket[] clients = new Socket[30];

    public static void main(String[] args) throws IOException {
        for (int i = 1; i <= 30; i++) {

            clients[i - 1] = new Socket("127.0.0.1", 8080);
            System.out.println("client connection:" + i);
        }
    }

}
