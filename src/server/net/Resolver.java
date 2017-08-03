package server.net;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import server.tool.HashMapManager;
import server.tool.MessageManager;

/**
 * 处理命令
 */
public class Resolver {

	Socket socket;
	int uid;
	String readLine;

	public void resolve(int _uid, Socket s, String _readLine) {
		this.socket = s;
		this.uid = _uid;
		this.readLine = _readLine;
		try {
			PrintStream ps = new PrintStream(socket.getOutputStream());
			// 更新列表
			if (readLine.startsWith(Header.LIST)) {
				new Action().getList(socket);
			}
			// 回复
			if (readLine.startsWith(Header.REPLY)) {
				String str = readLine.substring(Header.REPLY.length());
				// 回复挑战
				if (str.startsWith(Header.CHALLENGE)) {
					new Action().replyChallenge(uid, str.substring(Header.CHALLENGE.length()));
				}
			}
			// 落子
			if (readLine.startsWith(Header.PLAY)) {
				String str = readLine.substring(Header.PLAY.length());
				int position = Integer.parseInt(str);
				new Action().playChess(uid, position);
			}
			// 聊天
			if (readLine.startsWith(Header.CHAT)) {
				String str = readLine.substring(Header.CHAT.length());
				new Action().sendMessage(uid, str);
			}
			// 操作
			if (readLine.startsWith(Header.OPERATION)) {
				String str = readLine.substring(Header.OPERATION.length());
				// 挑战玩家
				if (str.startsWith(Header.CHALLENGE)) {
					str = str.substring(Header.CHALLENGE.length());
					int target = Integer.parseInt(str);
					new Action().sendChallenge(uid, target);
					HashMapManager.getInstance().getMatching().put(uid, target);
				}
				// 准备
				if (str.startsWith(Header.START)) {
					new Action().ready(uid);
				}
				// 重来
				if (str.startsWith(Header.RESTART)) {
					new Action().restart(uid);
				}
				// 离开
				if (str.startsWith(Header.QUIT)) {
					int oppoId = Integer.parseInt(str.substring(Header.QUIT.length()));
					new Action().quit(uid, oppoId);
				}
				// 认输
				if (str.startsWith(Header.GIVEUP)) {
					int oppoId = Integer.parseInt(str.substring(Header.GIVEUP.length()));
					new Action().giveUp(uid, oppoId);
				}
			}
			// 初始化信息
			if (readLine.startsWith(Header.INIT)) {
				HashMapManager.getInstance().getPlayer(uid).setName(readLine.substring(Header.INIT.length()));
				new Action().newClient(uid);
				ps.println(Header.INIT + uid + "-" + readLine.substring(Header.INIT.length()));
			}
		} catch (IOException e) {
			MessageManager.getInstance().addMessage("解析时获取输出流出错");
			e.printStackTrace();
		}
	}

}
