import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Player extends Character{

	int gold;		// Total value of golds the player has
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private ArrayList<Animal> animals = new ArrayList<Animal>();

	public Player(String name, char token, int hp, int x, int y, int ap){
		super(name, token, hp, x, y, ap);
		this.gold = 0;

		add_item(new Item("つるぎ", '剣', 10, 'A'));
		add_item(new Item("きびだんご", 'き', 5, 'H'));
		add_item(new Item("きびだんご", 'き', 5, 'H'));
		add_item(new Item("きびだんご", 'き', 5, 'H'));
	}

	@Override
	public void action(int key){
		switch(key){
		case 8:
			update_que();
			go_up();
			break;
		case 6:
			update_que();
			go_right();
			break;
		case 2:
			update_que();
			go_down();
			break;
		case 4:
			update_que();
			go_left();
			break;
		case 5: // Stay
			break;
		case 0:
			menu_handler();
			break;
		default:
			break;
		}
	}
	
	public void update_que() {
		int i;
		int[] next_pos = this.get_position();
		int[] prev_pos = new int[2];
		
		for(i = 0; i < animals.size(); i++) {
			prev_pos = animals.get(i).get_position();
			animals.get(i).set_position(next_pos[0], next_pos[1]);
			next_pos = animals.get(i).get_position();
			/* System.out.println(animals.get(i).get_name() + "at new (" + 
					animals.get(i).get_position()[0] + "," + 
					animals.get(i).get_position()[1] + ")"); */
			if(i >= 1) {
				char token;
				switch(i) {
				case 1:
					token = '２';
					break;
				case 2:
					token = '３';
					break;
				default:
					token = 'E';
				}
				animals.get(i).set_token(token);
			}
		}
	}


	public void update(ArrayList<int []> c_pos) {
		int key = read_key(1);
		// Stay if collision happened (key 5)
		if(collide(key, c_pos)) key = 5;
		action(key);
	}
	
	private void add_item(Item item) {
		this.inventory.add(item);
	}

	public void menu_handler() {
		int i;
		int key;
				
		while(true){
			System.out.println("　・・・　ステータス　・・・　");
			System.out.println("たいりょく　：　" + this.get_hp());
			System.out.println("こうげき : " + this.get_ap());
			
			System.out.println("　・・・　アイテム　・・・　");
			for(i = 0; i < this.inventory.size(); i++) {
				System.out.println(this.inventory.get(i).get_name());
			}
			System.out.println("ゴールド・・・" + this.gold + "Ｇ");
			System.out.println("●　アイテム　を　つかう？:1");
			System.out.println("●　メニュー　を　とじる？:0");
			key = read_key(2);
			if(key == 1){
				if(this.inventory.size() == 0) System.out.println("アイテム が　ありません");
				else choose_item(inventory);
			}
			else if(key == 0) break;
		}
	}
	
	public void choose_item(ArrayList<Item> inventory){
		int i;
		int key;
		
		while(true){
			System.out.println("どれ　を　つかいますか？");
			for(i = 0; i < inventory.size(); i++){
				System.out.println(i + "　．．．　" + inventory.get(i).get_name());
			}
			key = read_key(2);
			if(0 <= key && key < inventory.size()){
				this.use_item(inventory.get(key));
				inventory.remove(key);
			}
			else{
				System.out.println("そんざい　しません");
			}

			if(inventory.size() == 0) break;
			System.out.println("つづけて　つかいますか？");
			System.out.println("つかう:1　　やめる:0");
			key = read_key(2);
			if(key == 0) break;
			else if(key == 1) continue;
		}
	}

	public void use_item(Item item) {
		switch(item.get_type()){
		case 'H':
			this.add_hp(item.get_point());
			break;
		case 'A':
			this.add_ap(item.get_point());
			break;
		}
		System.out.println(this.get_name() + "　は　" + item.get_name() + "　を　つかった");
		System.out.println(item.get_status_code() + " が　" + item.get_point() + "　あがった");
	}

	public void pickup(Item item) {
		System.out.println(this.get_name() + "　は　" + item.get_name() + "　を　てにいれた"
				+ "");
		if(item.get_type() == 'G')
			this.gold += item.get_point();
		else
			this.add_item(item);
	}
	
	// If the player have the item to pick up animals return true otherwise false.
	public boolean pickup(Animal animal) {
		int i;
		for(i = 0; i < this.inventory.size(); i++) {
			if(this.inventory.get(i).get_name().equals("きびだんご")) {
				System.out.println(this.get_name() + "　は　きびだんご　を　つかった");
				System.out.println(animal.get_name() + "　が　なかまになった");
				this.inventory.remove(i);
				this.animals.add(animal);
				return true;
			}
		}
		System.out.println(this.get_name() + "　きびだんご　を　もっていない");
		return false;
	}
	
	public boolean goal_judge(int x, int y) {
		int [] pos = this.get_position();
		if(pos[0] == x && pos[1] == y) {
			System.out.println(this.get_name() + "は　ステージ　を　クリア　した");
			return true;
		}
		else return false;
	}
	
	public ArrayList<Animal> get_animals() {
		return this.animals;
	}

	private int read_key(int type) {
		String str = "";
		int key;
		
		System.out.print("\nソウサ：");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			str = br.readLine();
		}
		catch(IOException e) {
			System.out.println("IOError");
		}
		
		try {
			key = Integer.parseInt(str);
		}
		catch(NumberFormatException e) {
			System.out.println("ソウサ　フカ");
			key = 5;
		}

		if(type == 1) {
			if(key == 8 || key == 6 || key == 2 || key == 4 || key == 5 || key == 0)
				return key;
		}
		else if(type == 2) {
			if(0 <= key && key < 10)
				return key;
		}
		return -1;

	}

}
