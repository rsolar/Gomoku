package server.net;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import server.tool.FightManager;
import server.tool.HashMapManager;
import server.tool.Player;

/**
 * 执行命令
 */
public class Action {

	HashMapManager manager = HashMapManager.getInstance();

	// 新客户端
	public void newClient(int uid) {
		HashMap<Integer, Player> players = manager.getPlayers();
		Collection<Player> c = players.values();
		Iterator<Player> i = c.iterator();
		while (i.hasNext()) {
			Socket s = i.next().socket;
			try {
				PrintStream ps = new PrintStream(s.getOutputStream());
				String name = manager.getName(uid);
				ps.println(Header.ADDPLAYER + uid + "-" + name);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 移除客户端
	public void removeClient(int uid) {
		Set<Integer> p = manager.getPlayers().keySet();
		Iterator<Integer> i = p.iterator();
		while (i.hasNext()) {
			Socket s = manager.getPlayer(i.next()).socket;
			try {
				PrintStream ps = new PrintStream(s.getOutputStream());
				ps.println(Header.DELETEPLAYER + uid + "-" + manager.getPlayer(uid).name);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 获取列表
	public void getList(Socket s) {
		String list = null;
		try {
			PrintStream ps = new PrintStream(s.getOutputStream());
			Set<Integer> ids = manager.getPlayers().keySet();
			Iterator<Integer> i = ids.iterator();
			while (i.hasNext()) {
				int id = (Integer) i.next();
				if (list == null) {
					list = id + "-" + manager.getPlayer(id).name + "&";
				} else {
					list = list + id + "-" + manager.getPlayer(id).name + "&";
				}
			}
			ps.println(Header.LIST + list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 发送聊天消息
	public void sendMessage(int uid, String readLine) {
		String s[] = readLine.split("&");
		String message = s[0];
		int targetId = Integer.parseInt(s[1]);
		Socket socket = manager.getPlayer(targetId).socket;
		try {
			PrintStream printStream = new PrintStream(socket.getOutputStream());
			printStream.println(Header.CHAT + message + "&" + uid + "-" + HashMapManager.getInstance().getName(uid));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 发送挑战消息
	public void sendChallenge(int uid, int target) {
		HashMap<Integer, Player> players = manager.getPlayers();
		String name = HashMapManager.getInstance().getName(uid);
		Socket s = players.get(target).socket;
		try {
			PrintStream ps = new PrintStream(s.getOutputStream());
			ps.println(Header.OPERATION + Header.CHALLENGE + uid + "-" + name);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 发送重新开始请求
	public void restart(int uid) {
		if (manager.getFightManagers().containsKey(uid)) {
			manager.getFightManagers().get(uid).restart(uid);
		}
	}

	// 回复挑战
	public void replyChallenge(int uid, String readLine) {
		String[] s = readLine.split("&");
		int challengerId = Integer.parseInt(s[0]);
		String choose = s[1];
		Socket socket = manager.getPlayer(challengerId).socket;
		try {
			PrintStream challengerPs = new PrintStream(socket.getOutputStream());
			if (choose.equals("YES")) {
				challengerPs.println(Header.REPLY + Header.CHALLENGE + uid + "-" + manager.getName(uid) + "&YES");
				manager.getMatching().remove(challengerId);
				manager.addMatchs(challengerId, uid);
			} else {
				challengerPs.println(Header.REPLY + Header.CHALLENGE + uid + "-" + manager.getName(uid) + "&NO");
				manager.getMatching().remove(challengerId);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 准备
	public void ready(int uid) {
		int oppoId = 0;
		// 标记已准备
		manager.getReadys().add(uid);
		// 获取对方ID
		if (manager.getMatchs().containsKey(uid)) {
			oppoId = manager.getMatchs().get(uid);
		} else {
			Set<Integer> s = manager.getMatchs().keySet();
			Iterator<Integer> i = s.iterator();
			while (i.hasNext()) {
				int id = i.next();
				if (manager.getMatchs().get(id) == uid) {
					oppoId = id;
				}
			}
		}
		// 若两方都已准备，开始游戏
		if (manager.getReadys().contains(oppoId)) {
			// 获取已经注册的fightManager
			FightManager publicManager = manager.getFightManagers().get(oppoId);
			// 公用一个fightManager
			manager.getFightManagers().put(uid, publicManager);
			publicManager.startPlay(uid, oppoId);
			publicManager.sendStartMessage();
		} else {
			// 若对方未准备
			manager.getFightManagers().put(uid, new FightManager());
		}
	}

	// 落子
	public void playChess(int from, int position) {
		manager.getFightManagers().get(from).sendPlay(from, position);
	}

	// 离开
	public void quit(int uid, int oppoId) {
		HashMapManager manager = HashMapManager.getInstance();
		// 通知对手
		Socket s = manager.getPlayer(oppoId).socket;
		try {
			PrintStream ps = new PrintStream(s.getOutputStream());
			ps.println(Header.OPERATION + Header.QUIT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 移除准备状态
		manager.getReadys().remove(uid);
		manager.getReadys().remove(oppoId);
		// 移除配对
		if (manager.getMatchs().containsKey(uid)) {
			manager.removeMatchs(uid);
		} else {
			manager.removeMatchs(oppoId);
		}
		// 移除FightManager
		manager.getFightManagers().remove(uid);
		manager.getFightManagers().remove(oppoId);
	}

	// 认输
	public void giveUp(int uid, int oppoId) {
		HashMapManager manager = HashMapManager.getInstance();
		// 通知对手
		Socket s = manager.getPlayer(oppoId).socket;
		try {
			PrintStream ps = new PrintStream(s.getOutputStream());
			ps.println(Header.OPERATION + Header.GIVEUP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 移除准备状态
		manager.getReadys().remove(uid);
		manager.getReadys().remove(oppoId);
		// 移除配对
		if (manager.getMatchs().containsKey(uid)) {
			manager.removeMatchs(uid);
		} else {
			manager.removeMatchs(oppoId);
		}
		// 移除FightManager
		manager.getFightManagers().remove(uid);
		manager.getFightManagers().remove(oppoId);
	}

}
