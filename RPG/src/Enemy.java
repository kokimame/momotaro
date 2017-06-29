
public class Enemy extends Character{

	private String name;

	public Enemy(int hp, int x, int y, int ap, String name) {
		super(hp, x, y, ap);
		this.name = name;
	}

	public String get_name() {
		return name;
	}

}
