package client.net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import client.Data;
import client.manager.IOManager;
import client.manager.MessageManager;
import client.ui.GameFrame;

/**
 * 服务器连接/断开连接
 */
public class Connecter {

	Socket socket = null; // Socket连接

	public void connect() {
		try {
			// 从界面获取服务器IP及端口号
			String ipStr = GameFrame.getInstance().getLoginPanel().getIpTextField().getText();
			String portStr = GameFrame.getInstance().getLoginPanel().getPortTextField().getText();
			int portValue = Integer.parseInt(portStr);
			// 创建Socket连接
			socket = new Socket(ipStr, portValue);
			// 设置输入输出流
			IOManager.getInstance().setBr(new InputStreamReader(socket.getInputStream()));
			IOManager.getInstance().setPs(socket.getOutputStream());

			Data.connected = true;
			MessageManager.getInstance().addMessage("服务器已连接");
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(GameFrame.getInstance(), "找不到服务器");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(GameFrame.getInstance(), "服务器连接出错");
		}
	}

	public void disconnect() {
		if (socket.isConnected()) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
