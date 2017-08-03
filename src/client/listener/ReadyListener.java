package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.net.Header;
import client.ui.GameFrame;

/**
 * 准备按钮监听类
 */
public class ReadyListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if (Data.connected) {
			if (Data.oppoId != 0) {
				if (!Data.ready) {
					MessageManager.getInstance().addMessage("请稍等");
					IOManager.getInstance().getPs().println(Header.OPERATION + Header.START);

					Data.ready = true;
				} else {
					MessageManager.getInstance().addMessage("已经准备过了");
				}
			} else {
				JOptionPane.showMessageDialog(GameFrame.getInstance(), "请先选择一个对手");
			}
		}
	}

}
