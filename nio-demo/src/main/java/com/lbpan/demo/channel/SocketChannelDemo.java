package com.lbpan.demo.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class SocketChannelDemo {

    public static void main(String[] args) throws IOException, InterruptedException {
        Selector selector = Selector.open();

        ServerSocketChannel servChannel = ServerSocketChannel.open();
        servChannel.configureBlocking(true); // 必须是非阻塞模式
        servChannel.socket().bind(new InetSocketAddress(8080), 1);

        while (selector.isOpen()) {
//            SocketChannel accept = servChannel.accept();
//            System.out.println(accept);
//            if (accept != null) {
//                System.out.println(accept != null ? accept.toString() : "");
//                accept.register(selector, SelectionKey.OP_READ);
//            }
//            selector.select(1000);
//            Set<SelectionKey> selectionKeys = selector.selectedKeys();
//            System.out.println(selectionKeys.toString());
//
//            Iterator<SelectionKey> iterator = selectionKeys.iterator();
//            while (iterator.hasNext()) {
//                SelectionKey nextKey = iterator.next();
//            }
        }

        SocketChannelDemo.class.wait();

    }
}
