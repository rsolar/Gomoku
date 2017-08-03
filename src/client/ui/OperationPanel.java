package client.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import client.listener.QuitListener;
import client.listener.ReadyListener;
import client.listener.RestartListener;

/**
 * 操作面板
 */
public class OperationPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel operationPanel = new JPanel(new BorderLayout());

	private JButton quitButton = new JButton("离开"); // 离开按钮
	private JButton restartButton = new JButton("重来"); // 重来按钮
	private JButton readyButton = new JButton("准备"); // 准备按钮

	OperationPanel() {
		quitButton.addActionListener(new QuitListener());
		restartButton.addActionListener(new RestartListener());
		readyButton.addActionListener(new ReadyListener());

		operationPanel.add(quitButton, BorderLayout.WEST);
		operationPanel.add(restartButton, BorderLayout.CENTER);
		operationPanel.add(readyButton, BorderLayout.EAST);
		this.add(operationPanel);
	}

	public void setQuitButtonText(String s) {
		quitButton.setText(s);
	}

}
