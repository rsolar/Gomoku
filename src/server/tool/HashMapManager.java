package server.tool;

import java.util.HashMap;
import java.util.HashSet;

import server.ui.ServerFrame;

/**
 * HashMap管理器
 */
public class HashMapManager {

	private static HashMapManager instance = null;

	private HashMap<Integer, Player> players = null; // 玩家
	private HashMap<Integer, Integer> matchs = null; // 已配对
	private HashMap<Integer, Integer> matching = null; // 配对中
	private HashMap<Integer, FightManager> fightManagers = null; // 对战管理器
	private HashSet<Integer> readys = null; // 已准备玩家

	private HashMapManager() {

	}

	public static HashMapManager getInstance() {
		if (instance == null) {
			instance = new HashMapManager();
		}
		return instance;
	}

	public HashMap<Integer, FightManager> getFightManagers() {
		if (fightManagers == null) {
			fightManagers = new HashMap<Integer, FightManager>();
		}
		return fightManagers;
	}

	public HashSet<Integer> getReadys() {
		if (readys == null) {
			readys = new HashSet<Integer>();
		}
		return readys;
	}

	public HashMap<Integer, Integer> getMatching() {
		if (matching == null) {
			matching = new HashMap<Integer, Integer>();
		}
		return matching;
	}

	public void addMatchs(Integer uid1, Integer uid2) {
		getMatchs().put(uid1, uid2);
		// 服务器显示更新
		ServerFrame.getInstance().getMatchsPanel().addMatchs(uid1, uid2);
	}

	public void removeMatchs(Integer uid) {
		// 服务器显示更新
		ServerFrame.getInstance().getMatchsPanel().removeMatchs(uid);
		getMatchs().remove(uid);
	}

	public HashMap<Integer, Integer> getMatchs() {
		if (matchs == null) {
			matchs = new HashMap<Integer, Integer>();
		}
		return matchs;
	}

	public String getName(Integer uid) {
		return getPlayer(uid).name;
	}

	public void addPlayer(Integer uid, Player player) {
		getPlayers().put(uid, player);
		// 服务器显示更新
		ServerFrame.getInstance().getClientPanel().addClient(uid);
	}

	public void removePlayer(Integer uid) {
		getPlayers().remove(uid);
		// 服务器显示更新
		ServerFrame.getInstance().getClientPanel().removeClient(uid);
	}

	public Player getPlayer(Integer uid) {
		return getPlayers().get(uid);
	}

	public HashMap<Integer, Player> getPlayers() {
		if (players == null) {
			players = new HashMap<Integer, Player>();
		}
		return players;
	}

}
