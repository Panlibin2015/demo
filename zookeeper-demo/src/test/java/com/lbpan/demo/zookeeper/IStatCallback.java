package com.lbpan.demo.zookeeper;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

public class IStatCallback implements AsyncCallback.StatCallback {

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        if (rc == 0) {
            System.out.println("Update path result:[" + rc + ", version:" + stat.getVersion() + ",ctx " + ctx + ", " + "real path name:" + path);
        }
    }

}
