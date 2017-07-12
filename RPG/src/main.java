import java.util.ArrayList;

public class main {

	/**
	 * @param args
	 */


	public static void main(String[] args) {

		int Width = 8;
		int Height = 6;
		char map[][] = new char[Height][Width];
		// All characters to draw on the map
		ArrayList<Character> charas = new ArrayList<Character>();
		ArrayList<Item> drops = new ArrayList<Item>();

		// To their great surprise, a healthy baby boy came right out of the peach!
		Player player = new Player("モモタロウ", '桃', 10, 0, 0, 0);
		charas.add(new Zombie("ゾンビA", 'ゾ', 10, 4, 0));
		charas.add(new Zombie("ゾンビB", 'ゾ', 10, 3, 3));
		charas.add(new Animal("イヌ", '犬', 10, 0, 2, 2));
		charas.add(new Animal("サル", '猿', 10, 7, 0, 2));
		charas.add(new Animal("キジ", '雉', 10, 5, 2, 2));

		System.out.println("----------------");
		System.out.println("Move:↑:8 ↓:2 ←:4 →:6, Stay:5");
		System.out.println("Open Menu:0");
		System.out.println("----------------");
		
		for(;;){
			show_map(map, player, charas, drops);
			game_loop(player, charas, drops);
			if(player.goal_judge(Width - 1, Height - 1)) {
				break;
			}
		}
		
		show_map(map, player, charas, drops);
		System.out.println("\n\n\n\n\n\n\nめでたしめでたし");
	}

	static void game_loop(Player player, ArrayList<Character> charas, ArrayList<Item> drops) {
		player.update(collision_pos(charas));
		around_objects(player, charas, drops);
	}

	static ArrayList<int[]> collision_pos(ArrayList<Character> charas) {
		int i, j;
		ArrayList <int []> c_pos = new ArrayList<int []>();
		for(i = 0; i < charas.size(); i++) {
			c_pos.add(charas.get(i).get_position());
		}
		// FIXME: Wall definition are separated among functions.
		// Hopefully there should be a map object binding all these stuff.
		for(i = -1; i <= 6; i++) {
			for(j = -1; j <= 8; j++) {
				if((j == 4 && i >= 1 && i <= 5) || (j == -1 || j == 8 || i == -1 || i == 6)){
					int[] wall  = new int[2];
					wall[0] = j; wall[1] = i;
					c_pos.add(wall);
				}
			}
		}
		return c_pos;
	}

	static void around_objects(Player player, ArrayList<Character> charas, ArrayList<Item> drops) {

		int i;
		int[] p_pos = player.get_position();
		// FIXME: Use ch_pos to store positions of all objects under Character.
		int[] ch_pos, i_pos; // Character and Item position

		for(i = 0; i < charas.size(); i++) {
			ch_pos = charas.get(i).get_position();
			if(charas.get(i) instanceof Enemy){
				// TODO: Refer here "down-casting" relating to OOP
				Enemy enemy = (Enemy)charas.get(i);
				
				if(p_pos[0] == ch_pos[0] - 1 && p_pos[1] == ch_pos[1])
					battle(player, enemy);
				else if(p_pos[0] == ch_pos[0] + 1 && p_pos[1] == ch_pos[1])
					battle(player, enemy);
				else if(p_pos[0] == ch_pos[0] && p_pos[1] == ch_pos[1] - 1)
					battle(player, enemy);
				else if(p_pos[0] == ch_pos[0] && p_pos[1] == ch_pos[1] + 1)
					battle(player, enemy);
	
				if(enemy.get_hp() <= 0){
					System.out.println(player.get_name() + "　は　" + enemy.get_name() 
							+ " を　たおした");
					enemy.drop(drops);
					charas.remove(i);
					i = 0;
				}
			}
			else if(charas.get(i) instanceof Animal){
				Animal animal = (Animal)charas.get(i);
				if(p_pos[0] == ch_pos[0] - 1 && p_pos[1] == ch_pos[1] ||
				   p_pos[0] == ch_pos[0] + 1 && p_pos[1] == ch_pos[1] ||
				   p_pos[0] == ch_pos[0] && p_pos[1] == ch_pos[1] - 1 ||
				   p_pos[0] == ch_pos[0] && p_pos[1] == ch_pos[1] + 1){
					if(player.pickup(animal))
						charas.remove(i);
				}
			}
		}

		for(i = 0; i < drops.size(); i++) {
			i_pos = drops.get(i).get_position();
			if(p_pos[0] == i_pos[0] && p_pos[1] == i_pos[1]) {
				player.pickup(drops.get(i));
				drops.remove(i);
			}
		}
	}


	static void battle(Player player, Enemy enemy) {

		enemy.get_damaged(player.attack());
		System.out.println(player.get_name() + "　は　" + enemy.get_name() 
				+ "　に　" + player.attack() + "　の　ダメージ　を　あたえた");
		player.get_damaged(enemy.attack());
		System.out.println(enemy.get_name() + "　は　" + player.get_name() 
				+ "　に　" + enemy.attack() + "　の　ダメージ　を　あたえた");

	}

	static void show_map(char map[][], Player p, ArrayList<Character> charas, ArrayList<Item> items) {
		int i, j;
		int[] p_pos = p.get_position() ;

		for(i = 0; i < map.length; i++){
			for(j = 0; j < map[0].length; j++) {
				if(i == p_pos[1] && j == p_pos[0])
					map[i][j] = p.get_token();
				else if(i == map.length - 1 && j == map[0].length - 1)
					map[i][j] = '島';
				else if((i == 1 || i == 2 || i == 3 || i == 4 || i == 5) && j == 4)
					map[i][j] = '川';
				else if(i == 0 && j == 0)
					map[i][j] = '村';
				else
					map[i][j] = '口';
			}
		}

		for(i = 0; i < charas.size(); i++)
			map[charas.get(i).get_position()[1]][charas.get(i).get_position()[0]] =
							charas.get(i).get_token();
		
		for(i = 0; i < p.get_animals().size(); i++){
			Animal animal = p.get_animals().get(i);
			map[animal.get_position()[1]][animal.get_position()[0]] = animal.get_token();
		}

		for(i = 0; i < items.size(); i++)
			map[items.get(i).get_position()[1]][items.get(i).get_position()[0]] = items.get(i).get_token();

		for(i = 0; i < map.length; i++){
			for(j = 0; j < map[0].length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}

	}
}
