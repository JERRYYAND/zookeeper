package com.zg.zookeeper.starter.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
/**
 * 获取连接工具类
 * @author xyl
 */
public class CuratorUtil {
	private static String connectstr="192.168.199.216:2181,192.168.199.209:2181,192.168.199.201:2181";
	
	private static class innerclass{
		private static CuratorFramework client = CuratorFrameworkFactory.builder().connectString(connectstr).sessionTimeoutMs(5000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).build();
	}
	
	public static CuratorFramework getInstance() {
		return innerclass.client;
	}
}
