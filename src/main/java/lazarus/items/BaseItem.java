/*Imports*/
package lazarus.items;
import java.util.ArrayList;

import lazarus.main.LazarusMain;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/*Main*/
public class BaseItem extends Item
{

	/*---------------------------------------- Variables ----------------------------------------*/
	private String name;
	private boolean isShiny;
	ArrayList<ModelResourceLocation> resourseLocationArray = new ArrayList<ModelResourceLocation>();
	
	/*---------------------------------------- Constructor ----------------------------------------*/
	public BaseItem(String name, boolean isShiny)
	{
		super(); /*Super class*/
		this.name = name; /*Set name*/	
		this.setUnlocalizedName(name); /*Set unlocalized name*/		
		this.setCreativeTab(LazarusMain.tabLazarus); /*Creative tab*/
		this.isShiny = isShiny; /*Set is shiny*/
		this.setMaxStackSize(1); /*Set stack size to 1*/
		this.setMaxDamage(0);  /*Set damage of item to 0*/
	}
	
	/*---------------------------------------- Make it shiny ----------------------------------------*/
	public boolean hasEffect(ItemStack par1ItemStack){return isShiny;}
	
	/*---------------------------------------- Set resource locations ----------------------------------------*/
	public void registerItemModel(ModelResourceLocation resLocList)
	{this.resourseLocationArray.add(resLocList);}
	
}
