package client.net;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.manager.PlayerListManager;
import client.ui.BoardCanvas;
import client.ui.GameFrame;

/**
 * 处理命令
 */
public class Resolver {

	// 初始化
	public void init(String s) {
		String str[] = s.split("-");
		String name = str[1];
		int id = Integer.parseInt(str[0]);
		Data.myId = id;
		Data.myName = name;
		// 显示游戏界面
		GameFrame.getInstance().showGamePanel();
		// 更新玩家列表
		IOManager.getInstance().getPs().println(Header.LIST);
	}

	// 游戏开始消息
	public void startMessage(String s) {
		MessageManager.getInstance().addMessage(s);
	}

	// 游戏开始
	public void start(String s) {
		if (s.substring(0, 5).equals("BLACK")) {
			Data.myChess = Data.BLACK;
			Data.oppoChess = Data.WHITE;
			Data.turn = Data.BLACK;
			Data.started = true;
		}
		if (s.substring(0, 5).equals("WHITE")) {
			Data.myChess = Data.WHITE;
			Data.oppoChess = Data.BLACK;
			Data.turn = Data.BLACK;
			Data.started = true;
		}
		GameFrame.getInstance().getFunctionPanel().getOperationPanel().setQuitButtonText("认输");
	}

	// 更新列表
	public void updateList(String s) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					PlayerListManager playerListManager = PlayerListManager.getInstance();
					playerListManager.clearList();
					String[] players = s.split("&");
					for (String player : players) {
						playerListManager.addPlayer(player);
					}
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	// 添加玩家
	public void addList(String s) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					PlayerListManager.getInstance().addPlayer(s);
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	// 删除玩家
	public void delList(String s) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					PlayerListManager.getInstance().removePlayer(s);
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	// 落子
	public void play(String s) {
		int position = Integer.parseInt(s);
		int x = position % 15;
		int y = position / 15;
		new PlayChess().play(x, y, Data.oppoChess);
	}

	// 聊天
	public void chat(String s) {
		String str[] = s.split("&");
		String message = str[0];
		String who = str[1];
		MessageManager.getInstance().addMessage(who + "说：" + message);
	}

	// 操作
	public void operation(String s) {
		// 对方认输
		if (s.startsWith(Header.GIVEUP)) {
			JOptionPane.showMessageDialog(GameFrame.getInstance(), "对方认输了，请重新选择对手");
			Data.last = -1;
			Data.oppoId = 0;
			Data.myChess = 0;
			Data.oppoChess = 0;
			Data.ready = false;
			Data.started = false;
			Data.chessBoard = new int[15][15];
			GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("目前对手：无");
			// 重绘棋盘
			BoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
			mapCanvas.paintBoardImage();
			mapCanvas.repaint();
		}
		// 对方离开
		if (s.startsWith(Header.QUIT)) {
			Data.last = -1;
			Data.oppoId = 0;
			Data.myChess = 0;
			Data.oppoChess = 0;
			Data.ready = false;
			Data.started = false;
			Data.chessBoard = new int[15][15];
			GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("目前对手：无");
			// 重绘棋盘
			BoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
			mapCanvas.paintBoardImage();
			mapCanvas.repaint();

			JOptionPane.showMessageDialog(GameFrame.getInstance(), "对方离开了，请重新选择对手");
		}
		// 收到挑战
		if (s.startsWith(Header.CHALLENGE)) {
			s = s.substring(Header.CHALLENGE.length());
			String[] str = s.split("-");
			int targetId = Integer.parseInt(str[0]);
			int value = JOptionPane.showConfirmDialog(GameFrame.getInstance(), "玩家“" + s + "”挑战你，是否接受？", "收到挑战",
					JOptionPane.YES_NO_OPTION);
			// 接受挑战
			if (value == JOptionPane.YES_OPTION) {
				IOManager.getInstance().getPs().println(Header.REPLY + Header.CHALLENGE + targetId + "&YES");
				JOptionPane.showMessageDialog(GameFrame.getInstance(), "您接受了对方的挑战，请点“准备”开始游戏，或点“离开”重新选择对手");
				Data.oppoId = targetId;
				GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("目前对手：" + s);
				MessageManager.getInstance().addMessage("您接受了对方的挑战，请点“准备”开始游戏，或点“离开”重新选择对手");
			}
			// 拒绝挑战
			else {
				IOManager.getInstance().getPs().println(Header.REPLY + Header.CHALLENGE + targetId + "&NO");
				MessageManager.getInstance().addMessage("您拒绝了对方的挑战");
			}
		}

	}

	// 回复
	public void reply(String s) {
		// 挑战回复
		if (s.startsWith(Header.CHALLENGE)) {
			s = s.substring(Header.CHALLENGE.length());
			String str[] = s.split("&");
			String challenged = str[0];
			String result = str[1];
			// 对方接受挑战
			if (result.equals("YES")) {
				int uid = Integer.parseInt(challenged.split("-")[0]);
				JOptionPane.showMessageDialog(GameFrame.getInstance(), "对方接受了您的挑战，请点“准备”开始游戏，或点“离开”重新选择对手");
				// 设置对手id
				Data.oppoId = uid;
				// 设置对手状态栏信息
				GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo()
						.setText("目前对手：" + challenged);
				MessageManager.getInstance().addMessage("对方接受了您的挑战，请点“准备”开始游戏，或点“离开”重新选择对手");
			}
			// 对方拒绝挑战
			else if (result.equals("NO")) {
				JOptionPane.showMessageDialog(GameFrame.getInstance(), "玩家“" + challenged + "”拒绝了您的挑战");
			}
		}
	}

	// 胜
	public void win() {
		JOptionPane.showMessageDialog(GameFrame.getInstance(), "你赢了！");
		MessageManager.getInstance().addMessage("你赢了！");
		Data.turn = 0;
		Data.ready = false;
		Data.started = false;
		GameFrame.getInstance().getFunctionPanel().getOperationPanel().setQuitButtonText("离开");
	}

	// 败
	public void lose() {
		JOptionPane.showMessageDialog(GameFrame.getInstance(), "你输了！");
		MessageManager.getInstance().addMessage("你输了！");
		Data.turn = 0;
		Data.ready = false;
		Data.started = false;
		GameFrame.getInstance().getFunctionPanel().getOperationPanel().setQuitButtonText("离开");
	}

}
