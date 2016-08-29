package com.zhangyin.saodi.accesspoint;

import com.zhangyin.saodi.base.AbstractNode;
import com.zhangyin.saodi.base.RealNode;
import com.zhangyin.saodi.base.VirtualNode;

public class TwoDegreeVirtualNode extends VirtualNode{
	//两度的节点需要一个真实的节点
	AbstractNode node;
	
	VirtualNode pair;
	
	RealNode realnode;

	public TwoDegreeVirtualNode(AbstractNode node,RealNode realnode) {
		super();
		this.node = node;
		this.realnode=realnode;
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

	@Override
	public RealNode getRealNode() {
		return realnode;
	}

	
	
	

}
