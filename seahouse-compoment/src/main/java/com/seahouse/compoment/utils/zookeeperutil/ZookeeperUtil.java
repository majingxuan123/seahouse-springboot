package com.seahouse.compoment.utils.zookeeperutil;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <ul>
 * <li>文件名称: ZookeeperUtil</li>
 * <li>文件描述:</li>
 * <li>版权所有: 版权所有(C) 2017</li>
 * <li>公 司: 厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要:</li>
 * <li>其他说明:</li>
 * <li>创建日期:2019/9/18</li>
 * </ul>
 * <ul>
 * <li>修改记录:</li>
 * <li>版 本 号:</li>
 * <li>修改日期:</li>
 * <li>修 改 人:</li>
 * <li>修改内容:</li>
 * </ul>
 *
 * @author majx
 * @version 1.0.0
 */
public class ZookeeperUtil {

    private ZooKeeper zooKeeper = null;

    @Before
    public void initZookeeper() throws IOException {
        zooKeeper = new ZooKeeper("master:2181,slave1:2181,slave2:2181,slave3:2181,slave4:2181,slave5:2181",
                2000, null);
    }

    @After
    public void closeZookeeper() throws InterruptedException {
        if (zooKeeper != null) {
            zooKeeper.close();
        }
    }

    public void create(String path, String value) throws KeeperException, InterruptedException {
        String s = zooKeeper.create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /**
     * @param path  路径
     * @param watch 是否需要监听
     * @param stat  获取数据版本  null表示最新版本
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     * @throws UnsupportedEncodingException
     */
    public String getData(String path, boolean watch, Stat stat) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        byte[] data = zooKeeper.getData(path, false, null);
        return new String(data, "utf-8");
    }

    public String getData(String path) throws KeeperException, InterruptedException, UnsupportedEncodingException {
        return getData(path, false, null);
    }

    /**
     *
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public List<String> testListChildren(String path) throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(path, false);
        for (String child : children) {
            System.out.println(child);
        }
        return children;
    }


    /**
     *
     * @param path
     * @throws KeeperException
     * @throws InterruptedException
     */
    public void delPath(String path) throws KeeperException, InterruptedException {
        zooKeeper.delete(path,-1);
    }

    /**
     * @param path    路径
     * @param value   值
     * @param version 版本号  如果-1代表任何版本
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     * @throws IOException
     */
    public String setData(String path, String value, int version) throws KeeperException, InterruptedException, IOException {
        Stat stat = zooKeeper.setData(path, value.getBytes(), version);
        return stat.toString();
    }


    @Test
    public void test() throws KeeperException, InterruptedException, IOException {
        System.out.println("****************");
//        String s = setData("/zktest", "我爱你");
        String s = getData("/zktest");
//        String s = zooKeeper.create("/zktest", "test zookeeper test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println(s);
        System.out.println("****************");
    }



}
