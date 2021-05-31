package game;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import static game.Utilities.*;

public class Main {

	static JFrame frame;
	static final Dimension size= new Dimension (1280, 720);
	
	static Player p1;
	
	static ArrayList<Entity> things;
	
	static MouseInputs mice;
	
	static double radius=200;
	
	static boolean battling=false;
	
	static Enemy currentEn=null;
	
	static boolean enemyMove=false;
	
	static enum CurrentMove {
		enemyMove, playerMove, displayingText
	}
	static CurrentMove move=CurrentMove.playerMove;
	
	static boolean held=false;
	
	static String[][] questions = new String[5][5];
	static int q=0;
		
	public static void main(String[] args) {
		questions[0][0] = "What stores a whole number value?";
		questions[0][1] = "Integer";
		questions[0][2] = "Boolean";
		questions[0][3] = "String";
		questions[0][4] = "Double";
		
		frame = new JFrame("game name");
		p1= new Player(10, 100, 100);
		things= new ArrayList<>();
		mice= new MouseInputs(frame, p1);
		
		things.add(new Obstruction(0,0, 20, 20));
		things.add(new Enemy(10, 200, 200));
		
		Background behind = new Background(size, frame, p1, things, mice);
		Thread background = new Thread (behind);
		
		Inputs keyboard= new Inputs();
		
		behind.addKeyListener(keyboard);
		behind.addMouseListener(mice);
		behind.addMouseMotionListener(mice);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.add(behind);
		frame.setSize(size);
		frame.setVisible(true);
		
		background.start();
		
		while(p1.hp>0) {
			
			if(battling) {
				if(p1.hp>0 && currentEn.hp>0) {
					if(enemyMove) {
						Moves enemySpell = currentEn.moveset[(int) (Math.random()*currentEn.moveset.length)];
						behind.displayText(currentEn.name + " used " + enemySpell.name + ". It did " + enemySpell.damage + " damage");
						p1.hp-=enemySpell.damage;
					}
					else {
						//behind.displayText("sheesh");
						
						Moves playerSpell= null;
						
						behind.playerMove(true);
						
						behind.displayText("What will you do?");
						
						behind.repaint();
						//int initialC=mice.clicks;
						while(playerSpell==null) {
							
							if(mice.clicked && contains(mice.click, behind.getA())) {
								playerSpell=new Moves(1, "attack");

							}
							else if(mice.clicked && contains(mice.click, behind.getI())) {
								playerSpell=new Moves(1, "Item");

							}
							
							try {
								Thread.sleep(10);
							}
							catch(InterruptedException e) {}
						}
						while(mice.clicked) {
							try {
								Thread.sleep(10);
							}
							catch(InterruptedException e) {}
						}
						behind.playerMove(false);
						
						
						// put question logic here
						behind.displayText(questions[q][0]);
						int spot = (int)(Math.random()* 4); //what question spot the answer will be
						String[] answerOrder= new String[4]; //the order the anwers will appear in
						answerOrder[spot] = questions[q][1];
						int j=2;
						for(int i=0; i<questions.length; i++) {
							if(answerOrder[i]!=null) {
								continue;
							}
							
							answerOrder[i]= questions[q][j];
							j++;
						}
						//answerOrder should have the answres in a random order now
						
						
						
						
						behind.displayText("You used " + playerSpell.name + ". It did " + playerSpell.damage + " damage");
						currentEn.hp-=playerSpell.damage;

						
					}
					
					enemyMove=!enemyMove;
					
					
				}
				else {
					currentEn=null;
					battling=false;
					
					behind.battling=battling;
					behind.currentEn=currentEn;
				}
			}
			
			else {
				int veloX=p1.updateX(keyboard);
				int veloY=p1.updateY(keyboard);
				
				for(Entity en: things) {
					if(mice.clicked) {
						if(distance(p1.x, p1.y, en.x, en.y)<radius && contains(mice.globalClick.x, mice.globalClick.y, en.x, en.y, en.width, en.height)) {
							if(en.getClass().equals(Enemy.class)) {
								//System.out.println("sheeesh");
								
								p1.hp=p1.maxHp;
								
								currentEn= (Enemy) en;
								battling=true;
								
								behind.battling=battling;
								behind.currentEn=currentEn;
							}
						}
					}
					Player p2= new Player(p1);

					p2.x+=veloX;
					if(touchesX(p2, en) && touches(p2, en)) {
						veloX=0;
					}				
					
					p2= new Player(p1);

					p2.y+=veloY;
					
					if(touchesY(p2, en) && touches(p2, en)) {
						veloY=0;
					}
					
				}
				p1.x+=veloX;
				p1.y+=veloY;
				
				/*
				if(mice.clicked) {
					for(Entity en: things) {
						if(distance(p1.x, p1.y, en.x, en.y)<radius && contains(mice.click.x, mice.click.y, en.x, en.y, en.width, en.height)) {
							if(en.getClass().equals(Enemy.class)) {
								currentEn= (Enemy) en;
							}
						}
						
					}
				}
				*/
			}
			
			behind.repaint();
			
			//stuck here
			/*
			if(battling) {
				int initial=mice.clicks;
				while(mice.clicks==initial) {
					//held=true;
					
					try {
						Thread.sleep(10);
					}
					catch(InterruptedException e) {}
				}
			}
			*/
			
			if(battling) {
				while(!mice.clicked) {
					try {
						Thread.sleep(10);
					}
					catch(InterruptedException e) {}
				}
				held=true;
				while(held) {
					if(!mice.clicked) {
						held=false;
					}
					try {
						Thread.sleep(10);
					}
					catch(InterruptedException e) {}
				}
			}
			
			/*
			if(mice.clicked) {
				held=true;
			}
			*/
			
			
			
			//System.out.println("3");

			behind.displayText(null);
			
			try {
				Thread.sleep(10);
				
			}
			catch(InterruptedException e) {}
		}
		
	}

}
