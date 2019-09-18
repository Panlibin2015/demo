package com.lbpan.demo.nio.timeserver.processor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public class ReaderProcessor extends AbstractSocketChannelProcessor {

    public ReaderProcessor(SelectionKey selectionKey) {
        super(selectionKey);
    }

    @Override
    public void process() throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int read = socketChannel.read(byteBuffer);
        byte[] bytes = new byte[byteBuffer.limit()];
        System.out.println("请求内容:" + new String(bytes, "UTF-8"));

        socketChannel.shutdownInput();
        socketChannel.register(selector, SelectionKey.OP_WRITE);
    }
}
