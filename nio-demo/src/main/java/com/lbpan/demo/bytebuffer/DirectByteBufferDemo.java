package com.lbpan.demo.bytebuffer;

import java.nio.ByteBuffer;

public class DirectByteBufferDemo {

    public static void main(String[] args) {
//        boolean booted = VM.isBooted();
//        System.out.println(booted);
//        long l = VM.maxDirectMemory();
//        System.out.println(l);
//
//        boolean pa = VM.isDirectMemoryPageAligned();
//
//        System.out.println(pa);
//        用法与HeapByteBuffer 完全相同
//
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024 * 1024);

        byteBuffer.put("Byte Buffer".getBytes());
        byteBuffer.flip();
        int limit = byteBuffer.limit();
        for (int i = 0; i < limit; i++) {
            System.out.print((char) byteBuffer.get());
        }
    }

}
