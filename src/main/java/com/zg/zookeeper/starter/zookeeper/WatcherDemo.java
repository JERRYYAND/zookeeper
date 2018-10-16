package com.zg.zookeeper.starter.zookeeper;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class WatcherDemo implements Watcher{

	private  CountDownLatch cdl;
	
	public WatcherDemo(CountDownLatch cdl) {
		super();
		this.cdl = cdl;
	}

	public void process(WatchedEvent event) {
		//如果client连接到服务端, 如果会话状态变成connected就会触发
		if(Event.KeeperState.SyncConnected==event.getState()) {
			if(Event.EventType.None==event.getType() && event.getPath()==null) {
				System.out.println("zookeeper会话创建成功!!");
				cdl.countDown();
			}else if(Event.EventType.NodeCreated==event.getType()) {
				System.out.println("触发节点创建事件"+event.getPath());
			}else if(Event.EventType.NodeDataChanged==event.getType()) {
				System.out.println("触发节点变更事件"+event.getPath());
			}else if(Event.EventType.NodeDeleted==event.getType()){
				System.out.println("触发节点删除事件"+event.getPath());
			}else if(Event.EventType.NodeChildrenChanged==event.getType()){
				System.out.println("触发子节点变更事件"+event.getPath());
			}
		}
		
	}

}
