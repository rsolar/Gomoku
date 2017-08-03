package client.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.listener.ConnectListener;

/**
 * 连接面板
 */
public class LoginPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JPanel infoPanel = new JPanel(new GridBagLayout()); // 信息网格面板
	private JPanel addressPanel = new JPanel(new FlowLayout()); // 地址输入面板
	private JPanel namePanel = new JPanel(new FlowLayout()); // 昵称输入面板

	private JLabel addressLabel = new JLabel("服务器地址："); // 地址
	private JLabel symbolLabel = new JLabel(" : "); // 分隔符
	private JLabel nameLabel = new JLabel("昵称："); // 昵称

	private JTextField ipTextField = null; // IP输入框
	private JTextField portTextField = null; // 端口号输入框
	private JTextField nameTextField = null; // 昵称输入框

	private JButton connectBotton = new JButton("连接"); // 连接按钮

	public LoginPanel() {
		this.setLayout(new BorderLayout());

		GridBagConstraints bag1 = new GridBagConstraints();
		bag1.gridx = 0;
		bag1.gridy = 0;
		bag1.gridwidth = 1;
		bag1.gridheight = 1;
		bag1.weightx = 0;
		bag1.weighty = 0;
		bag1.fill = GridBagConstraints.BOTH;
		bag1.anchor = GridBagConstraints.WEST;
		infoPanel.add(addressLabel, bag1);

		addressPanel.add(getIpTextField());
		addressPanel.add(symbolLabel);
		addressPanel.add(getPortTextField());
		GridBagConstraints bag2 = new GridBagConstraints();
		bag2.gridx = 1;
		bag2.gridy = 0;
		bag2.gridwidth = 2;
		bag2.gridheight = 1;
		bag2.weightx = 0;
		bag2.weighty = 0;
		bag2.fill = GridBagConstraints.BOTH;
		bag2.anchor = GridBagConstraints.WEST;
		infoPanel.add(addressPanel, bag2);

		GridBagConstraints bag3 = new GridBagConstraints();
		bag3.gridx = 0;
		bag3.gridy = 1;
		bag3.gridwidth = 1;
		bag3.gridheight = 1;
		bag3.weightx = 0;
		bag3.weighty = 0;
		bag3.fill = GridBagConstraints.BOTH;
		bag3.anchor = GridBagConstraints.WEST;
		infoPanel.add(nameLabel, bag3);

		namePanel.add(getNameTextField());
		GridBagConstraints bag4 = new GridBagConstraints();
		bag4.gridx = 1;
		bag4.gridy = 1;
		bag4.gridwidth = 2;
		bag4.gridheight = 1;
		bag4.weightx = 0;
		bag4.weighty = 0;
		bag4.fill = GridBagConstraints.BOTH;
		bag4.anchor = GridBagConstraints.WEST;
		infoPanel.add(namePanel, bag4);

		this.add(infoPanel, BorderLayout.CENTER);

		connectBotton.addActionListener(new ConnectListener());
		this.add(connectBotton, BorderLayout.SOUTH);
	}

	public JTextField getIpTextField() {
		if (ipTextField == null) {
			ipTextField = new JTextField("127.0.0.1", 15); // 默认连接本机
			ipTextField.setToolTipText("服务器IP地址");
		}
		return ipTextField;
	}

	public JTextField getPortTextField() {
		if (portTextField == null) {
			portTextField = new JTextField("46666", 5); // 默认端口号
			portTextField.setToolTipText("服务器端口");
		}
		return portTextField;
	}

	public JTextField getNameTextField() {
		if (nameTextField == null) {
			nameTextField = new JTextField("newbee", 22); // 默认昵称
			nameTextField.setToolTipText("昵称");
			nameTextField.setEditable(true);
		}
		return nameTextField;
	}

}
