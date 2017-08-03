package client.net;

import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.JOptionPane;

import client.Data;
import client.manager.IOManager;
import client.manager.PlayerListManager;
import client.ui.GameFrame;

/**
 * 接收数据
 */
public class Receiver implements Runnable {

	@Override
	public void run() {
		BufferedReader br = IOManager.getInstance().getBr();
		String s;

		while (Data.connected) {
			try {
				// 收到一条命令
				s = br.readLine();

				System.out.println("[RECIEVE]" + s);

				// 初始化
				if (s.startsWith(Header.INIT)) {
					new Resolver().init(s.substring(Header.INIT.length()));
				}

				// 游戏开始消息
				if (s.startsWith(Header.STARTMSG)) {
					new Resolver().startMessage(s.substring(Header.STARTMSG.length()));
				}

				// 游戏开始
				if (s.startsWith(Header.START)) {
					new Resolver().start(s.substring(Header.START.length()));
				}

				// 更新列表
				if (s.startsWith(Header.LIST)) {
					new Resolver().updateList(s.substring(Header.LIST.length()));
				}

				// 添加玩家
				if (s.startsWith(Header.ADDPLAYER)) {
					new Resolver().addList(s.substring(Header.ADDPLAYER.length()));
				}

				// 删除玩家
				if (s.startsWith(Header.DELETEPLAYER)) {
					new Resolver().delList(s.substring(Header.DELETEPLAYER.length()));
				}

				// 落子
				if (s.startsWith(Header.PLAY)) {
					new Resolver().play(s.substring(Header.PLAY.length()));
				}

				// 聊天
				if (s.startsWith(Header.CHAT)) {
					new Resolver().chat(s.substring(Header.CHAT.length()));
				}

				// 操作
				if (s.startsWith(Header.OPERATION)) {
					new Resolver().operation(s.substring(Header.OPERATION.length()));
				}

				// 回执
				if (s.startsWith(Header.REPLY)) {
					new Resolver().reply(s.substring(Header.REPLY.length()));
				}

				// 胜
				if (s.startsWith(Header.WIN)) {
					new Resolver().win();
				}

				// 败
				if (s.startsWith(Header.LOSE)) {
					new Resolver().lose();
				}

			} 
			catch (IOException e) {
				// 服务器连接断开
				// 清空数据
				Data.last = -1;
				Data.turn = -1;
				Data.myId = 0;
				Data.oppoId = 0;
				Data.myName = null;
				Data.myChess = 0;
				Data.oppoChess = 0;
				Data.ready = false;
				Data.started = false;
				Data.connected = false;
				Data.chessBoard = new int[15][15];
				PlayerListManager.getInstance().clearList();
				GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("目前对手：无");
				GameFrame.getInstance().getFunctionPanel().getMessagePanel().getMessageTextField().setText("");

				JOptionPane.showMessageDialog(GameFrame.getInstance(), "与服务器连接中断");
				GameFrame.getInstance().showLoginPanel();
			}
		}
	}

}
