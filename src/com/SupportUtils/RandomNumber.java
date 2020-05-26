package com.SupportUtils;

import java.util.Random;

public class RandomNumber {
	static Random r = new Random();
	
	public static int getInt(int Ubound) {
		return r.nextInt(Ubound);
	}
	
	
	

}
