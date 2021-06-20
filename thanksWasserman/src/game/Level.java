package game;

import java.util.List;

public class Level {
	Player p1;
	List<Entity> things;
	int level;
	
	public Level(int level, List<Entity> things, Player p1) {
		this.level=level;
		this.p1=p1;
		this.things=things;
	}
	
	public void load() {
		things.clear();
		things.add(new Obstruction(25, 600, 50, 1200));
		things.add(new Obstruction(1125, 600, 50, 1200));
		things.add(new Obstruction(275, 900, 500, 50));
		things.add(new Obstruction(875, 900, 500, 50));
		things.add(new Obstruction(575, 1225, 1100, 50));

		things.add(new Enemy(level*5, 575, 900));
		
		p1.reset();
	}
	

}
