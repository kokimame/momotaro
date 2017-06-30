
public class Item {

	private String name;
	private char token;
	/* The point an item holds (ex. the value for a Gold)  */
	private int point;
	/* Item type: 'A': Weapon, 'D': Sheild, 'G': Gold, 'H': Health */
	private char type;

	/* Position of dropped item on the map  */
	private int x;
	private int y;

	public Item(String name, char token, int point, char type) {
		this.name = name;
		this.token = token;
		this.point = point;
		this.type = type;
	}

	// For dropped item
	public Item(String name, char token, int point, char type, int x, int y) {
		this.name = name;
		this.token = token;
		this.point = point;
		this.type = type;
		this.x = x;
		this.y = y;
	}

	public String get_name(){
		return this.name;
	}

	public char get_token() {
		return this.token;
	}

	public int[] get_position() {
		int[] pos = new int[2];
		pos[0] = this.x;
		pos[1] = this.y;
		return pos;
	}
}
