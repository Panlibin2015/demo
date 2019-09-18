package com.lbpan.demo.bytebuffer;

import java.nio.ByteBuffer;

public class HeapByteBufferDemo {

    public static void main(String[] args) {
        // HeapByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("Byte buffer".getBytes());
        byteBuffer.put(",Byte buffer".getBytes());
        byteBuffer.flip();// 切换ByteBuffer 到读模式
        // byteBuffer.duplicate();// 复制一个新的ByteBuffer
        byteBuffer.asReadOnlyBuffer();

        int limit = byteBuffer.limit();
        for (int i = 0; i < limit; i++) {
            byte b = byteBuffer.get();
            System.out.print((char) b);
        }

        System.out.println();
        System.out.println("-----------------------------------------");

        byteBuffer.put(",Byte Buffe".getBytes());
        byteBuffer.slice();

        limit = byteBuffer.limit();
        for (int i = 0; i < limit; i++) {
            byte b = byteBuffer.get();
            System.out.print((char) b);
        }

    }
}
