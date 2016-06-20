/**---------- Imports ----------**/
package lazarus.utilities.handlers;
import lazarus.items.other.TokenPouch;
import lazarus.items.tokens.AmplifyingToken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**---------- Main ----------**/
public class InventoryHandler 
{
	
	/*Check players inventory for a given item*/
	public static boolean scanInventory(EntityPlayer player, Class<?> itemInput, String key)
	{
		boolean flag = false;
		/*Iterate over inventory*/
		for(int i=0; i<39; i++)
		{
			if(player.inventory.getStackInSlot(i) != null)
			{
				/*Check item*/
				ItemStack currentItemStack = player.inventory.getStackInSlot(i);
				Item currentItem = currentItemStack.getItem();
				if(itemInput == currentItem.getClass()){flag = true;}
				
				/*Pouch with inventory*/
				else if(currentItem instanceof TokenPouch && currentItemStack.hasTagCompound() && currentItemStack.getTagCompound().hasKey("ItemInventory"))
				{
					NBTTagCompound itemInv = currentItemStack.getTagCompound();
					if(itemInv.getBoolean(key)){flag = true;}
				}		
			}
		}
		return flag; 
	}
}
