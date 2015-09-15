package gr11sumative;

public class Vector {
	public double x,y;
	private double mag;
	public Vector(double xComp, double yComp){
		x=xComp;
		y=yComp;
	}
	public Vector(){
		x=0;
		y=0;
		mag=0;
	}
	
	public static void normalizeVector(Vector v){
		double magnitude=getMagnitude(v);
		v.x= v.x/magnitude;
		v.y= v.y/magnitude;
	}
	public static double getMagnitude(Vector v){
		return Math.sqrt( (v.x*v.x) + (v.y*v.y));
	}
	
	public static double getDotProduct(Vector v1, Vector v2){
		return (v1.x*v2.x + v1.y*v2.y);
	}
	
	public static Vector scalarProduct(Vector v1, double scalar){
		Vector r = new Vector();
		r.x=v1.x*scalar;
		r.y=v1.y*scalar;
		return r;
	}
	
	
	public static Vector vectorAddition(Vector v1, Vector v2){
		Vector r = new Vector();
		r.x=v1.x+v2.x;
		r.y=v1.y+v2.y;
		return r;
		
	}
	
	public static Vector vectorSubtraction(Vector v1, Vector v2){
		Vector r = new Vector();
		r.x=v1.x-v2.x;
		r.y=v1.y-v2.y;
		return r;
		
	}
}
