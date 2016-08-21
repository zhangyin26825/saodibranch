package com.zhangyin.saodi.area;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import com.zhangyin.saodi.base.AbstractNode;
import com.zhangyin.saodi.base.Direction;
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
	
	Set<VirtualNode> del;
	
	Queue<AbstractNode> queue;
	
	Stack<Step> result;
	 
	List<NoStartAreaAnswer> solves;

	public Area(Set<AbstractNode> areaNode) {
		virtualNodes=new HashSet<>();
		realnodes=new HashSet<>();
		solves=new ArrayList<>();
		for (Iterator iterator = areaNode.iterator(); iterator.hasNext();) {
			AbstractNode abstractNode = (AbstractNode) iterator.next();
			if(abstractNode.isReal()){
				realnodes.add((RealNode)abstractNode);
			}else{
				virtualNodes.add((VirtualNode)abstractNode);
			}
		}
		checkPairVirtualNode();
		result=new Stack<>();
		SearchFromVirtulaNode(true);
	}
	
	/**搜索  无起点的区域答案
	 * 从一个虚拟节点开始
	 */
	public  void SearchFromVirtulaNode(boolean isfirst){
		/**
		 * 这里是从虚拟节点开始的第一个虚拟节点，
		 */
		for (Iterator iterator = virtualNodes.iterator(); iterator.hasNext();) {
			VirtualNode virtualNode = (VirtualNode) iterator.next();
			if(virtualNode.isMarked()){
				continue;
			}
			searchFromNode(virtualNode, isfirst);
		}
	
	}
	
	public void searchFromNode(AbstractNode node,boolean isfirst){
		//得到当前节点可以前进的方向
		Set<Direction> collect = node.getMoves().keySet().stream().filter(d->{
			AbstractNode temp=node.getMoves().get(d);
			if(realnodes.contains(temp)||virtualNodes.contains(temp)){
				return true;
			}
			return false;
			}).collect(Collectors.toSet());
		
		for (Iterator iterator = collect.iterator(); iterator.hasNext();) {
			Direction direction = (Direction) iterator.next();
			
			Stack<AbstractNode> s=new Stack<>();
			AbstractNode temp = node.getMoves().get(direction);
			while(temp!=null&&!temp.isMarked()){	
				s.push(temp);
				if(!temp.isReal()){
					break;
				}
				temp = temp.getMoves().get(direction);	
			}
			if(s.isEmpty()){
				continue;
			}
			AbstractNode end=s.peek();
			Step step=new Step(isfirst,node,end,direction,s);
			step.mark(true);
			result.push(step);
			if(end.isReal()){
				//行走一步之后，到达一个真实节点
				searchFromNode(end, false);
			}else{
				//行走一步之后，达到一个虚拟节点
				//判断是否结束，如果结束的话，
				if(isfinish()){
					solves.add(new NoStartAreaAnswer(this, result));
				}else{
					SearchFromVirtulaNode(false);
				}		
			}
			step.mark(false);
			result.pop();
			
		}
		
		
	}
	
	public boolean isfinish(){
		for (Iterator iterator = realnodes.iterator(); iterator.hasNext();) {
			RealNode realNode = (RealNode) iterator.next();
			if(!realNode.isMarked()){
				return false;
			}
		}
		for (Iterator iterator = virtualNodes.iterator(); iterator.hasNext();) {
			VirtualNode virtualNode = (VirtualNode) iterator.next();
			if(!((virtualNode.isRequire()&&virtualNode.isMarked())||!virtualNode.isRequire())){
				return false;
			}
			
		}
		return true;
	}
	
	
	
	//检测 是否有成对的一对虚拟节点，都在同一个区域，
	//如果是这种情况的话，需要把这两个虚拟节点全部删除掉
	public void checkPairVirtualNode(){
		 del=new HashSet<>();
		for (Iterator iterator = virtualNodes.iterator(); iterator.hasNext();) {
			VirtualNode virtualNode = (VirtualNode) iterator.next();
			VirtualNode pair = virtualNode.getPair();
			assert pair!=null;
			if(virtualNodes.contains(pair)){
				//System.out.println("进行了一次虚拟节点的删除");
				del.add(virtualNode);
				del.add(pair);
				VirtualNode.destroy(virtualNode, pair);
			
//				virtualNodes.remove(virtualNode);
//				virtualNodes.remove(pair);
			}	
		}
		assert del.size()%2==0;
		virtualNodes.removeAll(del);
	}

	public List<NoStartAreaAnswer> getSolves() {
		return solves;
	}

	public void setSolves(List<NoStartAreaAnswer> solves) {
		this.solves = solves;
	}
	
	
	
	

}
