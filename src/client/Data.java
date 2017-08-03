package client;

/**
 * 全局静态常量
 */
public final class Data {

	public static int last = -1; // 最后棋子位置

	public static int turn = 0; // 当前回合

	public static int myId = 0; // 我的ID
	public static int oppoId = 0; // 对手ID

	public static String myName = null; // 我的昵称

	public static int BLACK = 1; // 黑棋
	public static int WHITE = -1; // 白棋

	public static int myChess = 0; // 我的颜色
	public static int oppoChess = 0; // 对手颜色

	public static boolean connected = false; // 是否已连接
	public static boolean ready = false; // 是否已准备
	public static boolean started = false; // 是否已开始

	public static int[][] chessBoard = new int[15][15]; // 棋盘

}
