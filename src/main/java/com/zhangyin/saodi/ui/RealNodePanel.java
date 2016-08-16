package com.zhangyin.saodi.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import com.zhangyin.saodi.base.Direction;
import com.zhangyin.saodi.base.RealNode;

public class RealNodePanel extends JPanel{
	
	public static int defaultlength=2;
	public static int virtualLentth=6;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	RealNode r;

	public RealNodePanel(RealNode r) {
		this.r = r;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if(r==null){
			this.setBackground(Color.BLACK);
		}else{
			this.setBackground(Color.lightGray);	
			int top=defaultlength,  left=defaultlength,  bottom=defaultlength,  right=defaultlength;
			if(r.getMoves().containsKey(Direction.Up)&&!r.getMoves().get(Direction.Up).isReal()){
				top=virtualLentth;
			}
			if(r.getMoves().containsKey(Direction.Left)&&!r.getMoves().get(Direction.Left).isReal()){
				left=virtualLentth;
			}
			if(r.getMoves().containsKey(Direction.Down)&&!r.getMoves().get(Direction.Down).isReal()){
				bottom=virtualLentth;
			}
			if(r.getMoves().containsKey(Direction.Right)&&!r.getMoves().get(Direction.Right).isReal()){
				right=virtualLentth;
			}
			
			MatteBorder createMatteBorder = BorderFactory.createMatteBorder(top, left, bottom, right, Color.yellow);
			this.setBorder(createMatteBorder);
		}
		
		
	}
	
	
	

}
