package game;

public class EquippableItem implements Item {
	
	int atk;
	int def;
	int hp;
	
	public EquippableItem(int atk, int def, int hp) {
		this.atk = atk;
		this.def = def;
		this.hp = hp;
	}

	@Override
	public void use(Player p) {
		p.equipItem(this);
		// TODO add equip text output
	}

}
