/*Imports*/
package lazarus.utilities.handlers.whispers;

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
		return "Mysterious whispers - \"" + possibleWhsipers[index] + "\"";
	}
}
