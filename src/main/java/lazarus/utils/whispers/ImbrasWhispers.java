/*Imports*/
package lazarus.utils.whispers;

import java.util.Random;

/*Main*/
public class ImbrasWhispers {
	/*---------------------------------------- Whsipers ----------------------------------------*/
	final static String[] possibleWhsipers = {
			"TOP KEK M8."
			};
	
	/*---------------------------------------- Select random whsiper ----------------------------------------*/
	public static String randomWhsiper(){
		Random random = new Random();
		int index = random.nextInt(possibleWhsipers.length);
		return "mysterious whispers - \"" + possibleWhsipers[index] + "\"";
	}
}
