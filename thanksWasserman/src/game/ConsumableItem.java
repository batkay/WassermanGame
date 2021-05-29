package game;

public class ConsumableItem implements Item {
	
	int hpEffect;
	String message;
	
	public ConsumableItem(int hpE, String mess) {
		this.hpEffect = hpE;
		this.message = mess;
	}

	@Override
	public void use(Player p) {
		p.updateHP(hpEffect);
		// TODO implement consumable text output (console? custom text renderer?)
	}

}
