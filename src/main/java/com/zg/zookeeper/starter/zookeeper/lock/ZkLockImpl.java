package com.zg.zookeeper.starter.zookeeper.lock;

import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.exception.ZkException;

/**
 * 
 * @author xyl
 */
public class ZkLockImpl extends ZkAbstractLock{
	
	CountDownLatch cdl=null;
	/**
	 * 这个就是去创建znode,如果创建成功就返回true
	 */
	@Override
	protected boolean tryLock() {
		try {
			client.createEphemeral(path);
			return true;
		} catch (ZkException e) {
			return false;
		}
	
	}

	/**
	 * waitforlock要监控前面的那个线程的/lock节点
	 * 要知道/lock节点有没有删除
	 */
	@Override
	protected void waitforlock() {
		IZkDataListener iZkDataListener = new IZkDataListener() {
			
			public void handleDataDeleted(String dataPath) throws Exception {
				if(cdl!=null) {
					cdl.countDown();
				}
				
			}
			
			public void handleDataChange(String dataPath, Object data) throws Exception {
				
			}
		};
		//注册path的事件
		client.subscribeDataChanges(path, iZkDataListener);
		if(client.exists(path)) {
			 cdl=new CountDownLatch(1);
			 try {
				 //这里是要等待，只有当前面的线程释放了锁，也就是前面的线程zookeeper会话失效的时候，才不需要等
				 cdl.wait();
			} catch (Exception e) {
			}
		}
		client.unsubscribeDataChanges(path, iZkDataListener);
	}

}
