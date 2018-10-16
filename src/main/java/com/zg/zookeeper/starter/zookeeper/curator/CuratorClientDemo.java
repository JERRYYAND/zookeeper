package com.zg.zookeeper.starter.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public abstract class CuratorClientDemo {
	private static String connectstr="192.168.199.216:2181,192.168.199.209:2181,192.168.199.201:2181";
	public static void main(String[] args) {
		try {
			CuratorFramework newClient = CuratorFrameworkFactory.newClient(connectstr, 5000, 5000, new ExponentialBackoffRetry(1000, 3));
			//启动链接
			newClient.start();
			CuratorFramework client = CuratorFrameworkFactory.builder().connectString(connectstr).sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
			client.start();
		
		} catch (Exception e) {
		
		}
		
		
		
	}

}
