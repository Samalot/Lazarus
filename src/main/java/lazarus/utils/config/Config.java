/*Imports*/
package lazarus.utils.config;
import lazarus.main.LazarusMain;
import net.minecraftforge.common.config.Configuration;

/*Main*/
public class Config 
{
	/*---------------------------------------- Create configs ----------------------------------------*/
	public static void load(Configuration LazarusConfig)
	{
		//int amsollionXPfromGold = LazarusConfig.getInt("amsollionXPfromGold", Configuration.CATEGORY_GENERAL, 6, 0, Integer.MAX_VALUE, "How much XP is given per gold ingot from Amsollion's Token");
		LazarusConfig.save();
	}
}
