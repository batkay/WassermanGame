/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import javax.swing.JComponent;
import javax.swing.JFrame;

import java.awt.Point;

import static game.Utilities.*;

public class MouseInputs extends MouseAdapter{
	/*
	 * Tracks where the mouse is on screen
	 */
	
	JFrame j;
	Player p;
	
	boolean clicked=false;
	int clicks=0;
	Point globalClick = new Point(0,0);
	Point click = new Point(0,0);
	
	public MouseInputs(JFrame j, Player p) {
		this.j=j;
		this.p=p;
	}
	double angle=0;
	
    //mouse
    @Override
    public void mouseClicked(MouseEvent e){
    	clicks++;
    }
    @Override
    public void mouseEntered(MouseEvent e){
        
    }
    @Override
    public void mouseExited(MouseEvent e){
        
    }
    @Override
    public void mousePressed(MouseEvent e){
        clicked=true;
        globalClick= screenToGlobal(p.x, p.y, e.getX(), e.getY(), j.getWidth(), j.getHeight());
        click= new Point(e.getX(), e.getY());
    }
    @Override
    public void mouseReleased(MouseEvent e){
        clicked=false;
    }
    @Override
    public void mouseMoved(MouseEvent e){        
    	angle=Math.atan2(e.getY()-j.getHeight()/2, e.getX()-j.getWidth()/2);
    	
    }
}
