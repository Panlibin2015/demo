package com.lbpan.demo.channel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.Selector;

public class FileSelectorDemo {

    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        FileInputStream fileInputStream = new FileInputStream("nio-demo/src/main/resources/nio2.txt");
        FileChannel channel = fileInputStream.getChannel();
    }
}
