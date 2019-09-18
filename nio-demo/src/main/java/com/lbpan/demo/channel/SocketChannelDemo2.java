package com.lbpan.demo.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

/**
 * 只接受一个请求
 */
public class SocketChannelDemo2 {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 获取ServerSocket通道
        ServerSocketChannel servChannel = ServerSocketChannel.open();
        servChannel.configureBlocking(false); // 必须是非阻塞模式
        servChannel.socket().bind(new InetSocketAddress(8080), 1024);

        // 获取一个多路复用选择器
        Selector selector = Selector.open();
        servChannel.register(selector, SelectionKey.OP_ACCEPT);// 监听通道ACCEPT事件

        // 监听事件,直到获取一个Selector
        SelectionKey selectionKey = null;
        while (true) {
            selector.select(1000);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            while (selectionKeys.iterator().hasNext() && selectionKey == null) {
                selectionKey = selectionKeys.iterator().next();
                System.out.println(selectionKey);
                System.out.println("是否可读:" + selectionKey.isReadable() + ",是否连接:" + selectionKey.isConnectable()
                        + ",是否有效的：" + selectionKey.isValid() + ",是否可写的:" + selectionKey.isWritable() + ",是否可接受的:" + selectionKey.isAcceptable());
            }

            if (selectionKey != null) {
                break;
            }
        }

        // 获取一个套接字channel
        SelectableChannel channel = selectionKey.channel();
        // 由此可见,selectionKey中的channel中获取的channel与我们一开始打开的是一个对象
        System.out.println(channel.equals(servChannel));
        SocketChannel accept = ((ServerSocketChannel) channel).accept();
        accept.configureBlocking(false);// 必须是非阻塞的
        accept.register(selector, SelectionKey.OP_READ);
        selectionKey = null;
        while (true) {
            selector.select(1000);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            while (selectionKeys.iterator().hasNext() && selectionKey == null) {
                selectionKey = selectionKeys.iterator().next();
                if (!selectionKey.isReadable()) {
                    selectionKey = null;
                }
                System.out.println("是否可读:" + selectionKey.isReadable() + ",是否连接:" + selectionKey.isConnectable()
                        + ",是否有效的：" + selectionKey.isValid() + ",是否可写的:" + selectionKey.isWritable() + ",是否可接受的:" + selectionKey.isAcceptable());
            }

            if (selectionKey != null) {
                break;
            }
        }

        // 读取请求信息
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int read = socketChannel.read(byteBuffer);
        System.out.println(read);
        byteBuffer.flip();
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println(new String(bytes));
        socketChannel.shutdownInput();

        // 获取一个套接字channel
        // 由此可见,selectionKey中的channel中获取的channel与我们一开始打开的是一个对象
        System.out.println(channel.equals(servChannel));
        socketChannel.configureBlocking(false);// 必须是非阻塞的
        socketChannel.register(selector, SelectionKey.OP_WRITE);
        selectionKey = null;
        while (true) {
            selector.select(1000);
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            while (selectionKeys.iterator().hasNext() && selectionKey == null) {
                selectionKey = selectionKeys.iterator().next();
                if (!selectionKey.isWritable()) {
                    selectionKey = null;
                }
                System.out.println("是否可读:" + selectionKey.isReadable() + ",是否连接:" + selectionKey.isConnectable()
                        + ",是否有效的：" + selectionKey.isValid() + ",是否可写的:" + selectionKey.isWritable() + ",是否可接受的:" + selectionKey.isAcceptable());
            }

            if (selectionKey != null) {
                break;
            }
        }

        ByteBuffer rspByteBuffer = ByteBuffer.allocate(1024);
        rspByteBuffer.put("hello workd".getBytes());
        rspByteBuffer.flip();
        SocketChannel channel1 = (SocketChannel) selectionKey.channel();
        channel1.write(rspByteBuffer);
        channel1.shutdownOutput();
        channel1.finishConnect();

        Thread.sleep(1000);
    }
}
