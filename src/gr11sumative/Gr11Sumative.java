package gr11sumative;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;

import java.io.*;
import java.util.ArrayList;

public class Gr11Sumative extends PApplet {

	boolean right, up, down, left;

	boolean mouse;

	boolean space;

	int menu;
	int colorwheel;
	boolean colorwheelup;

	PFont font_t1;

	Player player;

	int shoot_timer;

	int counter;

	int jumpshadow;

	int rotatepointer;

	double score;

	double spawnrate;

	String scorepr;

	boolean highscorecheck;

	double highscore;

	ArrayList<Entity> bullets = new ArrayList<Entity>();
	ArrayList<Entity> enemies = new ArrayList<Entity>();

	BufferedReader infile;
	PrintWriter output;

	public void setup() {

		colorwheel = 170;
		font_t1 = createFont("TimesNewRoman", 50, true);
		menu = 3;
		shoot_timer = 40;
		rotatepointer = 0;
		jumpshadow = 0;
		player = new Player(new Vector(width / 2, height / 2));
		counter = 0;

		background(255);
		frameRate(50);
		size(1000, 800);

		noCursor();
	}

	public void draw() {

		if (menu == 1) {

			highscorecheck = true;
			score = score + .02;
			shoot_timer--;

			collision_wall();

			friction();
			background(0);
			noFill();

			if (counter > 0)
				counter--;
			jump();

			movement_p();

			if (mouse && shoot_timer < 0) {
				bullet_nw();
				shoot_timer = 4;
			}


			if (spawnrate < 40) {
				spawnrate =  score;
			}

			if (round(random((float) (40 - spawnrate))) == 0) {
				enemy_nw();
			}

			fill(255);
			rect((float)(player.location.x), (float)(player.location.y), (float)(player.size + player.height), (float)(player.size + player.height));

			textFont(font_t1, 52);
			fill(0);
			text("" + score, 250, 200);

			textFont(font_t1, 50);
			fill(255, 255, 255);
			text("" + score, 250, 200);

			mousepointer(255);
		}

		else if (menu == 2) {

			try {

				if (highscorecheck) {
					highscore = highscore(score);
					highscorecheck = false;
				}
			} catch (IOException e) {
				System.err.println("Caught IOException: " + e.getMessage());
			}

			background(255);

			if (score > 0) {
				scorepr = "" + score;
				score = 0;
			}

			textFont(font_t1, 36);
			fill(colorwheel, 0, 0);
			text("The current Highscore:" + highscore, width / 2 - 440, 400);
			text("Your Score: " + scorepr, width / 2 - 440, 100);
			text("RETRY", width / 2 - 415, 180);

			if (mouseX < width / 2 - 300 && mouseX > width / 2 - 430 && mouseY < 200 && mouseY > 140 && mouse == true) {

				bullets.clear();
				enemies.clear();
				player = new Player(new Vector(width / 2, height / 2));
				shoot_timer = 40;
				jumpshadow = 1;
				space = false;
				spawnrate = 0;

				menu = 1;
			}

			mousepointer(0);
		}

		else if (menu == 3) {

			background(255);
			textFont(font_t1, 50);
			fill(colorwheel, 0, 0);
			text("The Incredible Dodgeball Desperadoes", width / 2 - 440, 100);
			textFont(font_t1, 25);
			text("PLAY", width / 2 - 415, 180);
			text("INSTRUCTIONS", width / 2 + 240, 180);

			if (mouseX < width / 2 - 300 && mouseX > width / 2 - 430 && mouseY < 200 && mouseY > 140 && mouse == true) {

				menu = 1;
			} else if (mouseX < width / 2 + 430 && mouseX > width / 2 + 240 && mouseY < 200 && mouseY > 140 && mouse == true) {

				menu = 4;
			}
			mousepointer(0);
		}

		else if (menu == 4) {

			background(255);
			textFont(font_t1, 60);
			fill(colorwheel, 0, 0);
			text("Instructions", width / 2 - 240, 100);
			textFont(font_t1, 30);
			fill(colorwheel, 0, 0);
			text("W=up A=right S=down D=right", width / 2 - 440, 200);
			text("Space is used to jump", width / 2 - 440, 300);
			text("You control the white square", width / 2 - 440, 400);
			text("You are chased by red circles that kill on touch", width / 2 - 440, 500);
			text("You aim and shoot with your mouse", width / 2 - 440, 600);
			text("You can jump over the red circles with the spacebar", width / 2 - 440, 700);

			textFont(font_t1, 35);
			fill(colorwheel, 0, 0);
			text("Back to main menu", width / 2 + 100, 200);
			if (mouseX < width / 2 + 430 && mouseX > width / 2 + 100 && mouseY < 200 && mouseY > 160 && mouse == true) {
				menu = 3;
			}

			mousepointer(0);
		}

		if (colorwheelup == false)
			colorwheel++;
		else
			colorwheel--;
		if (colorwheel == 256)
			colorwheelup = true;
		else if (colorwheel == 170)
			colorwheelup = false;
	}

