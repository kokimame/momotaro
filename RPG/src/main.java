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

		Player player = new Player("ユウシャ", 10, 0, 0, 5);
		enemies.add(new Zombie("ゾンビA", 10, 4, 0));
		enemies.add(new Zombie("ゾンビB", 10, 3, 3));

		for(;;){
			System.out.println(player.get_name() + "　ＨＰ：" + player.get_hp());
			show_map(map, player, enemies);
			update(player, enemies);

		}

	}

	static void update(Player player, ArrayList<Enemy> enemies) {
		player.update();

		near_enemies(player, enemies);

	}

	static void near_enemies(Player player, ArrayList<Enemy> enemies) {

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
				System.out.println(enemies.get(i).get_name() + " を　たおした");
				enemies.remove(i);
				break;}
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

	static void show_map(char map[][], Player p, ArrayList<Enemy> e) {
		int i, j;
		int[] p_pos = p.get_position() ;

		for(i = 0; i < map.length; i++){
			for(j = 0; j < map[0].length; j++) {
				if(i == p_pos[1] && j == p_pos[0])
					map[i][j] = '勇';
				else if(i == map.length - 1 && j == map[0].length - 1)
					map[i][j] = 'Ｇ';
				else if((i == 1 || i == 2 || i == 3 || i == 4 || i == 5) && j == 4)
					map[i][j] = '■';
				else
					map[i][j] = '□';
			}
		}

		for(i = 0; i < e.size(); i++)
			map[e.get(i).get_position()[1]][e.get(i).get_position()[0]] = '×';



		for(i = 0; i < map.length; i++){
			for(j = 0; j < map[0].length; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}


	}

}
