package com.lbpan.demo.channel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelWrite {

    public static void main(String[] args) throws IOException {
        File file = new File("nio-demo/src/main/resources/nio.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file, true);
        FileChannel fileChannel = fos.getChannel();
        boolean open = fileChannel.isOpen();
        if (open) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put("欢迎回到神之领域,愿有你的荣耀,永不散场!".getBytes());

            byteBuffer.flip();
            fileChannel.write(byteBuffer);
        }

        fileChannel.close();
    }
}
