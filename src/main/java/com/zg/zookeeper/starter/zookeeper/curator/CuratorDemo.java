package com.zg.zookeeper.starter.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.CreateMode;

public class CuratorDemo {

	public static void main(String[] args) {
		CuratorFramework client = CuratorUtil.getInstance();
		//启动连接 
		client.start();
		//创建节点
		create(client);
		//异步创建节点
//		createSync(client);
		//异步创建节点
//		createSync1(client);
		client.close();
	}
	
	private static void  create(CuratorFramework client) {
		try {
			String forPath = client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/curator/curator1","aa".getBytes());
			System.out.println(forPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
	}
	
	private static void createSync(CuratorFramework client) {
		//inBackground这个方法是异步运行的意思
		try {
			client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).inBackground(new BackgroundCallback() {
				//只有当接到创建成功才会回调 
				public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
				  System.out.println(event.getName()+":"+event.getPath());
					
				}
			}).forPath("/createSync","createSync".getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void createSync1(CuratorFramework client) {
		try {
			client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).inBackground(new BackgroundCallback() {
				
				public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
					 System.out.println(event.getName()+":"+event.getPath());
				}
			}).forPath("/createSync1","createSync1".getBytes());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
