package com.zhangyin.saodi.base;

/**
 * 关卡地图 每个关卡的地图
 * 
 * @author Administrator
 *
 */
public class LevelMap {

	String map;
	Integer level;
	public Integer row;
	public Integer col;

	public RealNode[][] nodes;

	public LevelMap(String map, Integer level, Integer row, Integer col) {
		this.map = map;
		this.level = level;
		this.row = row;
		this.col = col;
		assert map.length() == row * col;
		init();
	}

	// 初始化 RealNode 数组 只包含所有的空白节点，所有的障碍物都是用null表示
	private void init() {
		nodes = new RealNode[row][col];
		assert map.length() == row * col;
		int count = 0;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (map.charAt(count) == '0') {
					RealNode n = new RealNode(i, j);
					nodes[i][j] = n;
				}
				count++;
			}
		}
		initALLMoveDirection();
	}

	// 初始化每个节点的连接 判断节点的上下左右是否有别的节点，有的话，修改moves信息
	public void initALLMoveDirection() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				RealNode n = nodes[i][j];
				if (n != null) {
					// 当前节点不能为空
					RealNode up = getUp(i, j);
					if (up != null) {
						// 当前节点的上方也不为空
						n.put(Direction.Up, up);
					}

					RealNode down = getDown(i, j);
					if (up != null) {
						// 当前节点的上方也不为空
						n.put(Direction.Down, down);
					}
					
					
					RealNode left = getLeft(i, j);
					if (up != null) {
						// 当前节点的上方也不为空
						n.put(Direction.Left, left);
					}
					
					RealNode right = getRight(i, j);
					if (up != null) {
						// 当前节点的上方也不为空
						n.put(Direction.Right, right);
					}

				}

			}
		}
		// initAccessPoint();	
	}

	


	// 判断一组数据是否超出了节点数组索引的范围
	private boolean isOutOfRange(int i, int j) {
		if (i >= 0 && i < row && j >= 0 && j < col) {
			return false;
		}
		return true;
	}

	private RealNode getUp(int i, int j) {
		if (isOutOfRange(i - 1, j)) {
			return null;
		} else {
			return nodes[i - 1][j];
		}
	}

	private RealNode getDown(int i, int j) {
		if (isOutOfRange(i + 1, j)) {
			return null;
		} else {
			return nodes[i + 1][j];
		}
	}

	private RealNode getLeft(int i, int j) {
		if (isOutOfRange(i, j - 1)) {
			return null;
		} else {
			return nodes[i][j - 1];
		}
	}

	private RealNode getRight(int i, int j) {
		if (isOutOfRange(i, j + 1)) {
			return null;
		} else {
			return nodes[i][j + 1];
		}
	}

}
