package com.victor.zk;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class ZKServer {

	private String connectString = "hadoop102:2181,hadoop103:2181,hadoop104:2181";
	private int sessionTimeout = 2000;

	private String parentNode = "/servers";

	ZooKeeper zkClient;

	// 建立连接
	public void getConnection() throws IOException {

		zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

			@Override
			public void process(WatchedEvent event) {

			}
		});
	}

	// 业务
	public void business() throws InterruptedException{
		System.out.println("banzhang chulai jie ke le");

		Thread.sleep(Long.MAX_VALUE);
	}

	// 注册
	public void regist(String hostname) throws KeeperException, InterruptedException {
		String create = zkClient.create(parentNode + "/server", hostname.getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL);

		System.out.println(hostname + " is online " + create);
	}

	public static void main(String[] args) throws Exception {

		ZKServer zkServer = new ZKServer();
		// 1 建立连接
		zkServer.getConnection();

		// 2 注册
		zkServer.regist(args[0]);

		// 3 具体业务处理
		zkServer.business();
	}
}
