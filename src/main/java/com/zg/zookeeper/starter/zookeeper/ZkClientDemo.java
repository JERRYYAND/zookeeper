package com.zg.zookeeper.starter.zookeeper;

import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

public class ZkClientDemo {
	private static String connectstr = "192.168.199.216:2181,192.168.199.209:2181,192.168.199.201:2181";

	public static void main(String[] args) {
		try {
			ZkClient zkclient = new ZkClient(connectstr, 5000);
			// zkclient.createPersistent("/zkClient", "高级的api,zkClient");
//			zkclient.createPersistent("/node", true);
			// zkclient.deleteRecursive("/zk");
			zkclient.deleteRecursive("/lock");
			// 事件注册
			zkclient.subscribeDataChanges("/lock", new IZkDataListener() {

				public void handleDataDeleted(String dataPath) throws Exception {
					System.out.println(dataPath + "节点删除了");

				}

				public void handleDataChange(String dataPath, Object data) throws Exception {
					System.out.println(dataPath + "节点修改了");

				}
			});
//			zkclient.writeData("/node", "高级API修改的数据");
//			zkclient.delete("/node");
			TimeUnit.SECONDS.sleep(3);
			zkclient.close();
			System.out.println("创建完毕");
		} catch (Exception e) {

		}

	}

}
