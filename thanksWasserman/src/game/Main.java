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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		frame = new JFrame("game name");
		p1= new Player(10, 100, 100);
		things= new ArrayList<>();
		mice= new MouseInputs(frame, p1);
		
		things.add(new Entity(0,0, 20, 20));
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
		
		//background.start();
		
		while(p1.hp>0) {
			
			if(battling) {
				if(p1.hp>0 && currentEn.hp>0) {
					if(enemyMove) {
						Moves enemySpell = currentEn.moveset[(int) (Math.random()*currentEn.moveset.length)];
						behind.displayText(currentEn.name + " used " + enemySpell.name + ". It did " + enemySpell.damage + " damage");
						p1.hp-=enemySpell.damage;
					}
					else {
						
					}
					
				}
				else {
					currentEn=null;
					battling=false;
				}
			}
			
			else {
				int veloX=p1.updateX(keyboard);
				int veloY=p1.updateY(keyboard);
				
				for(Entity en: things) {
					if(mice.clicked) {
						if(distance(p1.x, p1.y, en.x, en.y)<radius && contains(mice.click.x, mice.click.y, en.x, en.y, en.width, en.height)) {
							if(en.getClass().equals(Enemy.class)) {
								System.out.println("sheeesh");
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
				
				
				if(mice.clicked) {
					for(Entity en: things) {
						if(distance(p1.x, p1.y, en.x, en.y)<radius && contains(mice.click.x, mice.click.y, en.x, en.y, en.width, en.height)) {
							if(en.getClass().equals(Enemy.class)) {
								currentEn= (Enemy) en;
							}
						}
						
					}
				}
			}
			
			
			behind.repaint();
			
			
			try {
				Thread.sleep(10);
				
			}
			catch(InterruptedException e) {}
		}
		
	}

}
