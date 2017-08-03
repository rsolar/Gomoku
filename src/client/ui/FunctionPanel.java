package client.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/**
 * 右侧功能面板
 */
public class FunctionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	PlayerListPanel playerListPanel = null; // 玩家列表面板
	MessagePanel messagePanel = null; // 消息面板
	OperationPanel operationPanel = null; // 操作面板

	FunctionPanel() {
		this.setLayout(new BorderLayout());
		this.add(getPlayerListPanel(), BorderLayout.NORTH);
		this.add(getMessagePanel(), BorderLayout.CENTER);
		this.add(getOperationPanel(), BorderLayout.SOUTH);
	}

	public PlayerListPanel getPlayerListPanel() {
		if (playerListPanel == null) {
			playerListPanel = new PlayerListPanel();
		}
		return playerListPanel;
	}

	public MessagePanel getMessagePanel() {
		if (messagePanel == null) {
			messagePanel = new MessagePanel();
		}
		return messagePanel;
	}

	public OperationPanel getOperationPanel() {
		if (operationPanel == null) {
			operationPanel = new OperationPanel();
		}
		return operationPanel;
	}
}
