package client.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * 游戏面板
 */
public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JPanel gameBody = new JPanel(); // 左侧棋盘面板

	private BoardCanvas boardCanvas = null; // 棋盘画布

	GamePanel() {
		gameBody.add(getBoardCanvas());

		this.setLayout(new BorderLayout());
		this.add(gameBody, BorderLayout.CENTER);
	}

	public BoardCanvas getBoardCanvas() {
		if (boardCanvas == null) {
			boardCanvas = new BoardCanvas();
		}
		return boardCanvas;
	}

}
