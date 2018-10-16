package com.zg.zookeeper.starter.zookeeper.lock;

import java.util.concurrent.CountDownLatch;

/**
 * 
 * @author xyl
 */
public class OrderService implements Runnable{
	OrderNumFactory onf=new OrderNumFactory();
	private static Integer count=100;
	private static CountDownLatch cdl=new CountDownLatch(count);
//	private Lock lock=new ZkLockImpl();
	
	private Lock lock=new ZkImproveLockImpl();
	public void run() {
//		try {
//			cdl.wait();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		createOrderNum();
	}
	
	public void createOrderNum() {
		lock.lock();
		String orderNum=onf.createOrderNum();
		System.out.println("创建订单号>>>"+orderNum);
		lock.unlock();
	}
	public static void main(String[] args) {
		for(int i=0;i<count;i++) {
			new Thread(new OrderService()).start();
			cdl.countDown();
		}
	}
}
