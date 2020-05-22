//generate a random key that is presumed to be unique to this session
package com.sam.webtasks.basictools;

public class SessionKey {
	private static int len=20;    //default length of key
	private static int radix=62;  //default radix (i.e. set of all possible characters)
	
	private static final char[] CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
	
	public static String Get() {
		char[] id = new char[len];
		
		for (int i = 0; i < len; i++) {
			id[i] = CHARS[(int) (Math.random() * radix)];
		}
		
		return new String(id);
	}
}
