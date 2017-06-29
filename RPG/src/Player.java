import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Player extends Character{

	int x, y;
	private ArrayList<Item> inventory = new ArrayList<Item>();

	public Player(String name, int hp, int x, int y, int ap){
		super(name, hp, x, y, ap);
		this.x = x;
		this.y = y;

		add_item(new Item("・ツルギ", 10, 0));
		add_item(new Item("・ヤクソウ", 0, 10));

	}

	@Override
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
		case 5: // Stay
			break;
		case 0:
			show_inventory();
			break;
		default:
			System.out.println("key error");
			break;
		}
	}


	public void update() {
		action(read_key());
	}

	private void add_item(Item item) {
		this.inventory.add(item);
	}

	public void show_inventory() {
		int i;
		System.out.println("・・・ショジヒン・・・");
		for(i = 0; i < this.inventory.size(); i++) {
			System.out.println(this.inventory.get(i).get_name());
		}
	}


	static int read_key() {
		String str = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			str = br.readLine();
		}
		catch(IOException e) {
			System.out.println("IOError");
		}
		int key = Integer.parseInt(str);

		if(key == 8 || key == 6 || key == 2 || key == 4 || key == 5 || key == 0)
			return key;
		else
			return -1;

	}

}
