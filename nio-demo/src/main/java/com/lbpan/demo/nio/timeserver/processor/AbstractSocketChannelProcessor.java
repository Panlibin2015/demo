package com.lbpan.demo.nio.timeserver.processor;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public abstract class AbstractSocketChannelProcessor extends AbstractProcessor {

    protected SocketChannel socketChannel;

    public AbstractSocketChannelProcessor(SelectionKey selectionKey) {
        super(selectionKey);
        this.socketChannel = (SocketChannel) selectionKey.channel();
    }

}
