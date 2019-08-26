package com.lbpan.demo.zookeeper;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ZkClientOptional {

    private ZkClient zkClient;

    @Before
    public void before() {
        zkClient = new ZkClient("localhost:2181", 5000);
        System.out.println("ZooKeeper session established");
    }

    /**
     * 创建节点
     */
    @Test
    public void createNode() {
        String path = "/zk_client_book";
        zkClient.createPersistent(path);
    }

    /**
     * 删除节点
     */
    @Test
    public void deleteNode() {
        String path = "/zk_client_book";
        zkClient.delete(path);
    }

    /**
     * 获取节点
     */
    @Test
    public void getChildNodes() throws InterruptedException {
        final String path = "/zk_client_book";
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> list) throws Exception {
                System.out.println(parentPath + " 's child changed,currentChilds:" + list);
            }
        });

        zkClient.createPersistent(path);
        Thread.sleep(1000);
        System.out.println(zkClient.getChildren(path));
        Thread.sleep(1000);
        zkClient.createPersistent(path + "/c1");
        Thread.sleep(1000);
        zkClient.delete(path + "/c1");
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(3000);
    }

    /**
     * 获取节点数据
     */
    @Test
    public void getNodeData() throws InterruptedException {
        String path = "/zk_client_book1";
        zkClient.createEphemeral(path, "123");
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("Node " + dataPath + " changed, new data:" + data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("Node " + dataPath + " deleted");
            }
        });

        System.out.println(zkClient.readData(path));
        zkClient.writeData(path, "456"); // 更新数据

        if (zkClient.exists(path)) { // 节点是否存在
            System.out.println(path + ":存在");
        }
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(3000);
    }


}
