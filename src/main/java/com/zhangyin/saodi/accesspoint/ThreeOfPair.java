package com.zhangyin.saodi.accesspoint;

import com.zhangyin.saodi.base.AbstractNode;
import com.zhangyin.saodi.base.Direction;

/**
 * 三三组合的一个组合对象
 * 为了把几样东西封装到一个对象里罢了
 * @author Administrator
 *
 */
public class ThreeOfPair {
	
	
	AbstractNode realNode; 
	Direction d;
	AbstractNode another;
	public ThreeOfPair(AbstractNode realNode, Direction d, AbstractNode another) {
		super();
		this.realNode = realNode;
		this.d = d;
		this.another = another;
	}
	public void setAccessPoint(boolean b) {
		realNode.setAccessPoint(b);
		another.setAccessPoint(b);
	}

}
