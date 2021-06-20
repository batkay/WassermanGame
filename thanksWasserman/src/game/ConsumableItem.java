package game;

public class ConsumableItem implements Item {
	
	int hpEffect;
	String message;
	final String name;
	
	public ConsumableItem(String name, int hpE, String mess) {
		this.hpEffect = hpE;
		this.message = mess;
		this.name = name;
	}
	
	public ConsumableItem(String arg) {
		// USING list of items defined somewhere, assigned to array arr;
		Item [] arr;
		// doing this the simple way to make it easy to modify and understand later
		for (Item a : arr) {
			if (a.name == arg) {
				int[] r = a.get();
				this.hpEffect = r[0];
				this.name = a.name;
				this.message = a.getMessage();
				return;
			}
		}
		this.hpEffect = 0;
		this.message = "Placeheld.";
		this.name = "Pineapple";
		
	}

	@Override
	public void use(Player p) {
		p.updateHP(hpEffect);
		// TODO implement consumable text output (console? custom text renderer?)
	}
	
	@Override
	public int[] get() {
		return(new int[] {this.hpEffect});
	}
	
	public String getMessage() {
		return this.message;
	}

}
