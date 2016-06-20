/*Imports*/
package lazarus.utilities.handlers;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/*Main*/
public class NBTHandler {
	
	
	/*Variables*/
	private static List<String> amplifiers = Arrays.asList("§oBasic","§f§oUncommon","§1§oRare", "§5§oEpic","§6§oLegendary","§4§oAbyssal");
	
	/*Initialise the NBT tags of a pearl*/
	public static void pearlNBT(ItemStack stack)
	{
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setBoolean("collapseFlag", true);	
	}
	
	/*Initialise the NBT tags of a token*/
	public static void tokenNBT(ItemStack stack)
	{
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("rarity", getRarity());	
	}
	
	/*Get ratiry from int*/
	public static String getRarityInfo(int index){return amplifiers.get(index);}
	
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
	

}
