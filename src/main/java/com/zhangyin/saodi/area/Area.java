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
	 * 
	 * 搜索的方法是，从一个虚拟节点开始， 
	 * 中间经过很多步，然后到达一个虚拟节点为结束，
	 * 结束的步骤肯定是达到一个虚拟节点之后
	 * 
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
	/***
	 *  从 一个node开始搜索
	 * @param node
	 * @param isfirst
	 */
	public void searchFromNode(AbstractNode node,boolean isfirst){
		//得到当前节点可以前进的方向
		Set<Direction> collect = node.getMoves().keySet().stream().filter(d->{
			AbstractNode temp=node.getMoves().get(d);
			if(realnodes.contains(temp)||virtualNodes.contains(temp)){
				return true;
			}
			return false;
			}).collect(Collectors.toSet());
		//遍历所有的方向
		for (Iterator iterator = collect.iterator(); iterator.hasNext();) {
			Direction direction = (Direction) iterator.next();
			//依据当前的方向，生成一步
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
			//end代表当前 步的结束节点
			AbstractNode end=s.peek();
			Step step=new Step(isfirst,node,end,direction,s);
			//把当前步设置为已经标记过
			step.mark(true);
			//把当前步加入到结果的栈上面去
			result.push(step);
			//根据最后节点的状态进行判断，如果是真实节点，递归调用当前的方法
			//如果是虚拟节点，判断当前是否结束，如果不是的话，继续虚拟节点的搜索
			// 这里的情况是，到达一个虚拟节点之后，可以达到当前区域的任意一个虚拟节点，重新开始进行这个步骤
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
			//当前步骤完成之后，把step设置为未标记，然后从结果斩出栈
			step.mark(false);
			result.pop();
			
		}
		
		
	}
	/**
	 * 区域完成的标记是 所有的真实节点已经标记，所有的必需的虚拟节点已经标记，所有不必需的虚拟节点可以随意
	 * @return
	 */
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
