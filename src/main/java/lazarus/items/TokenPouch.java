/*Imports*/
package lazarus.items;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lazarus.container.token_pouch.InventoryTokenPouch;
import lazarus.main.LazarusMain;
import lazarus.utils.handlers.MainEventHandler;
import lazarus.utils.handlers.TokenHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*Main*/
public class TokenPouch extends BaseItem
{	
	/*---------------------------------------- Variables ----------------------------------------*/
	private static String name = "token_pouch";
	private List<String> tokenKeys = Arrays.asList("waning", "gilded","amplifying");

	
	/*---------------------------------------- Constructor ----------------------------------------*/
	public TokenPouch()
	{	
		super(name, false); /*Super class*/	
		this.setMaxStackSize(1); /*Stack size - max 1*/
		this.setMaxDamage(0); 
	}
	
	
	
	/*---------------------------------------- On right click ----------------------------------------*/
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
	{	
		if (!par2World.isRemote) 
		{
			if (!player.isSneaking()) 
			{
				player.openGui(LazarusMain.instance, LazarusMain.GUI_ITEM_INV, player.worldObj, 0, 0, 0);
				System.out.println(par1ItemStack.getTagCompound());
	
			} 
			else {new InventoryTokenPouch(player.getHeldItem());}
		}
		return par1ItemStack;
	}
	
	/*---------------------------------------- Change model ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public ModelResourceLocation getModel(ItemStack stack, EntityPlayer player, int useRemaining){
		return resourseLocationArray.get(MainEventHandler.globalFlag_Token_Pouch_Open);
	}
	
	/*---------------------------------------- Stuff to make inv work! ----------------------------------------*/
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {return 1;}	
	
	
	/*---------------------------------------- On tick ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		
		/*Update inventory if open*/
		if(MainEventHandler.globalFlag_Token_Pouch_Open == 1)
		{
			detectInventory(stack);	
			stack.getTooltip((EntityPlayer) entity, false);
		}
		/*Only continue if an inventory exists*/
		if(stack.hasTagCompound()){
			/*One off, initialise NBT Tags*/
			NBTTagCompound stackTags = stack.getTagCompound();
			if(!stackTags.hasKey("waning")){
				stackTags.setBoolean("gilded", false);
				stackTags.setBoolean("waning", false);
				stackTags.setBoolean("amplifying", false);
			}
			/*Check active tokens and apply any effects.*/
			else
			{
				if(stackTags.getBoolean("waning")){TokenHandler.waningToken(entity);}
			}
		}
		
		/*Update inventory*/
		
	}
	
	/*---------------------------------------- Detect item inventory from NBT ----------------------------------------*/
	public ArrayList detectInventory(ItemStack stack)
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
			else{}
		}
		
		/*Update keys*/
		for(String key : tokenKeys)
		{
			if(stack.hasTagCompound() && stack.getTagCompound().hasKey(key))
			{
				if(items.contains(key)){
					stack.getTagCompound().setBoolean(key, true);}
				else{stack.getTagCompound().setBoolean(key, false);}
			}
		}				
		return items;
	}
	
	
	/*---------------------------------------- Format inventory array into tooltip items ----------------------------------------*/
	public static Map<String, Integer> formatTooltip(ArrayList<String> inputList)
	{
		Map<String, Integer> tooltipInfo = new HashMap<String, Integer>();
		for(String element : inputList)
		{
			int colourCode = tokenToColour(element);
			String configuredElement = "§" + Integer.toString(colourCode) + "§o" + element.substring(0, 1).toUpperCase() + element.substring(1) + "§" + Integer.toString(colourCode) + "§o" + "Token";
			if(tooltipInfo.containsKey(configuredElement)){tooltipInfo.put(configuredElement, tooltipInfo.get(configuredElement)+1);}
			else{tooltipInfo.put(configuredElement, 1);}
		}
		System.out.println(tooltipInfo);
		return tooltipInfo;
	}
	
	/*---------------------------------------- Return colour of token ----------------------------------------*/
	public static int tokenToColour(String item)
	{
		int colourCode = 0;
		if(item.equals("waning")){colourCode = 5;}
		if(item.equals("gilded")){colourCode = 6;}
		if(item.equals("amplifying")){colourCode = 7;}
		return colourCode;
	}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		if(!stack.hasTagCompound() || (stack.getTagCompound().getTagList("ItemInventory", 10).tagCount()<1))
		{list.add("§oEMPTY");}
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
		}		
	}
	
	
}
