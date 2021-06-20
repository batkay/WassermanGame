package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
//import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import static game.Utilities.*;

public class Background extends Canvas implements Runnable {
	
	JFrame f;
	Player p1;
	ArrayList<Entity> things;
	MouseInputs m;
	
	Enemy currentEn;
	boolean battling=false;
	
	String text=null;
	
	boolean pMove=false;
	Entity aButton = new Entity (50, 50, 50, 50);
	Entity iButton = new Entity (100, 100, 50, 50);
	
	Entity tlB = new Entity (50, 50, 50, 50);
	Entity trB = new Entity (50, 100, 50, 50);
	
	Entity blB=new Entity (100, 50, 50, 50);
	Entity brB=new Entity (100, 100, 50, 50);
	
	int level=1;
	
	boolean askedQ =false;
	String[] answers=null;
	
	Image button0;
	Image button1;
	Image button2;
	Image button3;
	
	public Background(Dimension s, JFrame f, Player p1, ArrayList<Entity> things, MouseInputs m){
		setSize(s); //set the frame to an initial size
		this.f=f; 
		this.p1=p1;
		this.things=things;
		this.m=m;
		
		try {
			button0 = ImageIO.read(new File("src/extras/firstOpt.png"));
			button1 = ImageIO.read(new File("src/extras/SecondOpt.png"));
			button2 = ImageIO.read(new File("src/extras/thirdOpt.png"));
			button3 = ImageIO.read(new File("src/extras/FourthOpt.png"));
		}
		catch (IOException e) {
			
		}
		
	}
	
	public void setEnemy(Enemy en) {
		currentEn=en;
	}
	public void setBattling(boolean battling) {
		this.battling=battling;
	}
	public void playerMove(boolean pMove) {
		this.pMove=pMove;
	}
	
	public Entity getA() {
		return aButton;
	}
	public Entity getI() {
		return iButton;
	}
	
	public Entity getTL() {
		return tlB;
	}
	public Entity getTR() {
		return trB;
	}
	public Entity getBL() {
		return blB;
	}
	public Entity getBR() {
		return brB;
	}
	
	public void askQ(String[] answers) {
		this.answers=answers;
		
		askedQ=true;
	}
	public void setLevel(int level) {
		this.level=level;
	}
	
	@Override
	public void run() {
		while (true) {
			//repaint();
			
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

        int pWidth=(int)(factor *p1.width);
        int pHeight=(int)(factor * p1.height);
		
		Image buffer=createImage(f.getWidth(), f.getHeight()); //create a new image to draw on
        Graphics2D bufferG=(Graphics2D) buffer.getGraphics(); //get its graphics
        
        //bufferG.drawString("x: "+ Main.p1.xPos+"\ny:" + Main.p1.yPos, 200, 205);
        
       
        if(battling) {
        	int enHeight = p1.height * factor * 2; //want enemy to be same size as player on screen, reguardless of how big enemy is
        	int enWidth = p1.width*factor * 2;
        	
        	aButton = new Entity (width/10, height*18/20-factor*50, factor*2*50, factor * 2 *50);
        	iButton = new Entity (width*7/10, height*18/20-factor*50, factor*2*50, factor*2*50);
        	
        	
        	bufferG.setColor(Color.YELLOW);
        	bufferG.fillRect(3*width/4-enWidth/2, height/8, enWidth, enHeight); //enemy drawing
        	
        	bufferG.setColor(Color.BLACK);
        	if(text!=null) {
        		bufferG.drawString(text, factor, height*6/10);
        	}
        	
        	bufferG.setColor(Color.BLUE);
        	//bufferG.fillRect(2*pWidth, height*3/8, pWidth*2, pHeight*2);
        	bufferG.drawImage(p1.getPic(), 2*pWidth, height*3/8, pWidth*2, pHeight*2, this);
        	
        	bufferG.setColor(Color.GREEN);
        	bufferG.fillRect(width/2, height*3/8, (int) ((p1.hp/p1.maxHp)*width/4), height/20);
        	
        	bufferG.setColor(Color.RED);
        	bufferG.fillRect(pWidth, height/8, (int) ((currentEn.hp/currentEn.maxHp)*width/4), height/20);
        	
        	if(pMove) {
        		bufferG.setColor(Color.CYAN);
            	bufferG.fillRect((int)(aButton.x-aButton.width/2), (int)(aButton.y-aButton.height/2), aButton.width, aButton.height);
            	bufferG.fillRect((int)(iButton.x-iButton.width/2), (int)(iButton.y-iButton.height/2), iButton.width, iButton.height);
        	}
        	
        	if(askedQ) {
        		tlB = new Entity (width/10, height*16/20-factor*50, factor*50, factor*50, button0);
            	trB = new Entity (width*6/10, height*16/20-factor*50, factor*50, factor*50, button1);
        		
            	blB=new Entity (width/10, height*18/20-factor*50, factor*50, factor *50, button2);
            	brB=new Entity (width*6/10, height*18/20-factor*50, factor*50, factor *50, button3);
            	
            	Entity[] buttons = {tlB, trB, blB, brB};

            	bufferG.setColor(Color.BLACK);
            	bufferG.drawString(answers[0], width*2/10, height*16/20-factor*50);
            	bufferG.drawString(answers[1], width*7/10, height*16/20-factor*50);
            	bufferG.drawString(answers[2], width*2/10, height*18/20-factor*50);
            	bufferG.drawString(answers[3], width*7/10, height*18/20-factor*50);

            	bufferG.setColor(Color.MAGENTA);

            	for (Entity button: buttons) {
                	//bufferG.fillRect((int)(button.x-button.width/2), (int)(button.y-button.height/2), button.width, button.height);
                	bufferG.drawImage(button.getPic(), (int)(button.x-button.width/2), (int)(button.y-button.height/2), button.width, button.height, this);
            	}
            	
            	
        		askedQ=false;
        	}
        	
        	
        }
        else {
        	for(Entity en: things) {
        		if(en.getClass().equals(Enemy.class)) {
        			bufferG.setColor(Color.YELLOW);
        			
        		}
        		else {
        			bufferG.setColor(Color.GRAY);

        		}
        		
            	Point relP=relativeToP(p1, (int) en.x, (int) en.y);
            	bufferG.fillRect(relP.x*factor -en.width/2 +width/2, relP.y* factor -en.height/2 +height/2, en.width*factor, en.height*factor);
            }
            
            
            bufferG.rotate(m.angle+Math.PI/2, width/2, height/2);
            bufferG.setColor(Color.BLUE);
            //bufferG.fillRect(width/2-pWidth/2, height/2-pHeight/2, pWidth, pHeight); //for player
        	bufferG.drawImage(p1.getPic(), width/2-pWidth/2, height/2-pHeight/2, pWidth, pHeight, this);

        }
        
        
        g.drawImage(buffer, 0, 0, this); //place the buffer image onto the actual
	}
	public void displayText(String text) {
		this.text=text;
	}
	

}
