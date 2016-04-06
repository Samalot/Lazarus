/*Imports*/
package lazarus.items;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lazarus.container.token_pouch.InventoryTokenPouch;
import lazarus.main.LazarusMod;
import lazarus.utils.handlers.LazarusEventHandler;
import lazarus.utils.handlers.TokenHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
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
	List<String> tokenKeys = Arrays.asList("waning", "gilded","amplifying");
	
	/*---------------------------------------- Constructor ----------------------------------------*/
	public TokenPouch()
	{	
		super(name, false); /*Super class*/	
		this.setMaxStackSize(1); /*Stack size - max 1*/
		this.setMaxDamage(0); 
	}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{list.add("§oStore Your Tokens!");}
	
	/*---------------------------------------- On right click ----------------------------------------*/
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player)
	{	
		if (!par2World.isRemote) 
		{
			if (!player.isSneaking()) 
			{
				player.openGui(LazarusMod.instance, LazarusMod.GUI_ITEM_INV, player.worldObj, 0, 0, 0);
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
		return resourseLocationArray.get(LazarusEventHandler.globalFlag_Token_Pouch_Open);
	}
	
	
	/*---------------------------------------- Stuff to make inv work! ----------------------------------------*/
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {return 1;}	
	
	
	/*---------------------------------------- On tick ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{

		if(stack.hasTagCompound()){
			NBTTagCompound stackTags = stack.getTagCompound();
			if(!stackTags.hasKey("waning")){
				stackTags.setBoolean("gilded", false);
				stackTags.setBoolean("waning", false);
				stackTags.setBoolean("amplifying", false);
			}
			else
			{
				if(stackTags.getBoolean("waning"))
				{
					TokenHandler.waningToken(entity);
				}
			}
			
			
		}
		
		/*Update inventory*/
		detectInventory(stack);
	}
	
	/*---------------------------------------- Detect item inventory from NBT ----------------------------------------*/
	public int detectInventory(ItemStack stack)
	{
		ArrayList<String> items =new ArrayList<String>();
		
		if(!stack.hasTagCompound() || !stack.getTagCompound().hasKey("ItemInventory")){return 0;}

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
		
		return 0;
	}
	
}