	void jump() {

		if (counter <= 0 && space == true) {
			fill(0);
			if (jumpshadow == 30) {
				player.jumpdirection = true;
			} else if (jumpshadow == -1) {
				player.jumpdirection = false;
				space = false;
				counter = 30;
			}
			if (player.jumpdirection) {
				jumpshadow--;
				player.size--;
			} else {
				jumpshadow++;
				player.size++;
			}
		} else {
			space = false;
		}
	}

	void movement_p() {

		if (left == true && space == false && Vector.getMagnitude(player.velocity) <= 300) {
			player.velocity.x = player.velocity.x - 0.8;
		}
		if (right == true && space == false && Vector.getMagnitude(player.velocity) <= 300) {
			player.velocity.x = player.velocity.x + 0.8;
		}
		if (down == true && space == false && Vector.getMagnitude(player.velocity) <= 300) {
			player.velocity.y = player.velocity.y + 0.8;
		}
		if (up == true && space == false && Vector.getMagnitude(player.velocity) <= 300) {
			player.velocity.y = player.velocity.y - 0.8;
		}

		player.location.y = player.location.y + player.velocity.y;
		player.location.x = player.location.x + player.velocity.x;
	}

	void bullet_nw() {
		Vector bdirection = new Vector(mouseX-player.location.x, mouseY - player.location.y);
		Vector.normalizeVector(bdirection);
		Vector.scalarProduct(bdirection, 2);
		bullets.add(new Entity(player.location, bdirection, 5));
	}

	void enemy_nw() {

		int wall;
		int location;
		Vector temp;
		wall = (int)(Math.random()*4);

		if (wall == 0) {
			location = round(random(0, 800));
			temp = new Vector(0, location);
		} else if (wall == 1) {
			location = round(random(0, 800));
			temp = new Vector(1000, location);
		} else if (wall == 2) {
			location = round(random(0, 1000));
			temp = new Vector(location, 0);
		} else {
			location = round(random(0, 1000));
			temp = new Vector(location, 800);
		}
		temp2 = new Vector((player.location.x - temp.x) / 200, (player.locations.y - temp.y) / 200);

		enemies.add(new Entity);
		momentum_e.add(temp2);
		grace_period_e.add(30);
	}

	void printarray(ArrayList arrayloc, ArrayList arraymom, ArrayList grace, int form) {
		PVector enemy;
		boolean deletebullet;

		for (int i = 0; i < arrayloc.size(); i++) {
			deletebullet = true;

			temp = (PVector) arrayloc.get(i);
			temp2 = (PVector) arraymom.get(i);
			temp3 = (Integer) grace.get(i);

			if (form == 1) {
				stroke(255);
				fill(0, 0, 0);
				ellipse(temp.x, temp.y, 5, 5);
				stroke(0);
				for (int x = 0; x < enemies.size(); x++) {
					enemy = (PVector) enemies.get(x);
					if (abs(enemy.x - temp.x) < 40 && abs(enemy.y - temp.y) < 40) {
						enemies.remove(x);
						momentum_e.remove(x);
						grace_period_e.remove(x);
						deletebullet = false;
					}
				}
			}

			else if (form == 2) {
				fill(colorwheel, 0, 0);
				ellipse(temp.x, temp.y, 40, 40);
			}

			if (temp3 < 0) {
				temp2 = collision(temp, temp2, form);
			} else {
				temp3--;
			}
			if (deletebullet) {
				temp.x = temp.x + temp2.x;
				temp.y = temp.y + temp2.y;
				arrayloc.set(i, temp);
				grace.set(i, temp3);
			} else {
				arrayloc.remove(i);
				arraymom.remove(i);
				grace.remove(i);
			}
		}
	}

