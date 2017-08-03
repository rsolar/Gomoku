package client.manager;

import javax.swing.JTextArea;

/**
 * 消息框管理类
 */
public class MessageManager {

	private static MessageManager instance = null;

	private JTextArea messageArea = null; // 输入框

	private MessageManager() {

	}

	public static MessageManager getInstance() {
		if (instance == null) {
			instance = new MessageManager();
		}
		return instance;
	}

	public JTextArea getMessageArea() {
		if (messageArea == null) {
			messageArea = new JTextArea(12, 19);
			messageArea.setEditable(false);
			messageArea.setLineWrap(true);
		}
		return messageArea;
	}

	public void addMessage(String message) {
		//getMessageArea().setCaretPosition(getMessageArea().getDocument().getLength());
		//getMessageArea().replaceSelection();
		getMessageArea().append("•" + message + System.lineSeparator());
	}

}
