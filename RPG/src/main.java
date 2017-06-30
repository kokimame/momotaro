import java.util.ArrayList;

public class main {

	/**
	 * @param args
	 */


	public static void main(String[] args) {

		int Width = 8;
		int Height = 6;
		char map[][] = new char[Height][Width];
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		ArrayList<Item> drops = new ArrayList<Item>();

		Player player = new Player("ユウシャ", '勇', 10, 0, 0, 5);
		enemies.add(new Zombie("ゾンビA", 'ゾ', 10, 4, 0));
		enemies.add(new Zombie("ゾンビB", 'ゾ', 10, 3, 3));

		for(;;){
			System.out.println(player.get_name() + "　ＨＰ：" + player.get_hp());
			show_map(map, player, enemies, drops);
			update(player, enemies, drops);

		}

	}

	static void update(Player player, ArrayList<Enemy> enemies, ArrayList<Item> drops) {
		player.update();

		near_enemies(player, enemies, drops);

	}

	static void near_enemies(Player player, ArrayList<Enemy> enemies, ArrayList<Item> drops) {

		int i;
		int[] p_pos = player.get_position();
		int[] e_pos;

		for(i = 0; i < enemies.size(); i++) {
			e_pos = enemies.get(i).get_position();
			
			if(p_pos[0] == e_pos[0] - 1 && p_pos[1] == e_pos[1])
				battle(player, enemies.get(i));
			else if(p_pos[0] == e_pos[0] + 1 && p_pos[1] == e_pos[1])
				battle(player, enemies.get(i));
			else if(p_pos[0] == e_pos[0] && p_pos[1] == e_pos[1] - 1)
				battle(player, enemies.get(i));
			else if(p_pos[0] == e_pos[0] && p_pos[1] == e_pos[1] + 1)
				battle(player, enemies.get(i));

			if(enemies.get(i).get_hp() <= 0){
				System.out.println(player.get_name() + "　は　" + enemies.get(i).get_name() 
						+ " を　たおした");
				enemies.get(i).drop(drops);
				enemies.remove(i);
				i = 0;
			}
		}
	}


	static void battle(Player player, Enemy enemy) {

		enemy.get_damage(player.attack());
		System.out.println(player.get_name() + "　が　" + enemy.get_name() 
				+ "　に　" + player.attack() + "　の　ダメージ　を　あたえた");
		player.get_damage(enemy.attack());
		System.out.println(enemy.get_name() + "　が　" + player.get_name() 
				+ "　に　" + enemy.attack() + "　の　ダメージ　を　あたえた");

	}

	static void show_map(char map[][], Player p, ArrayList<Enemy> e, ArrayList<Item> items) {
		int i, j;
		int[] p_pos = p.get_position() ;

		for(i = 0; i < map.length; i++){
			for(j = 0; j < map[0].length; j++) {
				if(i == p_pos[1] && j == p_pos[0])
					map[i][j] = p.get_token();
				else if(i == map.length - 1 && j == map[0].length - 1)
					map[i][j] = '◎';
				else if((i == 1 || i == 2 || i == 3 || i == 4 || i == 5) && j == 4)
					map[i][j] = '■';
				else
					map[i][j] = '□';
			}
		}

		for(i = 0; i < e.size(); i++)
			map[e.get(i).get_position()[1]][e.get(i).get_position()[0]] = e.get(i).get_token();

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
