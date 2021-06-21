package game;

public class EquippableItem implements Item {
	
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
		Item [] arr = {
				new EquippableItem("Give Me Liberty", 10, 10, 10),
				new EquippableItem("Fundamentals of Physics", 7, 3, 4),
				new EquippableItem("College Physics", 4, 6, 7),
				new EquippableItem("Java Concepts Early Objects", 20, 20, 20),
				new EquippableItem("The Americans", 1, 1, 1),
				new EquippableItem("The Practice of Statistics", 2, 4, 1),
				new EquippableItem("Calculus Early Transcendentals", 8, 9, 5),
				new EquippableItem("Campbell Biology", 4, 8, 9),
				new EquippableItem("Chemestry", 9, 7, 10),
				new EquippableItem("Discovering French 3", 2, 1, 1),
				new EquippableItem("Precalc with Limits", 8, 10, 9)
				
				
		};
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
		this.atk = 0;
		this.def = 0;
		this.hp = 0;
		this.name = "Untitled Project - NetBeans 13.0.1";
		
	}

	@Override
	public void use(Player p) {
		p.equipItem(this);
		// TODO add equip text output
	}
	
	@Override
	public int[] get() {
		return(new int[] {this.atk, this.def, this.hp});
	}

}
