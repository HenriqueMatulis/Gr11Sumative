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
		player = new Player(new Vector(width / 2.0, height / 2.0));
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

			background(0);
			noFill();

			jump();

			movementkeys();
			friction();
			addTick(player);
			collision_wall(player);
			for (int i = 0; i < bullets.size(); i++) {
				addTick(bullets.get(i));
				collision_wall(bullets.get(i));
				if(bullets.get(i).grace>0){
					bullets.get(i).grace--;
				}
			}
			for (int i = 0; i < enemies.size(); i++) {
				addTick(enemies.get(i));
				collision_wall(enemies.get(i));
				if(enemies.get(i).grace>0){
					enemies.get(i).grace--;
				}
			}

			if (mouse && shoot_timer < 0) {
				shootBullet();
				shoot_timer = 4;
			}

			if (spawnrate < 40) {
				spawnrate = score;
			}

			if (round(random((float) (40 - spawnrate))) == 0) {
				createEnemy();
			}
			if (!player.jumping) {
				for (int i = 0; i < bullets.size(); i++) {
					if (collision(bullets.get(i), player)) {
						menu = 2;
					}
				}
				for (int i = 0; i < enemies.size(); i++) {
					if (collision(enemies.get(i), player)) {
						menu = 2;
					}
				}
			}

			for (Entity ent : bullets) {
				fill(255);
				ellipse((float) (ent.location.x), (float) (ent.location.y), (float) (ent.size), (float) (ent.size));
			}
			for (Entity ent : enemies) {
				fill(colorwheel, 0, 0);
				ellipse((float) (ent.location.x), (float) (ent.location.y), (float) (ent.size), (float) (ent.size));
			}
			for(int i = 0; i < bullets.size(); i++){
				for(int z=0; z<enemies.size(); z++){
					if(collision(enemies.get(z), bullets.get(i))){
						enemies.remove(z);
						bullets.remove(i);
						//z--;
						//i--;
					}
				}
			}

			fill(255);
			ellipse((float) (player.location.x), (float) (player.location.y), (float) (player.size + player.height), (float) (player.size + player.height));
			score *= 100;
			score = Math.round(score);
			score /= 100;
			textFont(font_t1, 52);
			fill(0);
			text("" + score, 250, 200);

			textFont(font_t1, 50);
			fill(255, 255, 255);
			text("" + score, 250, 200);

			mousepointer(255);
		}

		else if (menu == 2) {

			if (highscorecheck) {
				highscore = highscore(score);
				highscorecheck = false;
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
		if (player.jumping == true) {
			if (player.height >= 30) {
				player.jumpingup = false;
			} else if (player.height <= -1) {
				player.jumpingup = false;
				player.height = 1;
				player.jumping = false;
			}
			if (!player.jumpingup) {
				player.height--;
			} else {
				player.height++;
			}
		}

		if (space && !player.jumping) {
			player.jumping = true;
			player.jumpingup=true;
		}
	}

	void movementkeys() {

		if (left == true && player.jumping == false && Vector.getMagnitude(player.velocity) <= 300) {
			player.velocity.x = player.velocity.x - 0.8;
		}
		if (right == true && player.jumping == false && Vector.getMagnitude(player.velocity) <= 300) {
			player.velocity.x = player.velocity.x + 0.8;
		}
		if (down == true && player.jumping == false && Vector.getMagnitude(player.velocity) <= 300) {
			player.velocity.y = player.velocity.y + 0.8;
		}
		if (up == true && player.jumping == false && Vector.getMagnitude(player.velocity) <= 300) {
			player.velocity.y = player.velocity.y - 0.8;
		}
	}

	void addTick(Entity ent) {
		ent.location = Vector.vectorAddition(ent.location, ent.velocity);
	}

	void shootBullet() {
		Vector bdirection = new Vector(mouseX - player.location.x, mouseY - player.location.y);
		Vector.normalizeVector(bdirection);
		bdirection = Vector.scalarProduct(bdirection, 15);
		bullets.add(new Entity(player.location, bdirection, 5));
		bullets.get(bullets.size()-1).grace=3;
	}

	void createEnemy() {

		int wall;
		int location;
		Vector temp;
		wall = (int) (Math.random() * 4);

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
		Vector enemyVelocity = new Vector((player.location.x - temp.x), (player.location.y - temp.y));
		Vector.normalizeVector(enemyVelocity);
		enemyVelocity=Vector.scalarProduct(enemyVelocity, 5);
		enemies.add(new Entity(temp, enemyVelocity));
		enemies.get(enemies.size()-1).grace=30;

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

	void collision_wall(Entity ent1) {
		if(ent1.grace > 0) return;
		
		if (ent1.location.x> width) {
			ent1.velocity.x = ent1.velocity.x * (-1);
		}
		if (ent1.location.y > height) {
			ent1.velocity.y = ent1.velocity.y * (-1);
		}
		if (ent1.location.x < 1) {
			ent1.velocity.x = ent1.velocity.x * (-1);
		}
		if (ent1.location.y < 1) {
			ent1.velocity.y = ent1.velocity.y * (-1);
		}
	}

	boolean collision(Entity ent1, Entity ent2) {
		if(ent1.grace>0) return false;
		if(ent2.grace>0) return false;
		Vector distance = Vector.vectorSubtraction(ent1.location, ent2.location);
		if (Vector.getMagnitude(distance) <= ent1.size / 2.0 + ent2.size / 2.0) {
			return true; // entities collide
			// menu = 2;
		}

		return false;
	}

	void friction() {
		if (player.jumping == false && Vector.getMagnitude(player.velocity) != 0) {
			for (int i = 0; i < 3; i++) {
				Vector friction = new Vector(-player.velocity.x, -player.velocity.y);
				Vector.normalizeVector(friction);
				friction = Vector.scalarProduct(friction, 0.1);
				player.velocity = Vector.vectorAddition(friction, player.velocity);
			}
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

		if (key == ' ') {
			space = false;
		}
	}

	double highscore(double currentscore) {
		String currentline;
		double currenthighscore;
		try {
			infile = createReader("highscoreshenry.txt");
			currentline = infile.readLine();
			currenthighscore = Double.parseDouble(currentline);
			infile.close();
		} catch (Exception e) {
			currenthighscore = -1;
		}

		if (!(currenthighscore < currentscore)) {
			return currenthighscore;
		}
		output = createWriter("highscoreshenry.txt");
		output.println(currentscore);
		output.flush();
		output.close();
		return currentscore;
	}

	void quit() throws IOException {
		exit();
	}
}
