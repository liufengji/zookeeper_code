package com.victor.zk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZKClient {

	private String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
	private int sessionTimeout = 2000;
	ZooKeeper zkClient;
	private String parentNode = "/servers";

	// 获取连接
	public void getConnect() throws IOException {
		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {
				try {
					getServersList();
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 监听节点变化
	public void getServersList() throws KeeperException, InterruptedException{
		List<String> children = zkClient.getChildren(parentNode, true);

		ArrayList<String> servers = new ArrayList<>();

		for (String node : children) {
			byte[] data = zkClient.getData(parentNode+"/"+node, false, null);

			servers.add(new String(data));
		}

		System.out.println(servers);
	}


	// 业务
	public void business() throws InterruptedException{
		System.out.println("banzhang chulai song ke shou qian");
		Thread.sleep(Long.MAX_VALUE);
	}

	public static void main(String[] args) throws Exception {

		ZKClient zk = new ZKClient();

		// 1 获取连接
		zk.getConnect();

		// 2 监听节点变化
		zk.getServersList();

		// 3 业务逻辑处理
		zk.business();

	}
}
