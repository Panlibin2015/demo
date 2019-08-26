package com.lbpan.demo.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

public class IDataCallback implements AsyncCallback.DataCallback {

    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        System.out.println("Get znode data: [response code:" + rc + ",param path:" + path + ",ctx:" + ctx + ",data:" + new String(data) + ",stat:" + stat);
    }
}
