package game;

import java.util.ArrayList;

public class Enemy extends Entity{
	int hp;
	Moves[] moveset;
	
	String name;
	public Enemy(int hp, double x, double y) {
		super(x, y, 50, 50);
		this.hp=hp;
		
		if(hp>100) {
			name="CollegeBoard";
			
			moveset= new Moves[3];
			moveset[0] = new Moves(20, "Non-profit");
			moveset[1] = new Moves(30, "Grandfather picks up quartz and valuable onyx jewels");
			moveset[3] = new Moves(25, "CollegeBoard is watching");
		}
		else {
			name = "";
		}
		
	}
	
}
