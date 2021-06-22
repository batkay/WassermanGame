package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	/* Welcome to our very poorly written spaghetti code.
	 * Thanks again Mr. Wasserman for everything
	 */
	
	//declaring variables + frames + data input streams to be used later
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
	
	static Font script= new Font("Roboto", Font.PLAIN, (int) (size.getHeight()/50));
	
	public static void main(String[] args){
		//get the player's save file and find what level they are on
		File save = new File("src/extras/save.txt");
		try {
			if(!save.createNewFile()) {
				Scanner scan = new Scanner(save);
				if(scan.hasNext()) {
					currentLevel=scan.nextInt();
				}
				else {
					FileWriter writer= new FileWriter(save, false);
					writer.write(currentLevel+"");
					writer.close();
				}
				scan.close();
				
			}
			else {
				FileWriter writer= new FileWriter(save, false);
				writer.write(currentLevel+"");
				writer.close();
				
			}
		} catch (IOException e3) {
			e3.printStackTrace();
		}
		
		
		//grab the list of questions from our csv doc
		try {
			questionFile= "src/extras/questions.csv";
			file= new FileInputStream (questionFile);

		}
		catch (FileNotFoundException e){
			System.out.println("oof");
		}
				
		theInputs = new BufferedReader(new InputStreamReader(file));
		
		//question, answers are separated by commas so split the string by commas
		//can only load string questions, questions stored in csv doc. kinda an oversight on my end ngl
		String line;
		try {
			line = theInputs.readLine();
			while(line!=null) {
				String[] words= line.split(",");
				//csv files display "" differently so do some string manipulation to fix this 
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
						
					}
				}
				
				//add words to the list
				questions.add(words);
				line=theInputs.readLine();
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		frame = new JFrame("Wasserman and the 7 Textbooks");
				
		try {
			p1= new Player(10, 575, 1150);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		//like before, get player items from file
		File items = new File("src/extras/items.txt");
		try {
			if(!items.createNewFile()) {
				Scanner scan = new Scanner(items);
				while(scan.hasNext()) {
					p1.items.add(new EquippableItem(scan.nextLine()));
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
		
		//make a new level and load all the objects placed in the level
		Level firstLevel= new Level(currentLevel, things, p1);

		mice= new MouseInputs(frame, p1);
		
		firstLevel.load();
		
		//setup the drawing object
		Background behind = new Background(size, frame, p1, things, mice, script);
		behind.setLevel(currentLevel);
		
		Inputs keyboard= new Inputs();
		
		behind.addKeyListener(keyboard);
		behind.addMouseListener(mice);
		behind.addMouseMotionListener(mice);
		
		behind.setLevel(currentLevel);

		//put list of items into a drop down box, display box
		String[] itemList = new String[p1.items.size()];
		for(int i=0; i<itemList.length; i++) {
			itemList[i] = ((EquippableItem) p1.items.get(i)).name;
		}
		backpack = new JComboBox(itemList);
		dropdownListener = new ProgramBox(p1);
		backpack.addActionListener(dropdownListener);
		backpack.setBounds(0, 0, frame.getWidth()/16, frame.getHeight()/16);
		backpack.setFocusable(false);
		
		frame.add(backpack);

		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(behind);
		
		frame.setSize(size);
		frame.setVisible(true);
				
		
		behind.requestFocus();
		
		
		//and the game starts!
		while(p1.hp>0) {
			//battling phase, hide dropdown
			if(battling) {
				backpack.setVisible(false);
				if(p1.hp>0 && currentEn.hp>0) {
					
					//moves r split into enemy and player moves
					if(enemyMove) {
						//enemy chooses one of their moves at random and it does dmg based on complex math formula including player defense stat
						Moves enemySpell = currentEn.moveset[(int) (Math.random()*currentEn.moveset.length)];
						
						double damageTaken = round1000( enemySpell.damage * (1 - ((Math.log(p1.equipped.def+1)*1)/6)) );
						behind.displayText(currentEn.name + " used " + enemySpell.name + ". It did " + damageTaken + " damage");
						p1.hp-=damageTaken;
					}
					else {
						//setup the player's move
						Moves playerSpell= null;
						
						behind.playerMove(true);
						
						behind.displayText("What will you do?");
						
						behind.repaint();
						while(playerSpell==null) {
							//wait for player to choose to attack or use item
							if(mice.clicked && contains(mice.click, behind.getA())) {
								int bonusA=0;
								if(p1.equipped!=null) {
									bonusA=p1.equipped.atk;
								}
								playerSpell=new Moves((int)( 5+bonusA * Math.random()), "Textbook Slap");

							}
							else if(mice.clicked && contains(mice.click, behind.getI())) {
								int bonusA=0;
								if(p1.equipped!=null) {
									bonusA=p1.equipped.atk;
								}
								playerSpell=new Moves(-Math.round(bonusA/2), "Item");

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
						if(playerSpell.name.equals("Textbook Slap")) {
							//randomize where the question's answer goes
							int q=(int) (Math.random()*questions.size());
							behind.displayText(questions.get(q)[0]);
							
							int spot = (int)(Math.random()* 4); //what question spot the answer will be
							
							
							String[] answerOrder= new String[4]; //the order the anwers will appear in
							answerOrder[spot] = questions.get(q)[1];
							
							//fill in the rest of the questions into a spot
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
							
							//wait for them to answer the question
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
							
							//check if the answer is right, if not change dmg they do to 0
							if(answer==spot) {
								
							}
							else {
								playerSpell= new Moves(0, "Wrong Answer");
							}
							
						}
						
						//display dmg
						if(playerSpell.damage>0) {
							behind.displayText("You used " + playerSpell.name + ". It did " + playerSpell.damage + " damage");

						}
						else {
							behind.displayText("You used " + playerSpell.name + ". It healed " + playerSpell.damage + " damage");

						}
						
						currentEn.hp-=playerSpell.damage;

						
					}
					
					//change whose move it is
					enemyMove=!enemyMove;
					
					
				}
				else {
					currentEn=null;
					battling=false;
					
					behind.battling=battling;
					behind.currentEn=currentEn;
				}
			}
			//for movement part
			else {
				backpack.setVisible(true);
				
				int veloX=p1.updateX(keyboard);
				int veloY=p1.updateY(keyboard);
				
				//check to see if player clicked on anything
				for(int i=things.size()-1; i>=0; i--) {
					Entity en= things.get(i);
					if(mice.clicked) {
						if(distance(p1.x, p1.y, en.x, en.y)<radius && contains(mice.globalClick.x, mice.globalClick.y, en.x, en.y, en.width, en.height)) {
							
							//if clicked on enemy, engage battle
							if(en.getClass().equals(Enemy.class)) {
								
								p1.hp=p1.maxHp;
								
								currentEn= (Enemy) en;
								battling=true;
								
								behind.battling=battling;
								behind.currentEn=currentEn;
							}
							
							
							//lootboxes, give player random item and add it to the save file too
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
							//if clicked on blue box, display game credits
							else if(en.getClass().equals(CreditBox.class)){
								behind.displayTextBox("Credits: Thank you Wasserman!! From LHS CO-2021 Lead Graphic Designer: Ally Mintz, Lead Item Developer: Divy Jain, Question Developer: Ethan Dcosta, Lead Engine Developer: Thomas Lang");
								//System.out.println("Credits \nThank you Wasserman, From LHS CO-2021 \nLead Graphic Designer: Ally Mintz \nLead Item Developer: Divy Jain \nQuestion Developer: Ethan Dcosta \nLead Engine Developer: Thomas Lang");
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
			
			//make sure ui is scaling to the size of the frame
			script= new Font("Roboto", Font.PLAIN, (int) (frame.getHeight()/50));
			behind.giveFont(script);
			
			backpack.setBounds(frame.getWidth()/32, frame.getHeight()/32, frame.getWidth()/8, frame.getHeight()/8);
			backpack.setFont(script);
			
			//refresh graphics
			behind.repaint();

			//if player has no item equipped but items in their inventory, equip the first one
			if(p1.items.size()>0 && p1.equipped==null) {
				p1.equipItem(p1.items.get(0));
			}
			
			//wait until the player clicks for text boxes
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
			
			try {
				Thread.sleep(10);
				
			}
			catch(InterruptedException e) {}
		}
		
	}
	

}
