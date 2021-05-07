package com.tarena.fly;
import java.awt.image.BufferedImage;

/**
 * Hero aircraft: is a flying object
 */
public class Hero extends FlyingObject{
	
	private BufferedImage[] images = {};  //Hero aircraft pictures
	private int index = 0;                //Hero aircraft Picture Switch Index
	
	private int doubleFire;   //Double firepower
	private int life;   //Life
	
	/** Initialization data */
	public Hero(){
		life = 3;   //Initial 3 lives
		doubleFire = 0;   //Initial firepower is 0
		images = new BufferedImage[]{ShootGame.hero0, ShootGame.hero1}; //Hero Aircraft Picture Array
		image = ShootGame.hero0;   //Initially a hero0 picture
		width = image.getWidth();
		height = image.getHeight();
		x = 150;
		y = 400;
	}
	
	/** Get double firepower*/
	public int isDoubleFire() {
		return doubleFire;
	}

	/** Set double firepower */
	public void setDoubleFire(int doubleFire) {
		this.doubleFire = doubleFire;
	}
	
	/** Increase firepower */
	public void addDoubleFire(){
		doubleFire = 40;
	}
	
	/** addLife */
	public void addLife(){  //addLife
		life++;
	}
	
	/** subtractLife */
	public void subtractLife(){   //subtractLife
		life--;
	}
	
	/** Get life */
	public int getLife(){
		return life;
	}
	
	/** The current object moved a bit, relative distance, x,y mouse position  */
	public void moveTo(int x,int y){   
		this.x = x - width/2;
		this.y = y - height/2;
	}

	/** Cross-border processing */
	@Override
	public boolean outOfBounds() {
		return false;  
	}

	/** Fire a bullet */
	public Bullet[] shoot(){   
		int xStep = width/4;     
		int yStep = 20;  
		if(doubleFire>0){  
			Bullet[] bullets = new Bullet[2];
			bullets[0] = new Bullet(x+xStep,y-yStep);  //y-yStep(The position of the bullet from the aircraft)
			bullets[1] = new Bullet(x+3*xStep,y-yStep);
			return bullets;
		}else{      //Single firepower
			Bullet[] bullets = new Bullet[1];
			bullets[0] = new Bullet(x+2*xStep,y-yStep);  
			return bullets;
		}
	}

	/** mobile */
	@Override
	public void step() {
		if(images.length>0){
			image = images[index++/10%images.length];  //Switch picture hero0, hero1
		}
	}
	
	/** Collision algorithm */
	public boolean hit(FlyingObject other){
		
		int x1 = other.x - this.width/2;                 //x coordinate minimum distance
		int x2 = other.x + this.width/2 + other.width;   //Maximum distance of x coordinate
		int y1 = other.y - this.height/2;                //y coordinate minimum distance
		int y2 = other.y + this.height/2 + other.height; //y coordinate maximum distance
	
		int herox = this.x + this.width/2;               //Hero machine x coordinate center point distance
		int heroy = this.y + this.height/2;              //Hero machine y coordinate center point distance
		
		return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;   //Within the range of the collision
	}
	
}










