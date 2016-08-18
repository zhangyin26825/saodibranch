package com.zhangyin.saodi.base;

import java.util.Iterator;

public abstract class VirtualNode extends AbstractNode{

	@Override 
	public boolean isReal() {
		return false;
	}
	
	@Override
	public boolean isAccessPoint() {	
		return false;
	}
	//是不是必需的
	public abstract boolean isRequire();
	
	/** 得到另一个虚拟节点  
	 * 虚拟节点都是成对存在的，这是为了切割成子图的时候，一个虚拟节点只属于一个子图，做一些判断的时候方便些
	 * 
	 */
	public abstract VirtualNode getPair();
	
	
	public static void destroy(VirtualNode a,VirtualNode b){
		assert a.getPair()==b;
		assert b.getPair()==a;
		
		Direction atob=null;
		for (Iterator iterator = a.getMoves().keySet().iterator(); iterator.hasNext();) {
			Direction type = (Direction) iterator.next();
			if(a.getMoves().get(type)==b){
				atob=type;
			}	
		}
		assert atob!=null;
		AbstractNode anode=a.getMoves().get(Direction.getInverseDirection(atob));
		AbstractNode bnode=b.getMoves().get(atob);
		assert anode.isReal();
		assert bnode.isReal();
		
		anode.put(atob, bnode);
		bnode.put(Direction.getInverseDirection(atob), anode);
	}
}
