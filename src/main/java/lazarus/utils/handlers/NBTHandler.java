/*Imports*/
package lazarus.utils.handlers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/*Main*/
public class NBTHandler {
	/*Initialise the NBT tags of a token*/
	public static void tokenNBT(ItemStack stack)
	{
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("rarity", getRarity());	
	}
	
	/*Get ratiry from int*/
	public static String getRarityInfo(int index)
	{
		String info = "";
		switch(index)
		{
		case 0: info = "§oBasic";break;
		case 1: info = "§f§oUncommon";break;
		case 2: info = "§1§oRare";break;
		case 3: info = "§5§oEpic";break;
		case 4: info = "§6§oLegendary";break;
		case 5: info = "§4§oAbyssal";break;
		}
		return info;
	}
	
	/*Generate rarity*/
	public static int getRarity(){
		int rarity = 0;
		int randomNum = 1 + (int)(Math.random() * 100); 
		if(randomNum<=51){rarity = 0;}
		else if(randomNum>51 & randomNum <= 76){rarity = 1;}
		else if(randomNum>76 & randomNum <= 88){rarity = 2;}
		else if(randomNum>88 & randomNum <= 94){rarity = 3;}
		else if(randomNum>94 & randomNum <= 98){rarity = 4;}
		else if(randomNum>98 & randomNum <= 100){rarity = 5;}
		return rarity;
	}
	
	/*Get ratiry from int*/
	public static double getRarityBoost(int index)
	{
		double boost = 1.0;
		switch(index)
		{
		case 0: boost = 1.0;break;
		case 1: boost = 1.25;break;
		case 2: boost = 1.5;break;
		case 3: boost = 1.75;break;
		case 4: boost = 2;break;
		case 5: boost = 2.75;break;
		}
		return boost;
	}
}
