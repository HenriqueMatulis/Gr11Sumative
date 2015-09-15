package gr11sumative;

public class Entity {
	public Vector location;
	public Vector velocity;
	public double size;
	public Entity(Vector loc) {
		location.x=loc.x;
		location.y=loc.y;
		velocity.x=0;
		velocity.y=0;
		size=40; //bullet size is 5
	}
	public Entity(Vector loc, Vector v) {
		location.x=loc.x;
		location.y=loc.y;
		velocity.x=v.x;
		velocity.y=v.y;
		size=40; //bullet size is 5
	}
	public Entity(Vector loc, int size) {
		location.x=loc.x;
		location.y=loc.y;
		velocity.x=0;
		velocity.y=0;
		size=size; //bullet size is 5
	}
	

	public Entity(Vector loc, Vector v, int size) {
		location.x=loc.x;
		location.y=loc.y;
		velocity.x=v.x;
		velocity.y=v.y;
		size=size; //bullet size is 5
	}
}
