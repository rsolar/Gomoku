package server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import server.tool.HashMapManager;
import server.tool.MessageManager;
import server.tool.Player;

/**
 * 服务线程
 */
public class ServerThread implements Runnable {

	Socket socket = null;

	public ServerThread(Socket s) {
		this.socket = s;
		Player player = new Player(this.hashCode(), s);
		HashMapManager.getInstance().addPlayer(this.hashCode(), player);
	}

	@Override
	public void run() {
		boolean connected = true;
		String s;
		MessageManager.getInstance().addMessage("玩家" + this.hashCode() + "上线");

		while (connected) {
			try {
				// 从客户端接收命令
				BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				s = br.readLine();

				System.out.println("[RECIEVE FROM " + this.hashCode() + "]" + s);

				new Resolver().resolve(this.hashCode(), socket, s);
			} catch (IOException e) {
				// 客户端下线
				connected = false;
				System.out.println(this.hashCode() + " is off");
				new EndDeal().clientOff(this.hashCode());
			}
		}
	}
}
