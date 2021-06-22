package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
//import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JFrame;

import static game.Utilities.*;

public class Background extends Canvas implements Runnable {
	/*
	 * Drawing class
	 */
	JFrame f;
	Player p1;
	ArrayList<Entity> things;
	MouseInputs m;
	
	Enemy currentEn;
	boolean battling=false;
	boolean displayingMessage=false;
	
	String text=null;
	String textBox=null;
	
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
	
	Image attackButton;
	Image itemButton;
	
	Font script;
	
	Clip clip;
	
	public void run() {
	      clip.loop(Clip.LOOP_CONTINUOUSLY);

	}
	public void playMusic(String musicLocation){
		  try{
		    File musicPath = new File(musicLocation);
		    if(musicPath.exists()){
		      AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
		      clip = AudioSystem.getClip();
		      clip.open(audioInput);

		      FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

		      gainControl.setValue(-40.0f); // Reduce volume by 10 decibels
		      clip.start();
		      /*try {
		            Thread.sleep(200000);
		        }
		      catch (InterruptedException e) {
		            e.printStackTrace();
		        }*/
		      //JOptionPane.showMessageDialog(null, "test");
		    }
		    else{
		      System.out.println("bruh");
		    }

		  }
		  catch(Exception e){
		    e.printStackTrace();
		  }
		}
	
	public Background(Dimension s, JFrame f, Player p1, ArrayList<Entity> things, MouseInputs m, Font script){
		setSize(s); //set the frame to an initial size
		this.f=f; 
		this.p1=p1;
		this.things=things;
		this.m=m;
		this.script=script;
		
		//load all the images for buttons
		try {
			button0 = ImageIO.read(new File("src/extras/firstOpt.png"));
			button1 = ImageIO.read(new File("src/extras/SecondOpt.png"));
			button2 = ImageIO.read(new File("src/extras/thirdOpt.png"));
			button3 = ImageIO.read(new File("src/extras/FourthOpt.png"));
			attackButton = ImageIO.read(new File("src/extras/sword.png"));
			itemButton = ImageIO.read(new File("src/extras/bag.png"));

			
		}
		catch (IOException e) {
			
		}
		
	}
	
