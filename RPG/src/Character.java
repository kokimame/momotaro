
public class Character {

	private int hp;
	private int x;
	private int y;
	private int attack;


	public Character(int hp, int x, int y, int ap) {
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


	int get_hp() {
		return this.hp;
	}

	public int[] get_position() {
		int[] pos = new int[2];

		pos[0] = x;
		pos[1] = y;

		return pos;
	}

}
