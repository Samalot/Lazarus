/*Imports*/
package lazarus.items.other;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lazarus.guis.container.token_pouch.InventoryTokenPouch;
import lazarus.items.BaseItem;
import lazarus.items.tokens.CowardiceToken;
import lazarus.items.tokens.WaningToken;
import lazarus.main.LazarusMain;
import lazarus.utilities.events.MainEventHandler;
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
	this.description = 
			   "Use this to hold your §8§ltokens§r."
	       + "\nAny passive effects from"
	       + "\ntokens in the bag will"
	       + "\nbe active from inside.";
             /*"-------------------------§§§"*/
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
		System.out.println(MainEventHandler.globalFlag_Token_Pouch_Open);
		if(MainEventHandler.globalFlag_Token_Pouch_Open == 1){detectInventory(stack);}
		
		/*Only continue if an inventory exists*/
		if(stack.hasTagCompound()){
			/*One off, initialise NBT Tags*/
			NBTTagCompound stackTags = stack.getTagCompound();
			if(!stackTags.hasKey("waning")){for(String key : tokenKeys){stackTags.setBoolean(key, false);}}
		}
		
		/*Pouch with inventory*/
		EntityPlayer player = null;
		boolean flag = false;
		if(entity instanceof EntityPlayer){player = (EntityPlayer) entity; flag = true;}
		if(flag)
		{
			if(stack.hasTagCompound() && stack.getTagCompound().hasKey("ItemInventory"))
			{
				NBTTagList itemInv = stack.getTagCompound().getTagList("ItemInventory", 10);
				int invCount = itemInv.tagCount();
				if(invCount>0){
					for (int j = 0; j < invCount; j++){
						NBTTagCompound slotItem = (NBTTagCompound) itemInv.get(j);
						String itemKey = slotItem.getTag("id").toString();
						itemKey = itemKey.substring(9,itemKey.length()-1);
						if(itemKey.equals("cowardice_token")){CowardiceToken.tick(player, world);}
						if(itemKey.equals("waning_token")){WaningToken.waningTokenEffect(player);}
					}
				}
			}		
		}		
	}
	
	/*---------------------------------------- Detect item inventory from NBT ----------------------------------------*/
	public ArrayList<String> detectInventory(ItemStack stack)
	{
		ArrayList<String> items =new ArrayList<String>();	
		if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("ItemInventory")){return null;}
		else{
			NBTTagList itemInv = stack.getTagCompound().getTagList("ItemInventory", 10);
			int invCount = itemInv.tagCount();
			if(invCount>0){
				for (int i = 0; i < invCount; i++){
					NBTTagCompound slotItem = (NBTTagCompound) itemInv.get(i);
					String itemKey = slotItem.getTag("id").toString();
					items.add(itemKey.substring(9, itemKey.length()-7));
				}
			}
		}
		
		/*Update keys*/
		for(String key : tokenKeys)
		{
			if(stack.hasTagCompound() && stack.getTagCompound().hasKey(key))
			{
				if(items.contains(key)){stack.getTagCompound().setBoolean(key, true);}
				else{stack.getTagCompound().setBoolean(key, false);}
			}
		}	
		System.out.println(items);
		return items;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*---------------------------------------- Format inventory array into tooltip items ----------------------------------------*/
	public static Map<String, Integer> formatTooltip(ArrayList<String> inputList)
	{
		Map<String, Integer> tooltipInfo = new HashMap<String, Integer>();
		for(String element : inputList)
		{
			String colourCode = tokenToColour(element);
			String configuredElement = "§" + colourCode + "§o" + element.substring(0, 1).toUpperCase() + element.substring(1) + "§" + colourCode + "§o" + "Token";
			if(tooltipInfo.containsKey(configuredElement)){tooltipInfo.put(configuredElement, tooltipInfo.get(configuredElement)+1);}
			else{tooltipInfo.put(configuredElement, 1);}
		}
		return tooltipInfo;
	}
	
	/*---------------------------------------- Return colour of token ----------------------------------------*/
	public static String tokenToColour(String item)
	{
		String colourCode = "7";
		if(item.equals("waning")){colourCode = "5";}
		if(item.equals("gilded")){colourCode = "6";}
		if(item.equals("amplifying")){colourCode = "7";}
		if(item.equals("cowardice")){colourCode = "3";}
		if(item.equals("quelling")){colourCode = "c";}
		if(item.equals("dormant")){colourCode = "8";}
		return colourCode;
	}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		if(!stack.hasTagCompound() || (stack.getTagCompound().getTagList("ItemInventory", 10).tagCount()<1))
		{list.add("§oEMPTY");list.add("§7§opress §c§oSpace §7§ofor §7§omore §7§oinfo");}
		else
		{
			ArrayList<String> items = detectInventory(stack);
			Map<String, Integer> formatedItems = formatTooltip(items);
			for(String element : formatedItems.keySet()){
				if(formatedItems.get(element) == 1)
				{list.add(element);}
				else
				{list.add(element + " §" + element.charAt(3) + "§o" + "X" + Integer.toString(formatedItems.get(element)));}
			}
			list.add("§7§opress §c§oSpace §7§ofor §7§omore §7§oinfo");
		}		
	}
	
	
}
