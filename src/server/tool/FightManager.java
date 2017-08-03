package server.tool;

import java.io.IOException;
import java.io.PrintStream;

import server.net.Header;

/**
 * 对局管理器 已配对的两位玩家共用一个FightManager
 */
public class FightManager {

	boolean restartA = false; // 玩家A是否同意重来
	boolean restartB = false; // 玩家B是否同意重来

	int turn = 0; // 当前回合

	int BLACK = 1; // 黑棋
	int WHITE = -1; // 白棋

	int playerA; // 黑棋玩家ID
	int playerB; // 白棋玩家ID

	PrintStream psA = null; // 对玩家A的打印流
	PrintStream psB = null; // 对玩家B的打印流

	int[][] chessBoard = new int[15][15]; // 棋盘

	// 获取玩家A的打印流
	public PrintStream getPsA() {
		if (psA == null) {
			try {
				psA = new PrintStream(HashMapManager.getInstance().getPlayer(playerA).socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return psA;
	}

	// 获取玩家B的打印流
	public PrintStream getPsB() {
		if (psB == null) {
			try {
				psB = new PrintStream(HashMapManager.getInstance().getPlayer(playerB).socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return psB;
	}

	// 设置玩家A的ID
	public void setPlayerA(int playerA) {
		this.playerA = playerA;
	}

	// 设置玩家B的ID
	public void setPlayerB(int playerB) {
		this.playerB = playerB;
	}

	// 发送开始信息
	public void sendStartMessage() {
		this.getPsA().println(Header.STARTMSG + "游戏开始，请落子");
		this.getPsB().println(Header.STARTMSG + "游戏开始，等待对方落子");
		turn = BLACK;
	}

	// 开始游戏
	public void startPlay(int playerA, int playerB) {
		this.setPlayerA(playerA);
		this.setPlayerB(playerB);
		this.getPsA().println(Header.START + "BLACK");
		this.getPsB().println(Header.START + "WHITE");
	}

	// 发送落子信息
	public void sendPlay(int from, int position) {
		if (from == playerA) {
			int x = position % 15;
			int y = position / 15;
			chessBoard[x][y] = BLACK;
			turn = WHITE;
			this.getPsB().println(Header.PLAY + position);
			// 检查是否胜利
			if (this.checkWin(x, y, BLACK)) {
				this.getPsA().println(Header.WIN);
				this.getPsB().println(Header.LOSE);
				HashMapManager.getInstance().getReadys().remove(playerA);
				HashMapManager.getInstance().getReadys().remove(playerB);
			}
		} else if (from == playerB) {
			int x = position % 15;
			int y = position / 15;
			chessBoard[x][y] = WHITE;
			turn = BLACK;
			this.getPsA().println(Header.PLAY + position);
			// 检查是否胜利
			if (this.checkWin(x, y, WHITE)) {
				this.getPsB().println(Header.WIN);
				this.getPsA().println(Header.LOSE);
				HashMapManager.getInstance().getReadys().remove(playerA);
				HashMapManager.getInstance().getReadys().remove(playerB);
			}
		} else {
			MessageManager.getInstance().addMessage("发送落子信息出错：" + Header.PLAY + position);
			System.out.println("发送落子信息出错：" + Header.PLAY + position);
			System.out.println("来源ID：" + from);
			System.out.println("玩家A ID:" + playerA);
			System.out.println("玩家B ID:" + playerB);
		}
	}

	// 检查是否胜利
	public boolean checkWin(int x, int y, int id) {
		return (new BoardChecker()).check(x, y, id, chessBoard);
	}

	// 重新开始
	public void restart(int uid) {
		if (uid == playerA) {
			if (!restartA) {
				restartA = true;
			}
		} else {
			if (!restartB) {
				restartB = true;
			}
		}
		if (restartA && restartB) {
			psA = null;
			psB = null;
			chessBoard = new int[15][15];
			this.startPlay(playerB, playerA);
			this.sendStartMessage();
			restartA = false;
			restartB = false;
		}
	}

}