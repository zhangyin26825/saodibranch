package com.zhangyin.saodi.base;

public class RealNode extends AbstractNode{

	public boolean isReal() {
		return true;
	}
	private int rowNum; //所在的行
	private int colNum; //所在的列
	
	
	/** 判断是不是出入点    主要是判断，在哪些地方插入虚拟节点
	 * 出入点是用来划分地图的，出入点之间可以插入虚拟节点，
	 */
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + colNum;
		result = prime * result + rowNum;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RealNode other = (RealNode) obj;
		if (colNum != other.colNum)
			return false;
		if (rowNum != other.rowNum)
			return false;
		return true;
	}
	
	
	
	
	
	
	

}
