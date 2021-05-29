package game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends Entity{
	double hp;
	
	
	double moveSpeed=10;
	
	ArrayList items;
	
	public Player(double hp, double xPos, double yPos) {
		super(xPos, yPos, 50, 50);
		items=new ArrayList<>();
		this.hp=hp;
		
	}
	
	public Player(Player p1) {
		// TODO Auto-generated constructor stub
		
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
}
