package com.zhangyin.saodi.base;

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
}
