package server.ui;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import server.net.ServerThread;
import server.tool.MessageManager;

/**
 * 服务端窗口
 */
public class ServerFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static ServerFrame instance = null;

	ServerSocket serversocket = null; // 服务端Socket

	private ClientPanel clientPanel = null; // 客户端列表面板
	private MatchsPanel matchsPanel = null; // 配对列表面板
	private MessagePanel messagePanel = null; // 消息面板

	private ServerFrame() {
		super("五子棋对战 服务端");

		this.setLayout(new BorderLayout());
		this.add(getMatchsPanel(), BorderLayout.EAST);
		this.add(getClientPanel(), BorderLayout.WEST);
		this.add(getMessagePanel(), BorderLayout.SOUTH);

		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void launchFrame() {
		try {
			// 打开服务端端口
			serversocket = new ServerSocket(46666);
			MessageManager.getInstance().addMessage("服务器已经启动");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "端口被占用！请检查端口");
			System.exit(0);
		}
		while (true) {
			try {
				// 接受客户端连接
				Socket socket = serversocket.accept();
				// 新进程处理客户端连接
				Thread thread = new Thread(new ServerThread(socket));
				thread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ClientPanel getClientPanel() {
		if (clientPanel == null) {
			clientPanel = new ClientPanel();
		}
		return clientPanel;
	}

	public MatchsPanel getMatchsPanel() {
		if (matchsPanel == null) {
			matchsPanel = new MatchsPanel();
		}
		return matchsPanel;
	}

	public MessagePanel getMessagePanel() {
		if (messagePanel == null) {
			messagePanel = new MessagePanel();
		}
		return messagePanel;
	}

	public static ServerFrame getInstance() {
		if (instance == null) {
			instance = new ServerFrame();
		}
		return instance;
	}

}
