package com.seahouse.compoment.utils.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * <ul>
 * <li>文件名称：HttpClientCloseConnection</li>
 * <li>文件描述：暂无描述</li>
 * <li>版权所有：版权所有(C) 2019</li>
 * <li>公 司：厦门云顶伟业信息技术有限公司</li>
 * <li>内容摘要：</li>
 * <li>其他说明：</li>
 * <li>创建日期：2020/1/14 22:51</li>
 * </ul>
 *
 * <ul>
 * <li>修改记录：</li>
 * <li>版 本 号：</li>
 * <li>修改日期：</li>
 * <li>修 改 人：</li>
 * <li>修改内容：</li>
 * </ul>
 *
 * @author majx
 * @version 1.0.0
 */
@Component//交给spring容器管理
public class HttpClientCloseConnection extends Thread{

    @Autowired
    private PoolingHttpClientConnectionManager manage;
    //开关 volatitle表示多线程可变数据,一个线程修改,其他线程立即修改
    private volatile boolean shutdown;
    //等待10秒
    private long intervalTime = 10000;


    public HttpClientCloseConnection() {
        ///System.out.println("执行构造方法,实例化对象");
        //线程开启启动
        this.start();
    }

    @Override
    public void run() {
        try {
            //如果服务没有关闭,执行线程
            while(!shutdown) {
                synchronized (this) {
                    wait(intervalTime);
                    //System.out.println("线程开始执行,关闭超时链接");
                    //关闭超时的链接
                    PoolStats stats = manage.getTotalStats();
                    //获取可用的线程数量
                    int av = stats.getAvailable();
                    //获取阻塞的线程数量
                    int pend = stats.getPending();
                    //获取当前正在使用的链接数量
                    int lea = stats.getLeased();
                    int max = stats.getMax();
                    System.err.println("server status:【httpclient线程使用情况：空闲持久线程："+av+" 阻塞线程："+pend+" 使用中线程："+lea+" 最大线程："+max+"】");
                    manage.closeExpiredConnections();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        super.run();
    }
    //关闭清理无效连接的线程
    @PreDestroy  //容器关闭时执行该方法.
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            //System.out.println("关闭全部链接!!");
            notifyAll(); //全部从等待中唤醒.执行关闭操作;
        }
    }
}
