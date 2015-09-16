package gr11sumative;

public class Player extends Entity{
	boolean jumpingup;
	boolean jumping;
	int height;
	public Player(Vector loc) {
		super(loc, 50);
		jumpingup=false;
		height = 0;
	}
}
