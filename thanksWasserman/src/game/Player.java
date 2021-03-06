package game;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Player extends Entity{
	/*
	 * Player class, stores some info and can equip items
	 */
	double hp;
	double maxHp;
	double baseHp;
	double moveSpeed=10;
		
	EquippableItem equipped;
	
	ArrayList<EquippableItem> items;
	
	double originalX;
	double originalY;
	
	public Player(double hp, double xPos, double yPos) throws IOException {
		super(xPos, yPos, 50, 50, ImageIO.read(new File("src/extras/jimmy.png")));
		items=new ArrayList<EquippableItem>();
		this.maxHp=hp;
		baseHp=maxHp;
		this.hp=maxHp;
		
		originalX=xPos;
		originalY=yPos;
	}
	
	public Player(Player p1) {
		super(p1.x, p1.y, 50, 50);
		items = new ArrayList<>();
		this.hp=p1.hp;
	}

	public int updateX(Inputs k) {
		int velocity=0;
		if(k.keys[KeyEvent.VK_A]) {
			velocity-=moveSpeed;
		}
		
		if(k.keys[KeyEvent.VK_D]) {
			velocity+=moveSpeed;
		}
		//x+=velocity;
		return velocity;
		
	}
	public int updateY(Inputs k) {
		int velocity =0;
		if(k.keys[KeyEvent.VK_W]) {
			velocity-=moveSpeed;
			

		}
		if(k.keys[KeyEvent.VK_S]) {
			velocity+=moveSpeed;
			

		}
		//y+=velocity;
		return velocity;

	}
	
	public void updateHP(int hp) {
		this.hp += hp;
	}
	public void equipItem(EquippableItem e) {
		if (this.equipped == null) {
			this.items.remove(e);
			this.equipped = e;
			return;
		}
		this.items.add(this.equipped);
		this.equipped = e;
		
		double difference = maxHp-hp;
		
		maxHp=baseHp;
		maxHp+=e.hp;
		hp=maxHp-difference;
		if(hp<1) {
			hp=1;
		}
	}
	
	public void reset() {
		this.x=originalX;
		this.y=originalY;
		this.hp=maxHp;
	}
	
}
