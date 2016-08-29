package com.zhangyin.saodi.accesspoint;

import com.zhangyin.saodi.base.RealNode;
import com.zhangyin.saodi.base.VirtualNode;

public class ThreeDegreeVirtualNode extends VirtualNode {
	
	ThreeOfPair  t;
	
	VirtualNode pair;
	
	RealNode realnode;

	public ThreeDegreeVirtualNode(ThreeOfPair t,RealNode realnode) {
		super();
		this.t = t;
		this.realnode=realnode;
	}

	@Override
	public boolean isRequire() {
		return false;
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
