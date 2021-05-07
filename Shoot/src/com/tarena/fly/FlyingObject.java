package com.tarena.fly;

import java.awt.image.BufferedImage;

/**
 * Flying objects (enemy planes, bees, bullets, hero planes)
 */
public abstract class FlyingObject {
	protected int x;    //x coordinate
	protected int y;    //y coordinate
	protected int width;    //width
	protected int height;   //High
	protected BufferedImage image;   //image

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * Check out of bounds
	 * @return true Out of bounds or not
	 */
	public abstract boolean outOfBounds();
	
	/**
	 * Flyer moves one step
	 */
	public abstract void step();
	
	/**
	 * Check whether the current flying object is hit by a bullet (x, y) (shoot)
	 * @param Bullet Bullet object
	 * @return true means it was hit
	 */
	public boolean shootBy(Bullet bullet){
		int x = bullet.x;  //Bullet abscissa
		int y = bullet.y;  //Bullet ordinate
		return this.x<x && x<this.x+width && this.y<y && y<this.y+height;
	}

}
