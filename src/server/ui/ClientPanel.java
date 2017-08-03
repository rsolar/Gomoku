package server.ui;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * 客户端列表面板
 */
public class ClientPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// 客户端列表
	JList<Integer> clientList = null;
	DefaultListModel<Integer> model = null;

	public JList<Integer> getClientList() {
		if (clientList == null) {
			clientList = new JList<Integer>(this.getModel());
			clientList.setFixedCellWidth(150);
			clientList.setVisibleRowCount(10);
		}
		return clientList;
	}

	public DefaultListModel<Integer> getModel() {
		if (model == null) {
			model = new DefaultListModel<Integer>();
		}
		return model;
	}

	public ClientPanel() {
		JScrollPane listPane = new JScrollPane();
		listPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		listPane.setViewportView(this.getClientList());

		this.setLayout(new BorderLayout());
		this.add(listPane, BorderLayout.CENTER);
		this.setBorder(new TitledBorder(new EtchedBorder(), "玩家列表", TitledBorder.CENTER, TitledBorder.TOP));
	}

	public void addClient(int uid) {
		this.getModel().addElement(uid);
	}

	public void removeClient(int uid) {
		this.getModel().removeElement(uid);
	}
}
