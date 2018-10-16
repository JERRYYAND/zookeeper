package com.zg.zookeeper.starter.zookeeper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Perms;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class ACLDemo {
	private static String connectstr="192.168.199.216:2181,192.168.199.209:2181,192.168.199.201:2181";
	private static CountDownLatch cdl=new CountDownLatch(1);
	public static void main(String[] args) {
		try {
			//这个监听是我们new出来的,是作用在client这个会话,但是这个watcher并没有指定监听那个Znode
			ZooKeeper client=new ZooKeeper(connectstr, 5000, new WatcherDemo(cdl));
			cdl.await();
			//
			ACL acl=new ACL(Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest("xyl:123")));
			List<ACL> acls=new ArrayList<ACL>();
			acls.add(acl);
			client.create("/acl", "acl".getBytes(), acls, CreateMode.PERSISTENT);
			//下面的客户端就需要进行权限的授权
			ZooKeeper client1=new ZooKeeper(connectstr, 5000, new WatcherDemo(cdl));
			cdl.await();
			//这是一个授权的方法
			client1.addAuthInfo("digest", "xyl:123".getBytes());
			client1.setData("/acl", "xxx吧".getBytes(), -1);
			
			ZooKeeper client2=new ZooKeeper(connectstr, 5000, new WatcherDemo(cdl));
			cdl.await();
			client2.setData("/acl", "xxx哦ooooo吧".getBytes(), -1);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
