package com.lbpan.demo.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.ZooKeeperServer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZookeeperOptional {

    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    private ZooKeeper zooKeeper;

    @Before
    public void before() throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper("localhost:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("Receive watched event:" + watchedEvent);
                if (Event.KeeperState.SyncConnected == watchedEvent.getState() && watchedEvent.getType() == Event.EventType.None) {
                    countDownLatch.countDown();
                }

                if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged) {
                    try {
                        System.out.println(zooKeeper.getChildren(watchedEvent.getPath(), true));
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (Event.EventType.NodeCreated == watchedEvent.getType()) {
                    System.out.println("Node(" + watchedEvent.getPath() + ")Created");
                    try {
                        zooKeeper.exists(watchedEvent.getPath(), true);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (Event.EventType.NodeDeleted == watchedEvent.getType()) {
                    System.out.println("Node(" + watchedEvent.getPath() + ")Deleted");
                    try {
                        zooKeeper.exists(watchedEvent.getPath(), true);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else if (Event.EventType.NodeDataChanged == watchedEvent.getType()) {
                    System.out.println("Node(" + watchedEvent.getPath() + ")Changed");
                    try {
                        zooKeeper.exists(watchedEvent.getPath(), true);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        System.out.println(zooKeeper.getState());
        countDownLatch.await();
        System.out.println("ZooKeeper session established.");
    }

    /**
     * 创建Zookeeper会话
     *
     * @throws InterruptedException
     * @throws IOException
     */
    @Test
    public void createSession() throws InterruptedException, IOException {
        ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("Receive watched event:" + watchedEvent);
                if (Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                    countDownLatch.countDown();
                }
            }
        });

        System.out.println(zooKeeper.getState());
        countDownLatch.await();
        System.out.println("ZooKeeper session established.");
    }

    /**
     * 同步方式创建节点
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void createNode() throws KeeperException, InterruptedException {
        String path01 = zooKeeper.create("/zookeeper_demo", "zookeeper".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create znode:" + path01);

        String path02 = zooKeeper.create("/zookeeper_demo02", "zookeeper".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("Success create znode:" + path02);
        Thread.sleep(30000);
    }

    /**
     * 异步方式创建节点
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void createNodeAsync() throws KeeperException, InterruptedException {
        zooKeeper.create("/zookeeper_demo", "zookeeper".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL,
                new ISStringCallback(), "i am context1.");

        zooKeeper.create("/zookeeper_demo02", "zookeeper".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL,
                new ISStringCallback(), "i am context2.");
        Thread.sleep(30000);
    }

    public void deleteNode() {
//        zooKeeper.delete("/");
    }


    /**
     * 获取节点
     */
    @Test
    public void getNodes() throws KeeperException, InterruptedException {
        String path = "/zk-book3";
        String path01 = zooKeeper.create(path, "zookeeper".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("Success create znode:" + path01);

        String path02 = zooKeeper.create(path + "/zookeeper_demo02", "zookeeper".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Success create znode:" + path02);

        List<String> children = zooKeeper.getChildren(path, true);
        System.out.println(children);

        zooKeeper.create(path + "/zookeeper_p03", "zookeeper03".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(3000);
    }

    /**
     * 异步获取节点
     */
    @Test
    public void getNodesAsync() throws KeeperException, InterruptedException {
        String path = "/zk-book3";
        zooKeeper.create(path + "/zookeeper_p03", "zookeeper03".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getChildren(path, true, new IChildren2CallBack(), "获取节点");
        Thread.sleep(3000);
    }

    /**
     * 同步获取节点数据
     *
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void getNodeData() throws KeeperException, InterruptedException {
        String path = "/zk-book4";
        String path01 = zooKeeper.create(path, "zookeeper".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        byte[] data = zooKeeper.getData(path, true, new Stat());
        System.out.println(new String(data));
    }

    /**
     * 异步获取数据节点
     */
    @Test
    public void getNodeDataAsync() throws KeeperException, InterruptedException {
        String path = "/zk-book4";
        String path01 = zooKeeper.create(path, "zookeeper".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        zooKeeper.getData(path, true, new IDataCallback(), "异步获取数据");
        Thread.sleep(3000);
    }

    /**
     * 同步更新数据
     */
    @Test
    public void updateData() throws KeeperException, InterruptedException {
        String path = "/zk-book4";
        String path01 = zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        Stat stat = zooKeeper.setData(path, "456".getBytes(), -1); // 如果对于数据更新没有原子性要求就可以用-1，表示使用最新版本号更新
        System.out.println(stat.getCzxid() + "," + stat.getMzxid() + "," + stat.getVersion());
        Stat stat1 = zooKeeper.setData(path, "456".getBytes(), stat.getVersion());
        System.out.println(stat1.getCzxid() + "," + stat1.getMzxid() + "," + stat1.getVersion());

        try {
            zooKeeper.setData(path, "456".getBytes(), stat.getVersion());
        } catch (KeeperException e) {
            System.out.println("Error:" + e.code() + "," + e.getMessage());
        }

        Thread.sleep(3000);
    }

    /**
     * 同步更新数据
     */
    @Test
    public void updateDataAsync() throws KeeperException, InterruptedException {
        String path = "/zk-book4";
        String path01 = zooKeeper.create(path, "123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        zooKeeper.setData(path, "456".getBytes(), -1, new IStatCallback(), "第一次更新数据"); // 如果对于数据更新没有原子性要求就可以用-1，表示使用最新版本号更新
        zooKeeper.setData(path, "456".getBytes(), -1, new IStatCallback(), "第二次更新数据");

        Thread.sleep(3000);
    }

    /**
     * 检测节点是否存在
     */
    @Test
    public void existsNode() throws KeeperException, InterruptedException {
        String path = "/zk-book4";
        zooKeeper.exists(path, true);

        zooKeeper.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.setData(path, "123".getBytes(), -1);

        zooKeeper.create(path + "/c1", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        zooKeeper.delete(path + "/c1", -1);
        zooKeeper.delete(path, -1);

        Thread.sleep(3000);
    }

    /**
     * 权限控制
     *
     * @throws IOException
     */
    @Test
    public void aclNode() throws IOException, KeeperException, InterruptedException {
        String path = "/zk-book4";

        ZooKeeper zookeeper1 = new ZooKeeper("localhost:2181", 5000, null);
        zookeeper1.addAuthInfo("digest", "foo:true".getBytes());
        zookeeper1.create(path, "init".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);

        zooKeeper.getData(path, false, null);
    }

}
