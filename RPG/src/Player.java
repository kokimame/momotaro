import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Player extends Character{

	int gold;		// Total value of golds the player has
	private ArrayList<Item> inventory = new ArrayList<Item>();

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
			menu_handler();
			break;
		default:
			break;
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
	
	public boolean goal_judge(int x, int y) {
		int [] pos = this.get_position();
		if(pos[0] == x && pos[1] == y) {
			System.out.println(this.get_name() + "は　ステージ　を　クリア　した");
			return true;
		}
		else return false;
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
