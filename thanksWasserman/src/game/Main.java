package game;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JComboBox;
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
	
	//static String[][] questions = new String[2][5];
	
	static ArrayList<String[]> questions= new ArrayList<>();
	//static int q=0;
	static String questionFile;
	static FileInputStream file;
	static BufferedReader theInputs; 
	static DataInputStream d;
	
	static int currentLevel=1;
	
	static JComboBox backpack;
	static ProgramBox dropdownListener;
	
	public static void main(String[] args){
		File save = new File("src/extras/save.txt");
		try {
			if(!save.createNewFile()) {
				Scanner scan = new Scanner(save);
				currentLevel=scan.nextInt();
				scan.close();
				
			}
			else {
				FileWriter writer= new FileWriter(save, false);
				writer.write(currentLevel+"");
				writer.close();
				
			}
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		
		
		
		try {
			questionFile= "src/extras/questions.csv";
			file= new FileInputStream (questionFile);

		}
		catch (FileNotFoundException e){
			System.out.println("oof");
		}
		
		//d= new DataInputStream(file);
		
		theInputs = new BufferedReader(new InputStreamReader(file));
		
		
		String line;
		try {
			line = theInputs.readLine();
			while(line!=null) {
				String[] words= line.split(",");
				for(int i=0; i<words.length; i++) {
					if(words[i].contains("\"")) {
						words[i]=(String) words[i].subSequence(1, words[i].length()-1);
						ArrayList<String> partStrings= new ArrayList<>();
						
						while(words[i].contains("\"\"")) {
							partStrings.add(words[i].substring(0, words[i].indexOf("\"\"")));
							partStrings.add("\"");
							words[i]=words[i].substring(words[i].indexOf("\"\"")+2);
						}
						String reconstructed="";
						for(int j=0; j<partStrings.size(); j++) {
							reconstructed=reconstructed+partStrings.get(j);
						}
						reconstructed=reconstructed+words[i];
						
						words[i]=reconstructed;
						/*
						while(words[i].contains("\"\"")) {
							words[i]=words[i].substring(0, words[i].indexOf("\"\""))+"\"" +words[i].substring(words[i].indexOf("\"\"")+2);
						}
						*/
					}
				}
				questions.add(words);
				//questions.add(line.split(","));
				
				line=theInputs.readLine();
			}
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		frame = new JFrame("Wasserman and the 7 Textbooks");
				
		try {
			p1= new Player(10, 575, 1150);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//get items from file
		File items = new File("src/extras/items.txt");
		try {
			if(!items.createNewFile()) {
				Scanner scan = new Scanner(items);
				while(scan.hasNext()) {
					//String daItem = scan.nextLine();
					p1.items.add(new EquippableItem(scan.nextLine()));
					//System.out.println(daItem);
				}
				scan.close();
				
			}
			else {
				
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		things= new ArrayList<>();
		
		Level firstLevel= new Level(currentLevel, things, p1);

		mice= new MouseInputs(frame, p1);
		
		firstLevel.load();
		
		
		Background behind = new Background(size, frame, p1, things, mice);
		Thread background = new Thread (behind);
		
		behind.setLevel(currentLevel);
		
		Inputs keyboard= new Inputs();
		
		behind.addKeyListener(keyboard);
		behind.addMouseListener(mice);
		behind.addMouseMotionListener(mice);
		
		behind.setLevel(currentLevel);

		String[] itemList = new String[p1.items.size()];
		for(int i=0; i<itemList.length; i++) {
			itemList[i] = ((EquippableItem) p1.items.get(i)).name;
		}
		backpack = new JComboBox(itemList);
		dropdownListener = new ProgramBox(p1);
		backpack.addActionListener(dropdownListener);
		backpack.setBounds(frame.getWidth()/16, frame.getHeight()/16, frame.getWidth()/8, frame.getHeight()/8);
		backpack.setFocusable(false);
		
		frame.add(backpack);

		
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
								playerSpell=new Moves(5, "attack");

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
						
						
						//if they chose to answer a question
						if(playerSpell.name.equals("attack")) {
							
							int q=(int) (Math.random()*questions.size());
							// put question logic here
							behind.displayText(questions.get(q)[0]);
							
							int spot = (int)(Math.random()* 4); //what question spot the answer will be
							
							
							String[] answerOrder= new String[4]; //the order the anwers will appear in
							answerOrder[spot] = questions.get(q)[1];
							int j=2;
							for(int i=0; i<answerOrder.length; i++) {
								if(answerOrder[i]!=null) {
									continue;
								}
								
								answerOrder[i]= questions.get(q)[j];
								j++;
							}
							//answerOrder should have the answres in a random order now
							
							behind.askQ(answerOrder);
							
							behind.repaint();
							
							int answer = -1;
							while(answer==-1) {
								
								if(mice.clicked && contains(mice.click, behind.getTL() )) {
									answer=0;
								}
								else if(mice.clicked && contains(mice.click, behind.getTR())) {
									answer=1;
								}
								else if(mice.clicked && contains(mice.click, behind.getBL())) {
									answer=2;
								}
								else if(mice.clicked && contains(mice.click, behind.getBR())) {
									answer=3;
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
							
							if(answer==spot) {
								
							}
							else {
								playerSpell= new Moves(0, "Wrong");
							}
							
						}
						
						
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
				
				for(int i=things.size()-1; i>=0; i--) {
					Entity en= things.get(i);
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
							
							
							//lootboxes
							else if(en.getClass().equals(Lootbox.class)) {
								EquippableItem rolledItem= ((Lootbox)en).roll();
								if(!p1.items.contains(rolledItem)) {
									p1.items.add( rolledItem);
									
									FileWriter writer;
									try {
										writer = new FileWriter(items, true);
										writer.write(rolledItem.name+"\n");
										writer.close();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									backpack.addItem(rolledItem.name);
									
									behind.displayTextBox("You got " + rolledItem.name + "!");
									
								}
								else {
									behind.displayTextBox("You got " + rolledItem.name + "! You already have this item");
								}
								
								while(!mice.clicked) {
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
								
								
								
								things.remove(i);
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
			
			//removes dead enemies
			for(int i=things.size()-1; i>=0; i--) {
				if(things.get(i).getClass().equals(Enemy.class) && ((Enemy)things.get(i)).hp<=0) {
					things.remove(i);
				}
			}
			
			//check to see if player reaches end
			if(p1.y<10) {
				currentLevel++;
				Level nextLevel= new Level(currentLevel, things, p1);
				nextLevel.load();
				enemyMove=false;
				behind.setLevel(currentLevel);
				
				//write level to file
				FileWriter writer;
				try {
					writer = new FileWriter(save, false);
					writer.write(currentLevel+"");
					writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					Thread.sleep(500);
				}
				catch(InterruptedException e) {}
			}
			
			backpack.setBounds(frame.getWidth()/8, frame.getHeight()/8, frame.getWidth()/4, frame.getHeight()/4);
			
			behind.repaint();

			if(p1.items.size()>0 && p1.equipped==null) {
				p1.equipItem(p1.items.get(0));
			}
			
			if(battling || behind.textBox!=null) {
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
			

			
			//behind.displayTextBox(null);
			//behind.displayText(null);
			
			try {
				Thread.sleep(10);
				
			}
			catch(InterruptedException e) {}
		}
		
	}
	

}
