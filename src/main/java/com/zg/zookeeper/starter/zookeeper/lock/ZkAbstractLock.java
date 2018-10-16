package com.zg.zookeeper.starter.zookeeper.lock;

import org.I0Itec.zkclient.ZkClient;

public abstract class  ZkAbstractLock implements Lock{
	public static String connectstr="192.168.199.216:2181,192.168.199.209:2181,192.168.199.201:2181";
	public ZkClient client=new ZkClient(connectstr);
	public static String  path="/lock";
	/**
	 * lock是获取锁的意思
	 * 但是在高并发的情况下，这个lock永远只有一个线程获取锁成功
	 * 其他线程如果获取不到锁，那么它会一直在等待
	 * 
	 * 1:线程试着去获取锁,
	 * 2:如果获取锁失败，等待，等待完以后再去获取锁
	 */
	public void lock() {
		if(tryLock()) {
			System.out.println(Thread.currentThread().getName()+"-->获取锁成功");
		}else {
			waitforlock();
			lock();
		}
		
	}
	
	protected abstract boolean tryLock();
	
	protected abstract void waitforlock();
	/**
	 * 释放锁
	 */
	public void unlock() {
		client.close();
	}

}
