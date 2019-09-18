package com.lbpan.demo.zookeeper.zkdatabase;

import org.apache.zookeeper.data.ACL;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SnapShotReaderTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File("/Users/lbpan/nfsq/demo/zookeeper-demo/src/main/resources/snapshot.300000000");

        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        int magic = dataInputStream.readInt();
        int version = dataInputStream.readInt();
        long dbid = dataInputStream.readLong();
        System.out.println("magic:" + magic);
        System.out.println("version:" + version);
        System.out.println("dbid:" + dbid);

        System.out.println("---------------------------");

        int count = dataInputStream.readInt();
        System.out.println("count:" + count);
        while (count > 0) {
            System.out.println("***");
            long id = dataInputStream.readLong();
            int sd = dataInputStream.readInt();
            System.out.println("id:" + id);
            System.out.println("sd:" + sd);
            count--;
        }

        System.out.println("--------------------------");
        int map = dataInputStream.readInt();
        System.out.println("map:" + map);
        while (map > 0) {
            Long aclIndex = dataInputStream.readLong();
            System.out.println("long:" + aclIndex);
            List<ACL> aclList = new ArrayList<ACL>();
            int acls = dataInputStream.readInt();
            System.out.println("acls:" + acls);

            while (acls > 0) {
                System.out.println("***ACL");
                int perms = dataInputStream.readInt();
                System.out.println("perms:" + perms);
                int schemeLen = dataInputStream.readInt();
                byte[] schemeLenByte = new byte[schemeLen];
                dataInputStream.read(schemeLenByte);
                System.out.println("schema:" + new String(schemeLenByte));

                int idLen = dataInputStream.readInt();
                byte[] idLenByte = new byte[idLen];
                dataInputStream.read(idLenByte);
                System.out.println("id:" + new String(idLenByte));
                acls--;
            }
            map--;
        }
        System.out.println("--------------------------");

        int pathLen = dataInputStream.readInt();
        byte[] pathBytes = new byte[pathLen];
        dataInputStream.readFully(pathBytes);
        String path = new String(pathBytes);
        System.out.println("path:" + new String(pathBytes));
        while (!"/".equals(path)) {
            System.out.println("***Node:" + dataInputStream.available());
            int bufferLen = dataInputStream.readInt();
            if (bufferLen != -1) {
                byte[] bufferBytes = new byte[bufferLen];
                dataInputStream.read(bufferBytes);
                System.out.println("buffer:" + Arrays.toString(bufferBytes));
            } else {
                System.out.println("buffer:[]");
            }
            long acl = dataInputStream.readLong();
            System.out.println("acl:" + acl);

            long czxid = dataInputStream.readLong();
            System.out.println("czxid:" + czxid);
            long mzxid = dataInputStream.readLong();
            System.out.println("mzxid:" + mzxid);
            long ctime = dataInputStream.readLong();
            System.out.println("ctime:" + ctime);
            long mtime = dataInputStream.readLong();
            System.out.println("mtime:" + mtime);
            version = dataInputStream.readInt();
            System.out.println("version:" + version);
            long cversion = dataInputStream.readInt();
            System.out.println("cversion:" + cversion);
            long aversion = dataInputStream.readInt();
            System.out.println("aversion:" + aversion);
            long ephemeralOwner = dataInputStream.readLong();
            System.out.println("ephemeralOwner:" + ephemeralOwner);
            long pzxid = dataInputStream.readLong();
            System.out.println("pzxid:" + pzxid);

            int pathLen2 = dataInputStream.readInt();
            if (pathLen2 == -1) {
                path = null;
            } else {
                byte[] pathBytes2 = new byte[pathLen2];
                dataInputStream.readFully(pathBytes2);
                System.out.println(Arrays.toString(pathBytes2));
                path = new String(pathBytes2);
                System.out.println();
                System.out.println("path:" + new String(pathBytes2));
            }
        }

    }
}
