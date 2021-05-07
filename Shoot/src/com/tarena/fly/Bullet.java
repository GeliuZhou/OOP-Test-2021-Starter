package com.tarena.fly;

/**
 * Bullet: It is a flying object
 */
public class Bullet extends FlyingObject {
	private int speed = 3;  //Speed of movement 
	
	/** Initialization data */
	public Bullet(int x,int y){
		this.x = x;
		this.y = y;
		this.image = ShootGame.bullet;
	}

	/** mobile */
	@Override
	public void step(){   
		y-=speed;
	}

	/** Cross-border processing */
	@Override
	public boolean outOfBounds() {
		return y<-height;
	}

}
