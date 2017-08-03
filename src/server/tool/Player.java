package server.tool;

import java.net.Socket;

/**
 * 玩家信息
 */
public class Player {

	public Socket socket;

	public int oppoId;
	public int myId;
	public boolean ready;
	public boolean started;
	public String name;

	FightManager fightManager;

	public Player(int id, Socket s) {
		this.myId = id;
		this.socket = s;
	}

	public int getOppoId() {
		return oppoId;
	}

	public int getMyId() {
		return myId;
	}

	public boolean isReady() {
		return ready;
	}

	public boolean isStarted() {
		return started;
	}

	public String getName() {
		return name;
	}

	public Socket getS() {
		return socket;
	}

	public FightManager getFightManager() {
		return fightManager;
	}

	public void setOppoId(int oppoId) {
		this.oppoId = oppoId;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFightManager(FightManager fightManager) {
		this.fightManager = fightManager;
	}

}
