package com.zhangyin.saodi.base;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
/**
 * 地图的一个节点，这里定义为一个抽象类，需要派生出两个类，
 * 一个是 真实的节点，
 * 一个是虚拟的节点，
 * 主要意图在于 
 *  在真实的节点之间插入虚拟的节点   通过虚拟的节点把整个地图分割成若干个小的地图
 *  这里需要的考虑的是，应该在哪些真实节点之间插入虚拟节点    真实节点的  isAccessPoint字段就是用来判断是否在当前节点插入虚拟节点  
 *  判断当前节点 是 isAccessPoint ，然后怎么插入虚拟节点，又是另一个要考虑的问题，等做到的时候再说吧
 *  切割为若干个小图之后，具体怎么做，还不清晰
 * 
 * @author Administrator
 *
 */
public abstract class  AbstractNode {
	//这个字段主要用来在图搜索的时候，标识当前节点是否已经经过了。
	private boolean marked=false;
	//当前节点 在某个方向上 连接的其他节点
	private Map<Direction,AbstractNode> moves;
	
    public	AbstractNode(){
    	moves=new HashMap<Direction, AbstractNode>();
	}
	public void put(Direction d,AbstractNode node){
		moves.put(d, node);
	}
	//节点的度  连接了几个方向
	public int degree(){
		return moves.size();
	}
	
	public Set<Direction> directions(){
		return moves.keySet();
	}
	
	//节点的类型，子类去实现
	public abstract boolean isReal();
	public boolean isMarked() {
		return marked;
	}
	public void setMarked(boolean marked) {
		this.marked = marked;
	}
	public Map<Direction, AbstractNode> getMoves() {
		return moves;
	}
	public void setMoves(Map<Direction, AbstractNode> moves) {
		this.moves = moves;
	}
	
	
	
}
