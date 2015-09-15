package gr11sumative;

public class Player extends Entity{
	boolean jumpdirection;
	int height;
	public Player(Vector loc) {
		super(loc, 50);
		jumpdirection=false;
		height = 0;
	}
}
