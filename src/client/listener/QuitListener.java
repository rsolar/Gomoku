package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.net.Header;
import client.ui.BoardCanvas;
import client.ui.GameFrame;

/**
 * 离开/认输按钮监听类
 */
public class QuitListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Data.oppoId != 0) {
			if (Data.started) {
				int value = JOptionPane.showConfirmDialog(GameFrame.getInstance(), "游戏还没结束，你确定要认输？", "认输",
						JOptionPane.YES_NO_OPTION);
				if (value == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(GameFrame.getInstance(), "你认输了！");
					IOManager.getInstance().getPs().println(Header.OPERATION + Header.GIVEUP + Data.oppoId);
					// 清空数据
					Data.last = -1;
					Data.oppoId = 0;
					Data.myChess = 0;
					Data.oppoChess = 0;
					Data.ready = false;
					Data.started = false;
					Data.chessBoard = new int[15][15];
					GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("目前对手：无");
					// 重绘棋盘
					BoardCanvas boardCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
					boardCanvas.paintBoardImage();
					boardCanvas.repaint();

					MessageManager.getInstance().addMessage("你可以重新选择对手了");
				}
			} else {
				IOManager.getInstance().getPs().println(Header.OPERATION + Header.QUIT + Data.oppoId);
				// 清空数据
				Data.last = -1;
				Data.oppoId = 0;
				Data.myChess = 0;
				Data.oppoChess = 0;
				Data.ready = false;
				Data.started = false;
				Data.chessBoard = new int[15][15];
				GameFrame.getInstance().getFunctionPanel().getPlayerListPanel().getOpponentInfo().setText("目前对手：无");
				// 重绘棋盘
				BoardCanvas boardCanvas = GameFrame.getInstance().getGamePanel().getBoardCanvas();
				boardCanvas.paintBoardImage();
				boardCanvas.repaint();

				MessageManager.getInstance().addMessage("你可以重新选择对手了");
			}
		}
	}

}
