
public class Zombie extends Enemy{

	public Zombie(String name, char token, int hp, int x, int y) {
		super(name, token, hp, x, y, 1);
		super.set_drop(new Item("10Ｇ", 'Ｇ', 10, 'G', x, y));
	}

}
