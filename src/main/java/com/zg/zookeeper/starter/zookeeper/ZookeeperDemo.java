package com.zg.zookeeper.starter.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperDemo {

	private static String connectstr="192.168.199.216:2181,192.168.199.209:2181,192.168.199.201:2181";
	private static CountDownLatch cdl=new CountDownLatch(1);
	public static void main(String[] args) {
		
		try {
			//这个监听我们new出啦，是作用在这个 client会话,但是这个watcher并没有指定监听哪一个znode
			ZooKeeper client=new ZooKeeper(connectstr, 5000, new WatcherDemo(cdl));
			cdl.await();
//			TimeUnit.SECONDS.sleep(3);
			System.out.println(client.getState());
//			nodeCreate(client,"/zooXyl");
			nodeChange(client, "/zooXyl", "修改之后的数据是什么?oo");
			nodeChange(client, "/zooXyl", "还会改吗");
//			nodeDelete(client, "/zooXyl");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 节点修改事件
	 * @author xyl
	 */
	private static void nodeChange(ZooKeeper client,String node,String data) {
		try {
			//注册事件,true代表注册事件
//			Stat stat=new Stat();	
//			client.getData(node, true, stat);
			//-1表示不管什么版本都可以修改
			client.setData(node, data.getBytes(), -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 节点删除事件
	 * @author xyl
	 */
	private static void nodeDelete(ZooKeeper client,String node) {
		try {
//			client.exists(node, true);
			client.delete(node, -1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 创建事件
	 * @author xyl
	 */
	private static void nodeCreate(ZooKeeper client,String node) {
		try {
			//注册事件,如果是true就代表着/zoo注册一个watcherDemo的事件 
//			client.exists(node, true);
			client.create(node, "xx".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
