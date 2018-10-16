package com.zg.zookeeper.starter.zookeeper.lock;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author xyl
 */
public class OrderNumFactory {

	private static int i=0;
	
	public  String createOrderNum() {
	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
		return sdf.format(new Date())+ ++i;
	}
		
	public static void main(String[] args) {
		System.out.println(new OrderNumFactory().createOrderNum());
	}
}
