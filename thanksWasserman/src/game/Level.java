package game;

import java.util.List;

public class Level {
	/*Class that just loads the environment in
	 * If you want to make different levels possible, edit the load method and put if statements based on what level is put in cunstructor
	 */
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

		things.add(new Lootbox(500, 500, Item.arr));
		
		things.add(new CreditBox( 100, 1000));
		
		things.add(new Enemy(level*5, 575, 900));
		
		p1.reset();
	}
	

}
