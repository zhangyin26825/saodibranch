package com.zhangyin.saodi.accesspoint;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.zhangyin.saodi.base.AbstractNode;
import com.zhangyin.saodi.base.Direction;
import com.zhangyin.saodi.base.LevelMap;
import com.zhangyin.saodi.base.RealNode;
import com.zhangyin.saodi.base.VirtualNode;

public class VirtualNodeGenerator {

	List<RealNode> twoDegreeNodes;

	List<RealNode> threeDegreeNodes;
	
	AccessPointJudge judge;
	
	List<VirtualNode> virtualNodes;

	public VirtualNodeGenerator(LevelMap levelmap) {
		twoDegreeNodes = levelmap.getTwoDegreeNodes();
		threeDegreeNodes = levelmap.getThreeDegreeNodes();
		judge=new AccessPointJudge();
		virtualNodes=new LinkedList<>();
		/**
		 * 找到两度的AccessPoint 然后插入虚拟节点
		 */
		initTwoDegree();
		/**
		 * 找到三度的AccessPoint 然后插入虚拟节点
		 */
		initThreeDegree();
	}
	
	private void initThreeDegree() {
		for (Iterator iterator = threeDegreeNodes.iterator(); iterator.hasNext();) {
			RealNode realNode = (RealNode) iterator.next();
			boolean accessPoint = judge.isAccessPoint(realNode);
			if(accessPoint){
				ThreeOfPair t=judge.getThreeOfPair(realNode);
				assert t!=null;
				t.setAccessPoint(true);
				generatorVirtualNode(t.realNode,t.d,t.another);
			}
		}
		
	}

	public void initTwoDegree(){
		for (Iterator iterator = twoDegreeNodes.iterator(); iterator.hasNext();) {
			RealNode realNode = (RealNode) iterator.next();
			boolean accessPoint = judge.isAccessPoint(realNode);
			if(accessPoint){
				realNode.setAccessPoint(true);
				generateTwoDegreeVirtualNode(realNode);
			}
			
		}	
	}
	
	//两度的accessPoint 插入虚拟节点  
	private void generateTwoDegreeVirtualNode(RealNode realNode){
		//随便选一个方向，
		Direction d=realNode.getMoves().keySet().iterator().next();
		//这个方向上连接的节点
		AbstractNode another = realNode.getMoves().get(d);
		another.setAccessPoint(true);
		generatorVirtualNode(realNode, d, another);
	}
	
	public void generatorVirtualNode(AbstractNode realNode, Direction d, AbstractNode another){
		assert realNode.getMoves().get(d)==another;
		assert another.getMoves().get(Direction.getInverseDirection(d))==realNode;
		
		/** 在  realNode  和 another 两个节点之间，新增两个虚拟节点   
		 *   realNodevirtual  为靠近  realnode的一端
		 *   anothervirtual   为靠近 another的一端
		 * 
		 */
		TwoDegreeVirtualNode realNodevirtual=new TwoDegreeVirtualNode(realNode);
		TwoDegreeVirtualNode anothervirtual=new TwoDegreeVirtualNode(realNode);
		realNodevirtual.setPair(anothervirtual);
		anothervirtual.setPair(realNodevirtual);
		
		
		realNodevirtual.put(d, anothervirtual);
		anothervirtual.put(Direction.getInverseDirection(d), realNodevirtual);
		
		realNode.put(d, realNodevirtual);
		realNodevirtual.put(Direction.getInverseDirection(d), realNode);
		
		another.put(Direction.getInverseDirection(d), anothervirtual);
		anothervirtual.put(d, another);

		virtualNodes.add(realNodevirtual);
		virtualNodes.add(anothervirtual);	
		assert realNode.getMoves().get(d)==realNodevirtual;
		assert realNodevirtual.getMoves().get(d)==anothervirtual;
		assert anothervirtual.getMoves().get(d)==another;
		
		assert another.getMoves().get(Direction.getInverseDirection(d))==anothervirtual;
		assert anothervirtual.getMoves().get(Direction.getInverseDirection(d))==realNodevirtual;
		assert realNodevirtual.getMoves().get(Direction.getInverseDirection(d))==realNode;
		
		}	

	public List<VirtualNode> getVirtualNodes() {
		return virtualNodes;
	}

	public void setVirtualNodes(List<VirtualNode> virtualNodes) {
		this.virtualNodes = virtualNodes;
	}
	
	

}
