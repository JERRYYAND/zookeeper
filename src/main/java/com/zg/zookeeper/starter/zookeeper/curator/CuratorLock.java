package com.zg.zookeeper.starter.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * @author xyl
 * zk分布式锁实现-->curator
 */
public class CuratorLock {
    public static void main(String[] args) {
        CuratorFramework client= CuratorUtil.getInstance();
        client.start();
        InterProcessMutex interProcessMutex = new InterProcessMutex(client, "/curator/lock");
        try {
            //这个是获取锁
            interProcessMutex.acquire();
            //获取锁成功以后就可以进行业务操作了
            System.out.println("获取锁成功，进行业务操作-->!");
            TimeUnit.SECONDS.sleep(3);
            //释放锁
            interProcessMutex.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
