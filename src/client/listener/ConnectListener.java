package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.Data;
import client.manager.IOManager;
import client.net.Connecter;
import client.net.Header;
import client.net.Receiver;
import client.ui.GameFrame;

/**
 * 连接按钮监听类
 */
public class ConnectListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!Data.connected) {
			new Connecter().connect();
			if (Data.connected) {
				// 开始新的接受数据线程
				new Thread(new Receiver()).start();
				String name = GameFrame.getInstance().getLoginPanel().getNameTextField().getText();
				// 更新玩家列表
				IOManager.getInstance().getPs().println(Header.INIT + name);
			}
		}
	}

}
