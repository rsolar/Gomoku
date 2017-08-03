package server.tool;

/**
 * 检查是否存在五子连线
 */
public class BoardChecker {

	// 四个方向向量
	private final int dirx[] = { 1, 0, 1, 1 };
	private final int diry[] = { 0, 1, 1, -1 };

	public boolean check(int x, int y, int id, int[][] chessBoard) {
		for (int d = 0; d < 4; d++) {
			int count = 0;
			for (int i = 0; i < 5; i++) {
				int xx = x + dirx[d] * i, yy = y + diry[d] * i;
				if (isInsideBoard(xx, yy) && chessBoard[xx][yy] == id) {
					count++;
				} else {
					break;
				}
			}
			for (int i = 1; i < 5; i++) {
				int xx = x - dirx[d] * i, yy = y - diry[d] * i;
				if (isInsideBoard(xx, yy) && chessBoard[xx][yy] == id) {
					count++;
				} else {
					break;
				}
			}
			if (count >= 5) {
				return true;
			}
		}
		return false;
	}

	// 是否在棋盘范围内
	private boolean isInsideBoard(int x, int y) {
		if (x >= 0 && x < 15 && y >= 0 && y < 15) {
			return true;
		} else {
			return false;
		}
	}

}
