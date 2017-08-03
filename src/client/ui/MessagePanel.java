package client.ui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import client.listener.MessageListener;
import client.manager.MessageManager;

/**
 * 消息面板
 */
public class MessagePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel messagePanel = new JPanel(new BorderLayout());

	private JPanel messageBodyPanel = new JPanel();
	private JPanel messageBottomPanel = new JPanel();

	private JScrollPane messagePane = new JScrollPane();
	private JTextField messageTextField = null; // 输入框

	public JTextField getMessageTextField() {
		if (messageTextField == null) {
			messageTextField = new JTextField(20);
		}
		return messageTextField;
	}

	MessagePanel() {
		this.setLayout(new BorderLayout());
		this.setBorder(
				BorderFactory.createTitledBorder(new EtchedBorder(), "消息", TitledBorder.CENTER, TitledBorder.TOP));

		messagePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		messagePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		messagePane.getViewport().add(MessageManager.getInstance().getMessageArea());
		messageBodyPanel.add(messagePane);
		messagePanel.add(messageBodyPanel, BorderLayout.CENTER);

		getMessageTextField().addActionListener(new MessageListener());
		messageBottomPanel.add(getMessageTextField());
		messagePanel.add(messageBottomPanel, BorderLayout.SOUTH);

		this.add(messagePanel);
	}

}
