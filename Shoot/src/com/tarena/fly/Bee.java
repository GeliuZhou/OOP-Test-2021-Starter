package com.tarena.fly;

import java.util.Random;

/** bee */
public class Bee extends FlyingObject implements Award{
	private int xSpeed = 1;   //x coordinate moving speed
	private int ySpeed = 2;   //y coordinate moving speed
	private int awardType;    //Reward type
	
	/** Initialization data */
	public Bee(){
		this.image = ShootGame.bee;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width);
		awardType = rand.nextInt(2);   //Reward at initialization
	}
	
	/** Reward type */
	public int getType(){
		return awardType;
	}

	/** Cross-border processing */
	@Override
	public boolean outOfBounds() {
		return y>ShootGame.HEIGHT;
	}

	/** Move, can fly diagonally */
	@Override
	public void step() {      
		x += xSpeed;
		y += ySpeed;
		if(x > ShootGame.WIDTH-width){  
			xSpeed = -1;
		}
		if(x < 0){
			xSpeed = 1;
		}
	}
}