	void mousepointer(int colour) {
		rotatepointer++;
		if (rotatepointer == 361) {
			rotatepointer = 0;
		}

		pushMatrix();
		translate(mouseX, mouseY);
		rotate(radians(rotatepointer));
		stroke(colour);
		line(-2, -2, -7, -7);
		line(2, 2, 7, 7);
		line(2, -2, 7, -7);
		line(-2, 2, -7, 7);
		stroke(0);

		translate(-mouseX, -mouseY);
		popMatrix();
	}

	void collision_wall() {
		if (player.location.x > 950) {
			player.velocity.x = player.velocity.x * (-1) - 16;
		}
		if (player.location.y > 750) {
			player.velocity.y = player.velocity.y * (-1) - 16;
		}
		if (player.location.x < 1) {
			player.velocity.x = player.velocity.x * (-1) + 16;
		}
		if (player.location.y < 1) {
			player.velocity.y = player.velocity.y * (-1) + 16;
		}
	}

	PVector collision(Entity ent1, Entity ent2) {

		if (location.x > 1000) {
			momentum.x = momentum.x * (-1);
		}
		if (location.y > 800) {
			momentum.y = momentum.y * (-1);
		}
		if (location.x < 1) {
			momentum.x = momentum.x * (-1);
		}
		if (location.y < 1) {
			momentum.y = momentum.y * (-1);
		}
		if (form == 1) {
			if (location.x < location_p.x + psize && location.x > location_p.x && location.y < location_p.y + psize && location.y > location_p.y && jumpshadow < 5) {
				menu = 2;
			}
		} else {
			if (location.x - 20 < location_p.x + psize && location.x + 20 > location_p.x && location.y - 20 < location_p.y + psize && location.y + 20 > location_p.y
					&& jumpshadow < 5)
				menu = 2;
		}

		return momentum;
	}

	void friction() {
		if (player.velocity.x < 0 && space == false) {
			player.velocity.x = player.velocity.x + 4;
		} else if (player.velocity.x > 0 && space == false) {
			player.velocity.x = player.velocity.x - 4;
		}
		if (player.velocity.y < 0 && space == false) {
			player.velocity.y = player.velocity.y + 4;
		} else if (player.velocity.y > 0 && space == false) {
			player.velocity.y = player.velocity.y - 4;
		}
	}

	public void keyPressed() {

		int ab = (int) key;
		if (ab >= 97) {
			ab = (int) key - 32;
		}
		key = (char) ab;

		if (key == ' ') {
			space = true;
		}
		if (key == 'A') {
			left = true;
		}
		if (key == 'D') {
			right = true;
		}
		if (key == 'S') {
			down = true;
		}
		if (key == 'W') {
			up = true;
		}
	}

	public void mousePressed() {
		mouse = true;
	}

	public void mouseReleased() {
		mouse = false;
	}

	public void keyReleased() {

		int ab = (int) key;
		if (ab >= 97) {
			ab = (int) key - 32;
		}
		key = (char) ab;

		if (key == 'A') {
			left = false;
		}
		if (key == 'D') {
			right = false;
		}
		if (key == 'S') {
			down = false;
		}
		if (key == 'W') {
			up = false;
		}
	}

	double highscore(double currentscore) throws IOException {
		String currentline;
		int lengthname;
		double currenthighscore;
		int x;
		boolean whichscore;

		infile = createReader("highscoreshenry.txt");

		currentline = infile.readLine();
		try {
			currenthighscore = Double.parseDouble(currentline);
		} catch (Exception e) {
			currenthighscore = -1;
		}

		infile.close();

		output = createWriter("highscoreshenry.txt");
		if (currenthighscore < currentscore) {
			output.println(currentscore);
			whichscore = true;
		} else {
			output.println(currenthighscore);
			whichscore = false;
		}

		output.flush();
		output.close();
		if (whichscore) {
			return currentscore;
		} else {
			return currenthighscore;
		}
	}

	void quit() throws IOException {

		exit();
	}
}
