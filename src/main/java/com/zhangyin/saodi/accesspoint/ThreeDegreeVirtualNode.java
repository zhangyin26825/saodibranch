package com.zhangyin.saodi.accesspoint;

import com.zhangyin.saodi.base.VirtualNode;

public class ThreeDegreeVirtualNode extends VirtualNode {
	
	ThreeOfPair  t;
	
	VirtualNode pair;
	
	

	public ThreeDegreeVirtualNode(ThreeOfPair t) {
		super();
		this.t = t;
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

}
