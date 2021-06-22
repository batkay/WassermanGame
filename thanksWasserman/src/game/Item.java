package game;

public interface Item {
	
	//framework for all items, plus has all the textbooks u can get. Java textbook most broken ofc
	final Item [] arr = {
			new EquippableItem("Give Me Liberty", 10, 10, 10),
			new EquippableItem("Fundamentals of Physics", 7, 3, 8),
			new EquippableItem("College Physics", 4, 3, 7),
			new EquippableItem("Java Concepts Early Objects", 20, 20, 20),
			new EquippableItem("The Americans", 1, 1, 1),
			new EquippableItem("The Practice of Statistics", 2, 4, 6),
			new EquippableItem("Calculus Early Transcendentals", 5, 3, 8),
			new EquippableItem("Campbell Biology", 4, 4, 7),
			new EquippableItem("Zumdahl Chemestry", 9, 5, 7),
			new EquippableItem("Discovering French 3", 2, 1, 5),
			new EquippableItem("Precalc with Limits", 4, 7, 5),
			new EquippableItem("Multivariable Calculus Early Transcendentals", 6, 4, 13),
			new EquippableItem("Myers Psychology", 6, 2, 8),
			new EquippableItem("Music In Theory and Practice", 3, 8, 12),
			new EquippableItem("McDougal Littell Geometry", 7, 6, 8)
			
	};
	public void use(Player p);
	public int[] get();
}
