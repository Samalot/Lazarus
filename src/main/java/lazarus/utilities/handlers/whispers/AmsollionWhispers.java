/*Imports*/
package lazarus.utilities.handlers.whispers;

import java.util.Random;

/*Main*/
public class AmsollionWhispers {
	/*---------------------------------------- Whsipers ----------------------------------------*/
	final static String[] possibleWhsipers = {
			"Appear weak when you are strong, and strong when you are weak.", 
			"Man is not what he thinks he is, he is what he hides.",
			"The poor lack much, but the greedy lack more.",
			"Pay no attention to the man behind the curtain!",
			"Greed is eternal.",
			"I will show you whatever you want to see and believe"
			};
	
	/*---------------------------------------- Select random whsiper ----------------------------------------*/
	public static String randomWhsiper(){
		Random random = new Random();
		int index = random.nextInt(possibleWhsipers.length);
		return "Mysterious whispers - \"" + possibleWhsipers[index] + "\"";
	}
}
