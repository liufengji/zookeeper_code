package com.victor.zk;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

public class TestZk {

	private String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
	private int sessionTimeout = 2000;
	ZooKeeper zkClient;

	// 初始化方法
	@Before
	public void initzk() throws IOException {

		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				System.out.println(event.getType() + "\t" + event.getPath());

				// 判断节点是否存在
				Stat exists;
				try {
					exists = zkClient.exists("/victor", true);
					System.out.println(exists == null?"not exist ":"exist");
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// 获取节点
//				List<String> children;
//				try {
//					children = zkClient.getChildren("/", true);
//
//					for (String node : children) {
//						System.out.println(node);
//					}
//
//				} catch (KeeperException e) {
//					e.printStackTrace();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
			}
		});
	}

	// 创建节点
	@Test
	public void createNode() throws KeeperException, InterruptedException{
		// 参数1：节点创建路径
		// 参数2：节点存放的数据
		// 参数3：权限
		// 参数4：节点类型
		String create = zkClient.create("/victor", "xulaoshimeilv".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

		System.out.println(create);
	}

	// 获取子节点
	@Test
	public void getNode() throws KeeperException, InterruptedException{
		// 参数1：获取节点路径
		// 参数2 ：是否监听
		// 参数3:状态
//		byte[] data = zkClient.getData("/victor", true, null);

		// 参数1：节点路径
		// 参数2：是否监听
		List<String> children = zkClient.getChildren("/", true);

		for (String node : children) {
			System.out.println(node);
		}

		// 延时阻塞
		Thread.sleep(Long.MAX_VALUE);
	}

	// 判断节点是否存在
	@Test
	public void isexist() throws KeeperException, InterruptedException{
		Stat exists = zkClient.exists("/victor", true);

		System.out.println(exists == null?"not exist ":"exist");

		Thread.sleep(Long.MAX_VALUE);
	}


}
