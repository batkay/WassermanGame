package game;

public interface Item {
	
	final Item [] arr = {
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
	public void use(Player p);
	public int[] get();
}
