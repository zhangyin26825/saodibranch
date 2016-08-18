package com.zhangyin.saodi.area;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.zhangyin.saodi.base.AbstractNode;
import com.zhangyin.saodi.base.RealNode;
import com.zhangyin.saodi.base.VirtualNode;

/** 
 * 虚拟节点 把整个LevelMap 分成若干个区域
 * 
 * 区域的生成，可以从一个虚拟节点，开始广度优先遍历，到达一个虚拟节点就停止
 * 可能的问题是， 虚拟节点是成对存在的，可能造成一对虚拟节点都在某一个区域内，
 * 这个时候，就要清除这两个虚拟节点，把它们取消，还原到没有增加虚拟节点的那种情况下去
 * @author Administrator
 *
 */
public class Area {
	
	Set<VirtualNode> virtualNodes;
	
	Set<RealNode>  realnodes;

	public Area(Set<AbstractNode> areaNode) {
		virtualNodes=new HashSet<>();
		realnodes=new HashSet<>();
		for (Iterator iterator = areaNode.iterator(); iterator.hasNext();) {
			AbstractNode abstractNode = (AbstractNode) iterator.next();
			if(abstractNode.isReal()){
				realnodes.add((RealNode)abstractNode);
			}else{
				virtualNodes.add((VirtualNode)abstractNode);
			}
		}
		checkPairVirtualNode();
	}
	//检测 是否有成对的一对虚拟节点，都在同一个区域，
	//如果是这种情况的话，需要把这两个虚拟节点全部删除掉
	public void checkPairVirtualNode(){
		for (Iterator iterator = virtualNodes.iterator(); iterator.hasNext();) {
			VirtualNode virtualNode = (VirtualNode) iterator.next();
			VirtualNode pair = virtualNode.getPair();
			assert pair!=null;
			if(virtualNodes.contains(pair)){
				System.out.println("进行了一次虚拟节点的删除");
				VirtualNode.destroy(virtualNode, pair);
			}	
		}
	}
	
	

}
