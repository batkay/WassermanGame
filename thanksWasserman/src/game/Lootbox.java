package game;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Lootbox extends Entity{
	//gives random item when rolled
	Item[] items;
	public Lootbox(int x, int y, Item[] allItems) {
		super(x, y, 50, 50);
		try {
			this.pic = ImageIO.read(new File("src/extras/chest.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		items=allItems;
	}
	
	public EquippableItem roll() {
		int rolled = (int)(Math.random()*items.length);
		return (EquippableItem) items[rolled];
	}
}
