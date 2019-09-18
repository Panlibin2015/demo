package com.lbpan.demo.nio.timeserver.processor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

public class WritableProcessor extends AbstractSocketChannelProcessor {

    public WritableProcessor(SelectionKey selectionKey) {
        super(selectionKey);
    }

    @Override
    public void process() throws IOException {
        String responseMsg = "欢迎回到神之领域,愿有你的荣耀,永不散场!";
        ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
        byteBuffer.put(responseMsg.getBytes("UTF-8"));
        byteBuffer.flip();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        socketChannel.write(byteBuffer);
        socketChannel.shutdownOutput();
        socketChannel.finishConnect();
    }
}
