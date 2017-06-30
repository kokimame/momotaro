
public class Item {

	private String name;
	private char token;
	private int ap;		 // Attack point
	private int hp;		 // Health point

	public Item(String name, int ap, int hp) {
		this.name = name;
		this.ap = ap;
		this.hp = hp;
	}

	public Item(String name, char token, int ap, int hp) {
		this.name = name;
		this.token = token;
		this.ap = ap;
		this.hp = hp;
	}

	public String get_name(){
		return this.name;
	}
}
