package game;

import java.awt.Image;
import java.io.File;

public class Entity {
	double x;
	double y;
	int width;
	int height;
	
	Image pic;
	
	public Entity(double x, double y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	
	public Entity(double x, double y, int width, int height, Image pic) {
		this(x,y,width,height);
		this.pic=pic;
	}

	
	public void setPic(Image picture) {
		this.pic=picture;
	}
	public Image getPic() {
		return pic;
	}
}
