package com.tarena.fly;

import java.util.Random;

/**
 * Enemy aircraft: It is a flying object and an enemy
 */
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 3;  //Move steps
	
	/** Initialization data */
	public Airplane(){
		this.image = ShootGame.airplane;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;          
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width);
	}
	
	/** Get points */
	@Override
	public int getScore() {  
		return 5;
	}

	/** //Cross-border processing */
	@Override
	public 	boolean outOfBounds() {   
		return y>ShootGame.HEIGHT;
	}

	/** mobile */
	@Override
	public void step() {   
		y += speed;
	}

}

