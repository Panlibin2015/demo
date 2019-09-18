package com.lbpan.demo.nio.timeserver;

import com.lbpan.demo.nio.timeserver.processor.Processor;
import com.lbpan.demo.nio.timeserver.processor.ProcessorFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 连接器
 */
public class Acceptor implements Runnable {

    /**
     * ServerSocket 通道
     */
    private ServerSocketChannel serverSocketChannel;
    /**
     * 多路复用选择器
     */
    private Selector selector;
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(
            5,
            10,
            10L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue());

    public Acceptor(int port) {
        try {
            // 打开Selector
            this.selector = Selector.open();

            // 打开ServerSocket通道
            this.serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); // 非阻塞
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024); // 监听端口

            // 注册Accept事件监听到Selector
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Acceptor acceptor = new Acceptor(8080);
        acceptor.run();
    }

    @Override
    public void run() {
        while (true) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> selectionKeyIterator = selectionKeys.iterator();
                while (selectionKeyIterator.hasNext()) {
                    SelectionKey selectionKey = selectionKeyIterator.next();
                    Processor processor = ProcessorFactory.processor(selectionKey);
                    executor.execute(processor);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