	//lots of information the class needs lmao
	public void giveFont(Font f) {
		script=f;
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
        bufferG.setFont(script);
        
        //make background teal
		bufferG.setColor(new Color(177, 255, 255));

        bufferG.fillRect(0, 0, width, height);

            	
        bufferG.setColor(Color.GRAY);
       
        if(battling) {
        	int enHeight = p1.height * factor * 2; //want enemy to be same size as player on screen, reguardless of how big enemy is
        	int enWidth = p1.width*factor * 2;
        	
        	aButton = new Entity (width/10, height*18/20-factor*50, factor*2*50, factor * 2 *50, attackButton);
        	iButton = new Entity (width*7/10, height*18/20-factor*50, factor*2*50, factor*2*50, itemButton);
        	
        	//draw enemy
        	if(currentEn.pic!=null) {
        		bufferG.drawImage(currentEn.pic, 3*width/4-enWidth/2, height/8, enWidth, enHeight, this);
        	}
        	else {
        		bufferG.setColor(Color.YELLOW);
            	bufferG.fillRect(3*width/4-enWidth/2, height/8, enWidth, enHeight); //enemy drawing
        	}
        	
        	//display any messages
        	bufferG.setColor(Color.BLACK);
        	if(text!=null) {
        		bufferG.drawString(text, factor, height*6/10);
        		text=null;
        	}
        	
        	//draw Jimmy the Java
        	bufferG.setColor(Color.BLUE);
        	bufferG.drawImage(p1.getPic(), 2*pWidth, height*3/8, pWidth*2, pHeight*2, this);
        	
        	//player hp and enemy hp bar
        	bufferG.setColor(Color.GREEN);
        	bufferG.fillRect(width/2, height*3/8, (int) ((p1.hp/p1.maxHp)*width/4), height/20);
        	
        	bufferG.setColor(Color.RED);
        	bufferG.fillRect(pWidth, height/8, (int) ((currentEn.hp/currentEn.maxHp)*width/4), height/20);
        	
        	//if player is choosing what attack to use, display the buttons
        	if(pMove) {
        		bufferG.drawImage(aButton.pic, (int)(aButton.x-aButton.width/2), (int)(aButton.y-aButton.height/2), aButton.width, aButton.height,this);
        		bufferG.drawImage(iButton.pic, (int)(iButton.x-iButton.width/2), (int)(iButton.y-iButton.height/2), iButton.width, iButton.height,this);

        	}
        	
        	//display answer choices if question asked
        	if(askedQ) {
        		//tlB = new Entity (factor*50, height*16/20-factor*50, factor*50, factor*50, button0);
            	//trB = new Entity (width*6/10, height*16/20-factor*50, factor*50, factor*50, button1);
        		
            	//blB=new Entity (factor*50, height*18/20-factor*50, factor*50, factor *50, button2);
            	//brB=new Entity (width*6/10, height*18/20-factor*50, factor*50, factor *50, button3);
            	
            	tlB = new Entity (factor*50, height*16/20-factor*50, factor*50, factor*50, button0);
            	trB = new Entity (factor*50, height*17/20-factor*50, factor*50, factor*50, button1);
        		
            	blB=new Entity (factor*50, height*18/20-factor*50, factor*50, factor *50, button2);
            	brB=new Entity (factor*50, height*19/20-factor*50, factor*50, factor *50, button3);
            	
            	Entity[] buttons = {tlB, trB, blB, brB};

            	bufferG.setColor(Color.BLACK);
            	bufferG.drawString(answers[0], factor*100, height*16/20-factor*50);
            	bufferG.drawString(answers[1], factor*100, height*17/20-factor*50);
            	bufferG.drawString(answers[2], factor*100, height*18/20-factor*50);
            	bufferG.drawString(answers[3], factor*100, height*19/20-factor*50);
            	//bufferG.drawString(answers[0], factor*100, height*16/20-factor*50);
            	//bufferG.drawString(answers[1], width*19/30, height*16/20-factor*50);
            	//bufferG.drawString(answers[2], factor*100, height*18/20-factor*50);
            	//bufferG.drawString(answers[3], width*19/30, height*18/20-factor*50);

            	bufferG.setColor(Color.MAGENTA);

            	for (Entity button: buttons) {
                	//bufferG.fillRect((int)(button.x-button.width/2), (int)(button.y-button.height/2), button.width, button.height);
                	bufferG.drawImage(button.getPic(), (int)(button.x-button.width/2), (int)(button.y-button.height/2), button.width, button.height, this);
            	}
            	
        		askedQ=false;
        	}
        	
        }
        
        //drawings for if player is moving around
        else {
        	//for everything, if it has an image, draw the image, otherwise draw a colored box
        	for(Entity en: things) {
        		boolean hasPic=false;
            	Point relP=relativeToP(p1, (int) en.x, (int) en.y);
            	
        		if(en.getClass().equals(Enemy.class)) {
        			Enemy eni = (Enemy) en;
        			if(eni.pic != null) {
        				hasPic=true;
        				bufferG.drawImage(eni.pic, relP.x*factor -en.width/2 +width/2, relP.y* factor -en.height/2 +height/2, en.width*factor, en.height*factor, this);
        			}
        			bufferG.setColor(Color.YELLOW);
        			
        		}
        		else if(en.getClass().equals(Lootbox.class)) {
        			Lootbox box = (Lootbox) en;
        			if(box.pic != null) {
        				hasPic=true;
        				bufferG.drawImage(box.pic, relP.x*factor -en.width/2 +width/2, relP.y* factor -en.height/2 +height/2, en.width*factor, en.height*factor, this);
        			}
        			bufferG.setColor(Color.GREEN);
        		}
        		else if(en.getClass().equals(CreditBox.class)) {
        			bufferG.setColor(Color.BLUE);
        		}
        		else {
        			bufferG.setColor(new Color(249, 192, 216));
        		}
        		
        		if(!hasPic) {
                	bufferG.fillRect(relP.x*factor -en.width/2 +width/2, relP.y* factor -en.height/2 +height/2, en.width*factor, en.height*factor);

        		}
            }
            
        	
        	//if there is a message to draw, draw it in an orange box
        	if(displayingMessage) {
        		displayingMessage=false;
        	}
            if(textBox!=null) {
            	bufferG.setColor(new Color(255, 201, 33));
            	bufferG.fillRect(0, height*11/16, width, height/2);
            	
            	
            	bufferG.setColor(Color.BLACK);
            	bufferG.drawString(textBox, 20, height*6/8);
            	
            	displayingMessage=true;
            	
            	textBox=null;
            }
        	
            bufferG.rotate(m.angle+Math.PI/2, width/2, height/2);
            bufferG.setColor(Color.BLUE);
            //bufferG.fillRect(width/2-pWidth/2, height/2-pHeight/2, pWidth, pHeight); //for player
        	bufferG.drawImage(p1.getPic(), width/2-pWidth/2, height/2-pHeight/2, pWidth, pHeight, this);
            bufferG.rotate(-(m.angle+Math.PI/2), width/2, height/2);
        }
        
        //display the current level and item equipped
        bufferG.setColor(Color.BLACK);
        bufferG.drawString("Current Level: " + level , width*9/10, 20);
        if(p1.equipped!=null) {
        	bufferG.drawString("Equipped Item: " + p1.equipped.name, width/4, height/9);

        }
        
        g.drawImage(buffer, 0, 0, this); //place the buffer image onto the actual
	}
	public void displayText(String text) {
		this.text=text;
	}
	public void displayTextBox(String text) {
		textBox=text;
	}

}
