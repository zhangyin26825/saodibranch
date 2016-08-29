package com.zhangyin.saodi.area;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import com.zhangyin.saodi.base.AbstractNode;
import com.zhangyin.saodi.base.VirtualNode;

/**
 * 
 * @author Administrator
 *
 */
public class AreaFactory {
	
	Set<VirtualNode> set;
	
	List<VirtualNode> virtualNodes;
	
	
	
	
	public AreaFactory(List<VirtualNode> virtualNodes) {
		super();
		this.virtualNodes = virtualNodes;
		set=new HashSet<>(virtualNodes.size());
	}




	public  List<Area> generatorArea(){
		List<Area> result=new ArrayList<>();
		for (Iterator iterator = virtualNodes.iterator(); iterator.hasNext();) {
			//从一个虚拟节点开始搜索
			VirtualNode virtualNode = (VirtualNode) iterator.next();
			if(set.contains(virtualNode)){
				continue;
			}
			//一个区域的所有节点   生成Area对象用
			Set<AbstractNode> areaNode=new LinkedHashSet<>();
			areaNode.add(virtualNode);
			set.add(virtualNode);
			

			//获取第一个虚拟节点相连的所有节点，排除掉 成对的那个虚拟节点
			List<AbstractNode> collect = virtualNode.values().stream().filter(n->n.isReal()).collect(Collectors.toList());
			assert collect.size()>0;
			Queue<AbstractNode> queue=new LinkedList<>();
			queue.addAll(collect);
			areaNode.addAll(collect);
			
			AbstractNode node;
			
			while((node=queue.poll())!=null){
				if(node.isReal()){
					//真实节点    
					//获取一个节点   相连的不包含在 areaNode 中的节点 防止一个节点重复进入队列
					Collection<AbstractNode> values = node.values().stream()
							.filter(n->!areaNode.contains(n))
							.collect(Collectors.toList());
					queue.addAll(values);
					areaNode.addAll(values);
		
				}else{
					//虚拟节点
					set.add((VirtualNode)node);
					areaNode.add(node);	
				}
				
			}	
			//队列循环结束之后，根据 Set<AbstractNode> areaNode 生成一个Area对象
			result.add(new Area(areaNode));
		}
		assert set.size()==virtualNodes.size();
		return result;
	}  

}
