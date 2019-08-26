package com.lbpan.demo.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketOption;
import java.util.Scanner;

public class TcpClientDemo4 {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9098);
        socket.setOption()
        OutputStream outputStream = socket.getOutputStream();
        int i = 0;
        while (true) {
//            Scanner scanner = new Scanner(System.in);
//            String s = scanner.nextLine();
            System.out.println(i++);
            outputStream.write(1);
        }


    }
}
