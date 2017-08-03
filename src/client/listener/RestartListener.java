package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.net.Header;
import client.ui.BoardCanvas;
import client.ui.GameFrame;

/**
 * 重来按钮监听类
 */
public class RestartListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (Data.connected) {
			if (!Data.started) {
				// 清空数据
				Data.last = -1;
				Data.turn = 0;
				Data.chessBoard = new int[15][15];
				// 交换颜色
				Data.myChess = 0;
				Data.oppoChess = 0;
				// 重绘棋盘
				BoardCanvas boardCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
				boardCanvas.paintBoardImage();
				boardCanvas.repaint();

				IOManager.getInstance().getPs().println(Header.OPERATION + Header.RESTART);
			} else {
				MessageManager.getInstance().addMessage("游戏还没结束");
			}
		}
	}

}
