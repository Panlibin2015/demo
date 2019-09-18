package com.lbpan.demo.zookeeper.zkdatabase;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

public class TxnFileReaderTest {

    public static void main(String[] args) throws IOException {
//        File file = new File("/Users/lbpan/nfsq/es-demo/es-parent/zookeeper-demo/src/main/resources/log.200000003");
        File file = new File("/Users/lbpan/nfsq/demo/zookeeper-demo/src/main/resources/log.300000001");
        DataInputStream ia = new DataInputStream(new FileInputStream(file));
        int magic = ia.readInt();
        int version = ia.readInt();
        long dbid = ia.readLong();
        System.out.println("magic:" + magic);
        System.out.println("version:" + version);
        System.out.println("dbid:" + dbid);

        while (parse(ia)) {
        }

    }

    public static boolean parse(DataInputStream ia) throws IOException {
        System.out.println("***txtEntry");
        long crcvalue = ia.readLong();
        System.out.println("crcvalue:" + crcvalue);
        int len = ia.readInt();
        if (len != -1) {
            byte[] txtEntry = new byte[len];
            ia.read(txtEntry);
            if (txtEntry.length == 0) {
                return false;
            }
            System.out.println("txtEntry:" + Arrays.toString(txtEntry));
            byte b = ia.readByte();
            System.out.println("EOF:" + (char) b);

            Checksum crc = new Adler32();
            crc.update(txtEntry, 0, txtEntry.length);
            if (crcvalue != crc.getValue()) {
                System.out.println("crc error");
            }

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byteBuffer.put(txtEntry);
            byteBuffer.flip();
            long clientId = byteBuffer.getLong();
            System.out.println("clientId:" + clientId);
            int cxid = byteBuffer.getInt();
            System.out.println("cxid:" + cxid);
            long zxid = byteBuffer.getLong();
            System.out.println("zxid:" + zxid);
            long time = byteBuffer.getLong();
            System.out.println("time:" + time);
            long type = byteBuffer.getInt();
            System.out.println("type:" + type);

            if (type == -10) {
                // 创建节点事务
                int timeOut = byteBuffer.getInt();
                System.out.println("timeOut:" + timeOut);
            }
        }
        return true;
    }
}
