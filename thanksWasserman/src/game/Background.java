package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
//import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;

import static game.Utilities.*;

public class Background extends Canvas implements Runnable{
	
	JFrame f;
	Player p1;
	ArrayList<Entity> things;
	MouseInputs m;
	
	public Background(Dimension s, JFrame f, Player p1, ArrayList<Entity> things, MouseInputs m) {
		setSize(s); //set the frame to an initial size
		this.f=f; 
		this.p1=p1;
		this.things=things;
		this.m=m;
	}
	@Override
	public void run() {
		while (true) {
			repaint();
			try {
				Thread.sleep(10);
				
			}
			catch(InterruptedException e) {}
		}
	}
	
	@Override
	public void update(Graphics g) {
		paint(g);
	}
	
	@Override
	public void paint(Graphics g) {
		int width=f.getWidth();
		int height= f.getHeight();
		
        //player default size will be 50x50, then scale everything accordingly by window size
        int smaller=Math.min(width, height);
        int factor = (int) Math.round((smaller/20.0)/50.0); //50*factor= new value

		
		Image buffer=createImage(f.getWidth(), f.getHeight()); //create a new image to draw on
        Graphics2D bufferG=(Graphics2D) buffer.getGraphics(); //get its graphics
        
        //bufferG.drawString("x: "+ Main.p1.xPos+"\ny:" + Main.p1.yPos, 200, 205);
        
        for(Entity en: things) {
        	Point relP=relativeToP(p1, (int) en.x, (int) en.y);
        	bufferG.fillRect(relP.x*factor -en.width/2 +width/2, relP.y* factor -en.height/2 +height/2, en.width*factor, en.height*factor);
        }
        
        int pWidth=(int)(factor *p1.width);
        int pHeight=(int)(factor * p1.height);
        
        bufferG.rotate(m.angle, width/2, height/2);
        bufferG.setColor(Color.BLUE);
        bufferG.fillRect(width/2-pWidth/2, height/2-pHeight/2, pWidth, pHeight);
        
        g.drawImage(buffer, 0, 0, this); //place the buffer image onto the actual
	}
	
	

}
