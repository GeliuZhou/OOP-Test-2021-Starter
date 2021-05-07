package com.tarena.fly;
/**
 * reward
 */
public interface Award {
	int DOUBLE_FIRE = 0;  //Double firepower
	int LIFE = 1;   //1 life
	/** Obtain the reward type (0 or 1 above) */
	int getType();
}
