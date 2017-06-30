import java.util.ArrayList;

public class Enemy extends Character{

	private Item drop;

	public Enemy(String name, char token, int hp, int x, int y, int ap) {
		super(name, token, hp, x, y, ap);
	}

	public void set_drop(Item drop) {
		this.drop = drop;
	}

	// Return true if an enemy has a drop otherwise false.
	public boolean has_drop() {
		return this.drop != null;
	}

	public void drop(ArrayList<Item> map_drops) {
		if(this.has_drop()) {
			System.out.println(super.get_name() + "　が　" + this.drop.get_name() + "　を　おとした");
			map_drops.add(this.drop);
		}
		else
			return;
	}

}
