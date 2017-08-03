package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JOptionPane;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.manager.PlayerListManager;
import client.net.Header;
import client.ui.GameFrame;

/**
 * 挑战按钮监听类
 */
public class ChallengeListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JList<String> list = PlayerListManager.getInstance().getPlayerList();
		if (Data.oppoId == 0) {
			if (!list.isSelectionEmpty()) {
				String s = list.getSelectedValue().split("-")[0];
				int targetId = Integer.parseInt(s);
				if (targetId != Data.myId) {
					MessageManager.getInstance().addMessage("等待对方接受挑战");
					IOManager.getInstance().getPs().println(Header.OPERATION + Header.CHALLENGE + targetId);
				} else {
					JOptionPane.showMessageDialog(GameFrame.getInstance(), "不能挑战自己");
				}
			} else {
				JOptionPane.showMessageDialog(GameFrame.getInstance(), "未选中任何对手");
			}
		} else {
			MessageManager.getInstance().addMessage("已有对手");
		}
	}

}
