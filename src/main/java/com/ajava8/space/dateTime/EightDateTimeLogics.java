package com.ajava8.space.dateTime;

import java.util.StringJoiner;

/**
 * EightDateTimeLogics shows new date logics 
 * @author aJava8Space
 *
 */
public class EightDateTimeLogics {

	public void test(){
	   StringJoiner sj = new StringJoiner(":", "[", "]");
	   sj.add("George").add("Sally").add("Fred");  //SJ's add method
	   String desiredString = sj.toString();
	   System.out.println("Desired O/P: "+desiredString);
	   
	}
	
}
