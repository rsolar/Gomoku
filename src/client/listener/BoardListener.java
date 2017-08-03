package client.listener;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import client.Data;
import client.manager.MessageManager;
import client.net.PlayChess;
import client.ui.BoardCanvas;

/**
 * 棋盘监听类
 */
public class BoardListener extends MouseAdapter {

	@Override
	public void mousePressed(MouseEvent e) {
		BoardCanvas canvas = (BoardCanvas) e.getSource();
		// 判断是否已连接
		if (Data.connected) {
			// 判断是否选择了对手
			if (Data.oppoId != 0) {
				if (Data.ready) {
					// 判断是否已开始
					if (Data.started) {
						// 判断是否轮到自己落棋
						if (Data.turn == Data.myChess) {
							// 判断是否在棋盘范围内
							if (e.getX() < canvas.getMapWidth() - 6 && e.getY() < canvas.getHeight() - 7) {
								int x = e.getX() / 35;
								int y = e.getY() / 35;
								// 判断此处是否已有棋子
								if (Data.chessBoard[x][y] == 0) {
									new PlayChess().play(x, y, Data.myChess);
								} else {
									MessageManager.getInstance().addMessage("不能下在这里");
								}
							}
						} else if (Data.turn == Data.oppoChess) {
							MessageManager.getInstance().addMessage("不是你的回合");
						}
					} else {
						MessageManager.getInstance().addMessage("等待对方准备");
					}
				} else {
					MessageManager.getInstance().addMessage("请先开始游戏");
				}
			} else {
				MessageManager.getInstance().addMessage("请先选择对手");
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		BoardCanvas canvas = (BoardCanvas) e.getSource();
		canvas.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		BoardCanvas canvas = (BoardCanvas) e.getSource();
		canvas.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

}
