package client;

import client.ui.GameFrame;

/**
 * 客户端程序入口
 */
public class ClientMain {

	public static void main(String[] args) {
		GameFrame.getInstance().showLoginPanel();
	}

}
