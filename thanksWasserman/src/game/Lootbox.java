package game;

import java.util.List;

public class Lootbox extends Entity{
	Item[] items;
	public Lootbox(int x, int y, Item[] allItems) {
		super(x, y, 50, 50);
		items=allItems;
	}
	
	public EquippableItem roll() {
		int rolled = (int)(Math.random()*items.length);
		return (EquippableItem) items[rolled];
	}
}
