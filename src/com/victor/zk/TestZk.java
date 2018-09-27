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

	// ��ʼ������
	@Before
	public void initzk() throws IOException {

		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				System.out.println(event.getType() + "\t" + event.getPath());
				
				// �жϽڵ��Ƿ����
				Stat exists;
				try {
					exists = zkClient.exists("/victor", true);
					System.out.println(exists == null?"not exist ":"exist");
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// ��ȡ�ڵ�
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
	
	// �����ڵ�
	@Test
	public void createNode() throws KeeperException, InterruptedException{
		// ����1���ڵ㴴��·��
		// ����2���ڵ��ŵ�����
		// ����3��Ȩ��
		// ����4���ڵ�����
		String create = zkClient.create("/victor", "xulaoshimeilv".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		System.out.println(create);
	}
	
	// ��ȡ�ӽڵ�
	@Test
	public void getNode() throws KeeperException, InterruptedException{
		// ����1����ȡ�ڵ�·��
		// ����2 ���Ƿ����
		// ����3:״̬
//		byte[] data = zkClient.getData("/victor", true, null);
		
		// ����1���ڵ�·��
		// ����2���Ƿ����
		List<String> children = zkClient.getChildren("/", true);
		
		for (String node : children) {
			System.out.println(node);
		}
		
		// ��ʱ����
		Thread.sleep(Long.MAX_VALUE);
	}
	
	// �жϽڵ��Ƿ����
	@Test
	public void isexist() throws KeeperException, InterruptedException{
		Stat exists = zkClient.exists("/victor", true);
		
		System.out.println(exists == null?"not exist ":"exist");
		
		Thread.sleep(Long.MAX_VALUE);
	}
	
	
}
