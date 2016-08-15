package com.zhangyin.saodi.base;

public class RealNode extends AbstractNode{

	public boolean isReal() {
		return true;
	}
	private int rowNum; //所在的行
	private int colNum; //所在的列
	
	
	/** 判断是不是出入点    主要是判断，在哪些地方插入虚拟节点
	 * 出入点是用来划分地图的，出入点之间可以插入虚拟节点，
	 * 判断一个节点是不是出入点 暂定的判定逻辑如下
	 * 	   当前节点周围没有没有出入点，如果当前节点连接的节点中，已经有出入点了，则当前节点不是出入点
	 * 	 如果当前节点的度是2，判断连接的两个节点的度数，如果两个节点的度数都是2，则不是，其余情况下都是
	 * 					 成对的判断，暂时还有点问题，如果连接的两个节点的度数中，有一个2，例如2,3或者2,4，成对的判断，以当前节点，跟不是2的节点 作为一个成对的出入点
	 * 										  如果连接的两个节点的度数都大于2，例如，3，3  3,4  4,4 这种情况下，这里比较乱，再定义吧
	 *  如果当前节点的度是3，则判断连接的节点中度为3的节点，这是两个度为3的节点，互相连接的情况，这两个度为3的节点，剩下的那个方向，是不是互相对应，
	 *  									 例如 AB两个节点上下相连  A在下，B在上，A节点除了上，还有右和下，B节点除了下，还有左和上，
	 *  									 那么 A剩下没有的方式是左， B剩下没有的方向是右，  左右互相对应，所以AB可以作为一组出入点
	 * 
	 */
	private boolean isAccessPoint;
	public RealNode(int rowNum, int colNum) {
		super();
		this.rowNum = rowNum;
		this.colNum = colNum;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public int getColNum() {
		return colNum;
	}
	public void setColNum(int colNum) {
		this.colNum = colNum;
	}
	public boolean isAccessPoint() {
		return isAccessPoint;
	}
	public void setAccessPoint(boolean isAccessPoint) {
		this.isAccessPoint = isAccessPoint;
	}
	
	
	
	
	

}
