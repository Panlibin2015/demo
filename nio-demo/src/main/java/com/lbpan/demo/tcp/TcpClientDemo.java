package com.lbpan.demo.tcp;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TcpClientDemo {
    private static AtomicInteger count = new AtomicInteger(0);
    private static final List list = new LinkedList<>();

    public static void main(String[] args) {
        final int port = 9098;

        BufferedReader in = null;
        int i = 200;
        try {
            while (i > 0) {
                i--;

                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                while (true) {
                                    int i = count.incrementAndGet();
                                    System.out.println(i);
                                    list.add(new Socket());
                                    Socket socket = new Socket("localhost", port);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
