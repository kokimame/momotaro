import java.util.ArrayList;

public class Character {

	private String name;
	private char token;
	private int hp;
	private int x;
	private int y;
	private int attack;


	public Character(String name, char token, int hp, int x, int y, int ap) {
		this.name = name;
		this.token = token;
		this.hp = hp;
		this.x = x;
		this.y = y;
		this.attack = ap; // Attack point
	}

	public void action(int key){
		switch(key){
		case 8:
			go_up();
			break;
		case 6:
			go_right();
			break;
		case 2:
			go_down();
			break;
		case 4:
			go_left();
			break;
		case 5:
			break;
		default:
			System.out.println("key error");
			break;
		}
	}

	public boolean collide(int key, ArrayList<int []> c_pos) {
		int[] new_pos = new int[2];
		int i;
		switch(key){
		case 8:
			new_pos[0] = this.x;
			new_pos[1] = this.y - 1;
			break;
		case 6:
			new_pos[0] = this.x + 1;
			new_pos[1] = this.y;
			break;
		case 2:
			new_pos[0] = this.x;
			new_pos[1] = this.y + 1;
			break;
		case 4:
			new_pos[0] = this.x - 1;
			new_pos[1] = this.y;
			break;
		case 5:
			return true;
		default:
			return false;
		}
		return contains_like(c_pos, new_pos);
	}

	// This partly substitutes ArrayList.contains for now
	// when the original doesn't work well.
	public boolean contains_like(ArrayList<int []> pos, int[] b) {
		int i;

		for(i = 0; i < pos.size(); i++){
			if(pos.get(i)[0] == b[0] && pos.get(i)[1] == b[1]){
				return true;
			}
		}
		return false;
	}

	public void go_up() {
		this.y--;
	}
	public void go_right() {
		this.x++;
	}
	public void go_down() {
		this.y++;
	}
	public void go_left() {
		this.x--;
	}

	public int attack() {
		return attack;
	}

	public void get_damage(int damage) {
		this.hp -= damage;
	}


	public int get_hp() {
		return this.hp;
	}
	
	public String get_name() {
		return this.name;
	}

	public char get_token() {
		return this.token;
	}

	public int[] get_position() {
		int[] pos = new int[2];

		pos[0] = x;
		pos[1] = y;

		return pos;
	}

}
