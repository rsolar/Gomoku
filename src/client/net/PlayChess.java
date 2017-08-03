package client.net;

import java.io.PrintStream;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.ui.BoardCanvas;
import client.ui.GameFrame;

/**
 * 落棋
 */
public class PlayChess {

	public void play(int x, int y, int chess) {
		PrintStream ps = IOManager.getInstance().getPs();
		// 计算落棋位置
		int position = 15 * y + x;
		Data.last = position;
		// 我方下棋
		if (chess == Data.myChess) {
			Data.chessBoard[x][y] = Data.myChess;
			// 重绘棋盘
			BoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
			mapCanvas.paintBoardImage();
			mapCanvas.repaint();
			// 换人
			Data.turn = Data.oppoChess;
			// 发送至服务器
			ps.println(Header.PLAY + position);

			MessageManager.getInstance().addMessage("等待对方落棋");
		}
		// 对方下棋
		if (chess == Data.oppoChess) {
			Data.chessBoard[x][y] = Data.oppoChess;
			// 重绘棋盘
			BoardCanvas mapCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
			mapCanvas.paintBoardImage();
			mapCanvas.repaint();
			// 换人
			Data.turn = Data.myChess;

			MessageManager.getInstance().addMessage("请您落棋");
		}
	}

}
