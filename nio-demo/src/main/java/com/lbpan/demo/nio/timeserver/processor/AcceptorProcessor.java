package com.lbpan.demo.nio.timeserver.processor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class AcceptorProcessor extends AbstractProcessor {

    private ServerSocketChannel serverSocketChannel;

    private SocketChannel socketChannel;

    public AcceptorProcessor(SelectionKey selectionKey) {
        super(selectionKey);
        this.serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        if (selectionKey.isValid()) {
            if (selectionKey.isAcceptable()) {
                try {
                    this.socketChannel = serverSocketChannel.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void process() {
        try {
            if (socketChannel != null) {
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
