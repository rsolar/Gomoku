package client.manager;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import client.Data;

/**
 * 玩家列表管理类
 */
public class PlayerListManager {

	private static PlayerListManager instance = null;

	// 玩家列表
	private JList<String> playerList = null;
	private DefaultListModel<String> listModel = null;

	private PlayerListManager() {

	}

	public static PlayerListManager getInstance() {
		if (instance == null) {
			instance = new PlayerListManager();
		}
		return instance;
	}

	public JList<String> getPlayerList() {
		if (playerList == null) {
			playerList = new JList<String>(getListModel());
			playerList.setCellRenderer(new MyListCellRenderer());
		}
		return playerList;
	}

	private class MyListCellRenderer extends JLabel implements ListCellRenderer<String> {

		private static final long serialVersionUID = 1L;

		public MyListCellRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
				boolean isSelected, boolean cellHasFocus) {
			setText(value);
			setForeground(value.equals(Data.myId + "-" + Data.myName) ? Color.RED : Color.BLACK);
			setBackground(isSelected ? Color.LIGHT_GRAY : Color.WHITE);
			return this;
		}
	}

	public DefaultListModel<String> getListModel() {
		if (listModel == null) {
			listModel = new DefaultListModel<String>();
		}
		return listModel;
	}

	public void clearList() {
		this.getListModel().clear();
		this.getPlayerList().repaint();
	}

	public void addPlayer(String name) {
		this.getListModel().addElement(name);
		this.getPlayerList().repaint();
	}

	public void removePlayer(String name) {
		this.getListModel().removeElement(name);
		this.getPlayerList().repaint();
	}

}
