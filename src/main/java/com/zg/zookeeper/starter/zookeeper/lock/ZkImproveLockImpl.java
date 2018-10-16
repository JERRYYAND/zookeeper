package com.zg.zookeeper.starter.zookeeper.lock;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.I0Itec.zkclient.IZkDataListener;

public class ZkImproveLockImpl extends ZkAbstractLock{
	//当前所在的位置
	private String currentPath;
	//前面所在的位置 
	private String beforePath;
	
	private CountDownLatch cdl;
	
	
	public ZkImproveLockImpl() {
		if(! this.client.exists(path)) {
			client.createPersistent(path, "aa".getBytes());
		}
	}

	@Override
	protected boolean tryLock() {
		if(currentPath==null || currentPath.length()<=0) {
			 currentPath = client.createEphemeralSequential(path+"/", "aa".getBytes());
		}
		//获取所有的子节点
		List<String> childrens = client.getChildren(path);
		Collections.sort(childrens);
		//这个就是判断当前用户创建临时节点的名称是否跟所有子节点里面最小的一个想等,如果想等就代表可以获得锁
		if(currentPath.equals((path)+"/"+childrens.get(0))) {
			return true;
		}else {
			//如果不能获得者把锁，那么必须要获取前面那个节点，要注册对前面那个节点的事件监控
			//得到当前节点的位置
			int currentPathi = Collections.binarySearch(childrens, currentPath.substring(6));
			beforePath=path+"/"+childrens.get(currentPathi-1);	
		}
		return false;
	}

	@Override
	protected void waitforlock() {
		//订阅事件
		IZkDataListener iZkDataListener = new IZkDataListener() {
			
			public void handleDataDeleted(String dataPath) throws Exception {
				if(cdl!=null) {
					cdl.countDown();
				}
				
			}
			
			public void handleDataChange(String dataPath, Object data) throws Exception {
				// TODO Auto-generated method stub
				
			}
		};
		client.subscribeDataChanges(beforePath,iZkDataListener );
		if(client.exists(beforePath)) {
			cdl=new CountDownLatch(1);
			try {
				cdl.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			client.unsubscribeDataChanges(beforePath, iZkDataListener);
		}
		
	}

}
