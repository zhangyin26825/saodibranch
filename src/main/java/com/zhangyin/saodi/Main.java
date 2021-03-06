package com.zhangyin.saodi;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.zhangyin.saodi.accesspoint.VirtualNodeGenerator;
import com.zhangyin.saodi.area.Area;
import com.zhangyin.saodi.area.AreaFactory;
import com.zhangyin.saodi.area.NodeAreaMapping;
import com.zhangyin.saodi.base.LevelMap;
import com.zhangyin.saodi.base.RealNode;
import com.zhangyin.saodi.base.VirtualNode;
import com.zhangyin.saodi.ui.MyJFrame;

public class Main {

	public static void main(String[] args) {
		//level=72&x=27&y=27&map=000000010000110000100001000000011000000110010000100010100001101000111001010000010000101100010100101001100010010000000010000101101100010011000100000000101100000010001000001100000100011000010100001001111110000011000010100101000011111000001000000000001011000010000100110001011000011000000111110000100000011110000101111100010000000000000100001100001000011000000000100000100000101100110011000000000110010001000000011001000000000010011010011011100011111111000010000011000000001111110001100101000000010100000010110001001100001000100100010000001000101100001111100000001001110100000100100000000000000100111000000100110000110110001000011000010000100010011000000110001001000001011010010000010100001001000001000110001000111111100000100000000

			
		Integer row=27;
		Integer col=27;
		int level=72; 
		String map="000000010000110000100001000000011000000110010000100010100001101000111001010000010000101100010100101001100010010000000010000101101100010011000100000000101100000010001000001100000100011000010100001001111110000011000010100101000011111000001000000000001011000010000100110001011000011000000111110000100000011110000101111100010000000000000100001100001000011000000000100000100000101100110011000000000110010001000000011001000000000010011010011011100011111111000010000011000000001111110001100101000000010100000010110001001100001000100100010000001000101100001111100000001001110100000100100000000000000100111000000100110000110110001000011000010000100010011000000110001001000001011010010000010100001001000001000110001000111111100000100000000";
//		int row=2;
//		int col=2;
//		int level=2;
//		String map="0001";		
		
		LevelMap levelmap=new LevelMap(map, level, row, col);
		Set<RealNode> nodesets = levelmap.nodesets;
		System.out.println("真实节点的数量"+nodesets.size());
		for (Iterator iterator = nodesets.iterator(); iterator.hasNext();) {
			RealNode realNode = (RealNode) iterator.next();
			//System.out.println(realNode.getColNum()+"　　　　"+realNode.getColNum()+"  "+realNode.degree());
		}
		//根据地图信息，判断出所有的AccessPoint节点，生成虚拟节点，把虚拟节点插入到真实的节点之中
		VirtualNodeGenerator vmg=new VirtualNodeGenerator(levelmap);
		//得到所有的 虚拟节点
		List<VirtualNode> virtualNodes = vmg.getVirtualNodes();
		System.out.println("虚拟节点的数量"+virtualNodes.size());
		
		MyJFrame frame=new  MyJFrame(levelmap);
		
		AreaFactory factory=new AreaFactory(virtualNodes);
		List<Area> areas = factory.generatorArea();
		System.out.println("区域的数量为"+areas.size());
		for (Iterator iterator = areas.iterator(); iterator.hasNext();) {
			Area area = (Area) iterator.next();

			System.out.println("该区域的无起点解法有 "+area.getSolves().size());
			if(area.getSolves().size()==0){
				Set<RealNode> realnodes = area.getRealnodes();
				System.out.println(realnodes);	
			}
		}
		
		NodeAreaMapping nam=new NodeAreaMapping(areas);
		System.out.println("节点区域映射中虚拟节点的数量为"+nam.getMap().keySet().stream().filter(n->!n.isReal()).count());

	}

}
