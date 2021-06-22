package game;

public class EquippableItem implements Item {
	/*Class that just stores item properties of equippables
	 * 
	 */
	int atk;
	int def;
	int hp;
	public final String name;
	
	public EquippableItem(String name, int atk, int def, int hp) {
		this.name = name;
		this.atk = atk;
		this.def = def;
		this.hp = hp;
	}
	
	public EquippableItem(String arg) {
		// USING list of items defined somewhere, assigned to array arr;
		
		// doing this the simple way to make it easy to modify and understand later
		for (Item a : arr) {
			if (a.getClass().equals(EquippableItem.class) && ((EquippableItem)a).name.equals(arg)) {
				int[] r = a.get();
				this.atk = r[0];
				this.def = r[1];
				this.hp = r[2];
				this.name = ((EquippableItem)a).name;
				return;
			}
		}
		//something must have gone wrong lmao if this shows up
		this.atk = 0;
		this.def = 0;
		this.hp = 0;
		this.name = "Untitled Project - NetBeans 13.0.1";
		
	}

	@Override
	public void use(Player p) {
		p.equipItem(this);
	}
	
	@Override
	public int[] get() {
		return(new int[] {this.atk, this.def, this.hp});
	}

}
