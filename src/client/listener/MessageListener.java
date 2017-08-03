package client.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.net.Header;
import client.ui.GameFrame;

/**
 * 消息发送监听类
 */
public class MessageListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String message = GameFrame.getInstance().getFunctionPanel().getMessagePanel().getMessageTextField().getText();
		int oppoId = Data.oppoId;
		if (oppoId != 0) {
			IOManager.getInstance().getPs().println(Header.CHAT + message + "&" + oppoId);
			GameFrame.getInstance().getFunctionPanel().getMessagePanel().getMessageTextField().setText("");
			MessageManager.getInstance().addMessage("我：" + message);
		} else {
			MessageManager.getInstance().addMessage("没有对手，不能说话");
			GameFrame.getInstance().getFunctionPanel().getMessagePanel().getMessageTextField().selectAll();
		}
	}

}
