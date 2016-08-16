package com.zhangyin.saodi.accesspoint;

import com.zhangyin.saodi.base.AbstractNode;
import com.zhangyin.saodi.base.VirtualNode;

public class TwoDegreeVirtualNode extends VirtualNode{
	//两度的节点需要一个真实的节点
	AbstractNode node;
	
	VirtualNode pair;
	
	

	public TwoDegreeVirtualNode(AbstractNode node) {
		super();
		this.node = node;
	}

	@Override
	public boolean isRequire() {
		return true;
	}

	@Override
	public VirtualNode getPair() {
		return pair;
	}

	public void setPair(VirtualNode pair) {
		this.pair = pair;
	}
	
	

}
