/*Imports*/
package lazarus.items.other;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lazarus.interfaces.inventory.InventoryTokenPouch;
import lazarus.items.BaseItem;
import lazarus.items.tokens.CowardiceToken;
import lazarus.items.tokens.QuellingToken;
import lazarus.items.tokens.WaningToken;
import lazarus.main.LazarusMain;
import lazarus.utilities.events.MainEventHandler;
import lazarus.utilities.handlers.InventoryHandler;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.swing.event.Key;

/*Main*/
public class TokenPouch extends BaseItem
{	
	/*---------------------------------------- Variables ----------------------------------------*/
	private static String name = "token_pouch";
	private List<String> tokenKeys = MainEventHandler.tokens;

	/*---------------------------------------- Constructor ----------------------------------------*/
	public TokenPouch(){super(name);
	this.description =  "Use this to hold your §8§ltokens§r. Any passive effects from tokens in the bag will be active from inside.";
	}
	
	/*---------------------------------------- On right click ----------------------------------------*/
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{	
		if (!world.isRemote) 
		{
			if (!player.isSneaking()) 
			{
				player.openGui(LazarusMain.instance, LazarusMain.GUI_TOKEN_POUCH, player.worldObj, 0, 0, 0);
			} 
			else {new InventoryTokenPouch(player.getHeldItem());}
		}
		return stack;
	}
	
	/*---------------------------------------- Change model ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining)
	{
		return resourseLocationArray.get(MainEventHandler.globalFlag_Token_Pouch_Open);
	}
	
	/*---------------------------------------- Stuff to make inv work! ----------------------------------------*/
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {return 1;}	
	
	/*---------------------------------------- On tick ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{	
		/*Update inventory if open*/
		if(MainEventHandler.globalFlag_Token_Pouch_Open == 1){}
		
		/*Pouch with inventory*/
		if(entity instanceof EntityPlayer && stack.hasTagCompound() && stack.getTagCompound().hasKey("ItemInventory"))
		{
			EntityPlayer player = (EntityPlayer) entity;
			NBTTagCompound itemTags = stack.getTagCompound();
			
			if(detectInventory(stack, "waning")){WaningToken.waningTokenEffect(player);}
			if(detectInventory(stack, "cowardice")){CowardiceToken.scanForSpeed(world, player);}
		}		
	}
	
	/*---------------------------------------- Detect item inventory from NBT ----------------------------------------*/
	public static boolean detectInventory(ItemStack stack, String key)
	{
		boolean result = false;
		//Break if item has no inventory.
		if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("ItemInventory")){return result;}
		//Extract raw inventory from pouch
		NBTTagCompound itemTags = stack.getTagCompound();
		NBTTagList itemInv = itemTags.getTagList("ItemInventory", 10);
		//Itterate over inventory contents.
 		for(int i = 0; i < itemInv.tagCount(); i++)
 		{
			NBTTagCompound slotItem = (NBTTagCompound) itemInv.get(i);
			String itemKey = slotItem.getTag("id").toString();
			itemKey = itemKey.substring(9,itemKey.length()-7);
			if(itemKey.equals(key)){result = true;}
		}
		return result;
	}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("§7§opress §c§oSpace §7§ofor §7§omore §7§oinfo");
	}
}
