package game;

public class Enemy extends Entity{
	int hp;
	public Enemy(int hp, double x, double y) {
		super(x, y, 50, 50);
		this.hp=hp;
	}

}
