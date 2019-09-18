package com.lbpan.demo.channel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelRead {

    public static void main(String[] args) throws IOException {
//        FileDescriptor var0, String var1, boolean var2, boolean var3, Object var4
//        FileDescriptor fileDescriptor = new FileDescriptor();
//        FileChannel fileChannel = new FileChannelImpl(fileDescriptor,"",);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        FileChannel fc = new FileInputStream("nio-demo/src/main/resources/nio.txt").getChannel();

//        FileChannel channel = FileChannelImpl.open(fd, path, true, false, this);

        System.out.println("读取日志文件...");
        System.out.println("读取日志文件..." + fc.size());
        long fileSize = fc.size();
        while (fc.isOpen() && fileSize > 0) {
            int read = fc.read(byteBuffer);
            fileSize -= read;

            byteBuffer.flip(); // 切换为读
            byte[] bytes = new byte[byteBuffer.limit()];
            byteBuffer.get(bytes);
            String string = new String(bytes, "UTF-8");
            System.out.print(string);

            byteBuffer.clear(); // 重置
        }

        fc.close();
    }

}
