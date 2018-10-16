package com.zg.zookeeper.starter.zookeeper.lock;
/**
 * |
 * @author xyl
 */
public interface Lock {
	/**
	 * 获取锁
	 * @author xyl
	 */
	void lock();
	/**
	 *释放锁
	 * @author xyl
	 */
	void unlock();
}
