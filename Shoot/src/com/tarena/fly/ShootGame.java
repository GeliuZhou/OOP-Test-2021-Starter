package com.tarena.fly;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShootGame extends JPanel {
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400; //Panel width
	public static final int HEIGHT = 654; //Panel height
	/** The current state of the game: START RUNNING PAUSE GAME_OVER */
	private int state;
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;

	private int score = 0; //Score
	private Timer timer; // Timer
	private int intervel = 1000 / 100; // Time interval (milliseconds)

	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage airplane;
	public static BufferedImage bee;
	public static BufferedImage bullet;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage pause;
	public static BufferedImage gameover;

	private FlyingObject[] flyings = {}; // Enemy aircraft array
	private Bullet[] bullets = {}; // Bullet array
	private Hero hero = new Hero(); // Hero Aircraft

	static { // Static code block to initialize image resources
		try {
			background = ImageIO.read(ShootGame.class
					.getResource("background.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			airplane = ImageIO
					.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO
					.read(ShootGame.class.getResource("gameover.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Draw */
	@Override
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null); // Draw a background image
		paintHero(g); // Draw hero airplane
		paintBullets(g); // Draw bullet
		paintFlyingObjects(g); // Draw a flyer
		paintScore(g); // Draw score
		paintState(g); // Draw game state̬
	}

	/** Draw hero airplane */
	public void paintHero(Graphics g) {
		g.drawImage(hero.getImage(), hero.getX(), hero.getY(), null);
	}

	/** Draw bullet */
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(),
					null);
		}
	}

	/** Draw a flyer */
	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			g.drawImage(f.getImage(), f.getX(), f.getY(), null);
		}
	}

	/** Draw score */
	public void paintScore(Graphics g) {
		int x = 10; // x coordinate
		int y = 25; // y coordinate
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22); // font
		g.setColor(new Color(0xFF0000));
		g.setFont(font); // set font
		g.drawString("SCORE:" + score, x, y); // draw score
		y=y+20; // y coordinate increases by 20
		g.drawString("LIFE:" + hero.getLife(), x, y); // draw life
	}

	/** Draw game state̬ */
	public void paintState(Graphics g) {
		switch (state) {
		case START: // start state̬
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE: //Pause state̬
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER: // Game termination status̬
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Fly");
		ShootGame game = new ShootGame(); // Panel object
		frame.add(game); // add the panel to the JFrame
		frame.setSize(WIDTH, HEIGHT); // set size
		frame.setAlwaysOnTop(true); // Set it to always be on top
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Default close operation
		frame.setIconImage(new ImageIcon("images/icon.jpg").getImage()); // Set the icon of the form
		frame.setLocationRelativeTo(null); // Set the initial position of the form
		frame.setVisible(true); // call paint as soon as possible

		game.action(); // start execution
	}

	/** Start execution code*/
	public void action() {
		// Mouse listener event
		MouseAdapter l = new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) { // mouse movement
				if (state == RUNNING) { //Move the hero machine in the running state - follow the mouse position
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) { // mouse enter
				if (state == PAUSE) { // run in pause state
					state = RUNNING;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) { // mouse exit
				if (state == RUNNING) { //The game is not over, set it to pause
					state = PAUSE;
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) { // mouse click
				switch (state) {
				case START:
					state = RUNNING; // run in startup state
					break;
				case GAME_OVER: // The game is over, clean up the scene
					flyings = new FlyingObject[0]; //Clear flying objects
					bullets = new Bullet[0]; // Empty bullets
					hero = new Hero(); //recreate the hero machine
					score = 0; // Clear the score
					state = START; // ״state is set to start
					break;
				}
			}
		};
		this.addMouseListener(l); // handle mouse click operation
		this.addMouseMotionListener(l); // handle mouse sliding operation

		timer = new Timer(); // main flow control
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (state == RUNNING) { // running state̬
					enterAction(); // Flying objects enter the arena
					stepAction(); // take a step
					shootAction(); // Hero shooting
					bangAction(); // Bullet hits the flying object
					outOfBoundsAction(); // delete out-of-bounds flying objects and bullets
					checkGameOverAction(); // check game over
				}
				repaint(); // Repaint, call paint() method
			}

		}, intervel, intervel);
	}

	int flyEnteredIndex = 0; // Flyer entry count

	/** Flyer admission */
	public void enterAction() {
		flyEnteredIndex++;
		if (flyEnteredIndex % 40 == 0) { // Generate a flying object in 400 milliseconds-10*40
			FlyingObject obj = nextOne(); // Randomly generate a flying object
			flyings = Arrays.copyOf(flyings, flyings.length + 1);
			flyings[flyings.length - 1] = obj;
		}
	}

	/** Take a step */
	public void stepAction() {
		for (int i = 0; i < flyings.length; i++) { //The flying object takes a step
			FlyingObject f = flyings[i];
			f.step();
		}

		for (int i = 0; i < bullets.length; i++) { // bullets take one step
			Bullet b = bullets[i];
			b.step();
		}
		hero.step(); // Hero airplane takes a step
	}

	/** The flying object takes a step */
	public void flyingStepAction() {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			f.step();
		}
	}

	int shootIndex = 0; // shoot count

	/** Shooting */
	public void shootAction() {
		shootIndex++;
		if (shootIndex % 30 == 0) { // send one in 300 milliseconds
			Bullet[] bs = hero.shoot(); //The hero shoots a bullet
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length); // Expansion
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length,
					bs.length); // append array
		}
	}

	/**Bullet and flying object collision detection*/
	public void bangAction() {
		for (int i = 0; i < bullets.length; i++) { //traverse all bullets
			Bullet b = bullets[i];
			bang(b); // collision check between bullet and flying object
		}
	}

	/** Delete cross-border flying objects and bullets */
	public void outOfBoundsAction() {
		int index = 0; // index
		FlyingObject[] flyingLives = new FlyingObject[flyings.length]; // living flying objects
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if (!f.outOfBounds()) {
				flyingLives[index++] = f; // keep it without crossing the boundary
			}
		}
		flyings = Arrays.copyOf(flyingLives, index); // Keep all flying objects that do not cross the boundary

		index = 0; //index is reset to 0
		Bullet[] bulletLives = new Bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBounds()) {
				bulletLives[index++] = b;
			}
		}
		bullets = Arrays.copyOf(bulletLives, index); // Keep the bullets that do not cross the boundary
	}

	/** Check the game is over*/
	public void checkGameOverAction() {
		if (isGameOver()==true) {
			state = GAME_OVER; //change state̬
		}
	}

	/** Check if the game is over*/
	public boolean isGameOver() {
		
		for (int i = 0; i < flyings.length; i++) {
			int index = -1;
			FlyingObject obj = flyings[i];
			if (hero.hit(obj)) { // Check whether the hero machine collides with the flying object
				hero.subtractLife(); // Reduce life
				hero.setDoubleFire(0); // Double Fire Release
				index = i; // record the index of the flying object encountered
			}
			if (index != -1) {
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length - 1];
				flyings[flyings.length - 1] = t; //exchanged with the last flying object

				flyings = Arrays.copyOf(flyings, flyings.length - 1); // delete flying objects
			}
		}
		
		return hero.getLife() <= 0;
	}

	/** Collision check between bullet and flying object*/
	public void bang(Bullet bullet) {
		int index = -1; // The index of the flying object hit
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject obj = flyings[i];
			if (obj.shootBy(bullet)) { //Determine whether to hit
				index = i; // Record the index of the flying object that was hit
				break;
			}
		}
		if (index != -1) { // There is a flying object hit
			FlyingObject one = flyings[index]; // record the flying objects hit

			FlyingObject temp = flyings[index]; // The hit flying object is exchanged with the last flying object
			flyings[index] = flyings[flyings.length - 1];
			flyings[flyings.length - 1] = temp;

			flyings = Arrays.copyOf(flyings, flyings.length - 1); // delete the last flying object (that is, the one that was hit)
			// Check the type of one (additional points for enemies, get rewards)
			if (one instanceof Enemy) { // Check the type, if it is an enemy, add points
				Enemy e = (Enemy) one; // forced type conversion
				score += e.getScore(); // add points
			} else { // If it is a reward, set a reward
				Award a = (Award) one;
				int type = a.getType(); // Get reward type
				switch (type) {
				case Award.DOUBLE_FIRE:
					hero.addDoubleFire(); // set double firepower
					break;
				case Award.LIFE:
					hero.addLife(); // set life plus
					break;
				}
			}
		}
	}

	/**
	 * Randomly generate flying objects
	 * 
	 * @return Flying object
	 */
	public static FlyingObject nextOne() {
		Random random = new Random();
		int type = random.nextInt(20); // [0,20)
		if (type < 4) {
			return new Bee();
		} else {
			return new Airplane();
		}
	}

}
