package game;

public abstract class Entity {
	double x;
	double y;
	int width;
	int height;
	
	public Entity(double x, double y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
}
