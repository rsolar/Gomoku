package server.ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import server.tool.MessageManager;

/**
 * 消息面板
 */
public class MessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JScrollPane messagePane = new JScrollPane();

	public MessagePanel() {
		messagePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		messagePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		messagePane.getViewport().add(MessageManager.getInstance().getMessageArea());
		this.add(messagePane);
	}

}
