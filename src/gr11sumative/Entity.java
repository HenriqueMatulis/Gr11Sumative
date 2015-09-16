package gr11sumative;

public class Entity {
	public Vector location;
	public Vector velocity;
	public double size;
	public int grace;
	public Entity(Vector loc) {
		location= new Vector(loc.x, loc.y);
		velocity= new Vector();
		size=40; //bullet size is 5
	}
	public Entity(Vector loc, Vector v) {
		location= new Vector(loc.x, loc.y);
		velocity= new Vector(v.x, v.y);
		size=40; //bullet size is 5
	}
	public Entity(Vector loc, int s) {
		location= new Vector(loc.x, loc.y);
		velocity= new Vector();
		size=s; //bullet size is 5
	}
	

	public Entity(Vector loc, Vector v, int s) {
		location= new Vector(loc.x, loc.y);
		velocity= new Vector(v.x, v.y);
		size=s; //bullet size is 5
	}
}
