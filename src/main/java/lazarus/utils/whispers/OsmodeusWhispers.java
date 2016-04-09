/*Imports*/
package lazarus.utils.whispers;

import java.util.Random;

/*Main*/
public class OsmodeusWhispers {
	/*---------------------------------------- Whsipers ----------------------------------------*/
	final static String[] possibleWhsipers = {
			"No gain without pain dude."
			};
	
	/*---------------------------------------- Select random whsiper ----------------------------------------*/
	public static String randomWhsiper(){
		Random random = new Random();
		int index = random.nextInt(possibleWhsipers.length);
		return "Mysterious whispers - \"" + possibleWhsipers[index] + "\"";
	}
}
