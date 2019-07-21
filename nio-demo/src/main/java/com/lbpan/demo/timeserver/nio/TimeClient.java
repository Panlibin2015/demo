package com.lbpan.demo.timeserver.nio;

public class TimeClient {

    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (Exception e) {

            }
        }

        new Thread(new TimeClientHandler("localhost", port), "TimeClient-001").start();
    }
}
