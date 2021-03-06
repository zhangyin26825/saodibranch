package com.zhangyin.saodi.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
 *  需要指出的一点是，任何节点，除了起点和终点之外，2度的节点 两条边是一定要经过的，从一条边进入，然后从另一条边出去，
 * 										3度和4度，都要舍弃一些边
 * 我们需要考虑的是 从2度的节点，进行合理的推理，来猜测 行走的路线，与起始点的位置，
 * 但是整个页面不可能有一堆2度的节点，切割成若干个页面，所以需要加上特殊的3度节点的组合来实现。
 * 思路是往这方面走，怎么做就看情况了
 * @author Administrator
 *
 */
public abstract class  AbstractNode {
	//这个字段主要用来在图搜索的时候，标识当前节点是否已经经过了。
	private boolean marked=false;
	//当前节点 在某个方向上 连接的其他节点
	private Map<Direction,AbstractNode> moves;
	
	private boolean isAccessPoint=false;
	
    public	AbstractNode(){
    	//总结一个问题，这里如果不用LinkedHashMap  用hashMap 会造成debug模式跟 正常运行模式运行的结果不一样 
    	//hashMap 取key的顺序是不定的，搜索两度的节点，设置AccessPoint可能使设置方位出错，因为有些随机取一条边 使整个地图的切割不一致
    	moves=new LinkedHashMap<Direction, AbstractNode>();
	}
	public void put(Direction d,AbstractNode node){
		if(!this.isReal()&&!node.isReal()){
			throw new IllegalArgumentException("虚拟节点不能连接虚拟节点");
		}
		moves.put(d, node);
	}
	
	public AbstractNode  get(Direction d){
		return moves.get(d);
	}
	
	public boolean containsKey(Direction d){
		return moves.containsKey(d);
	}
	
	public Collection<AbstractNode>  values(){
		return moves.values();
	}
	
	//节点的度  连接了几个方向
	public int degree(){
		return moves.size();
	}
	
	public Set<Direction> directions(){
		return moves.keySet();
	}
	/**
	 *  得到所有可以移动的方向
	 * @return
	 */
	public Set<Direction> getcanMoveDirection(){
		Set<Direction> collect = moves.keySet().stream().filter(n->!moves.get(n).isMarked()).collect(Collectors.toSet());	
		return collect;
	}
	
	//节点的类型，子类去实现
	public abstract boolean isReal();
	
	
	public boolean isMarked() {
		return marked;
	}
	public void setMarked(boolean marked) {
		this.marked = marked;
	}
	
	public boolean isAccessPoint() {
		return isAccessPoint;
	}
	public void setAccessPoint(boolean isAccessPoint) {
		this.isAccessPoint = isAccessPoint;
	}
	
	
	
	
	
}
