
public class Item {

	private String name;
	private int ap;		 // Attack point
	private int hp;		 // Health point

	public Item(String name, int ap, int hp) {
		this.name = name;
	}

	public String get_name(){
		return this.name;
	}
}
