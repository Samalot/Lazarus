/*Imports*/
package lazarus.items;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	public ArrayList<ModelResourceLocation> resourseLocationArray = new ArrayList<ModelResourceLocation>();
	public ArrayList<String> subDescription = new ArrayList<String>();
	public String description = "This is temp.";
	
	/*---------------------------------------- Constructor ----------------------------------------*/
	public BaseItem(String name)
	{
		super(); /*Super class*/
		this.name = name; /*Set name*/	
		this.setUnlocalizedName(name); /*Set unlocalized name*/		
		this.setCreativeTab(LazarusMain.tabLazarus); /*Creative tab*/
		this.setMaxStackSize(1); /*Set stack size to 1*/
		this.setMaxDamage(0);  /*Set damage of item to 0*/
	}
	
	/*---------------------------------------- Set resource locations ----------------------------------------*/
	public void registerItemModel(ModelResourceLocation resLocList)
	{this.resourseLocationArray.add(resLocList);}
	
	/*---------------------------------------- Get description ----------------------------------------*/
	public String getDescription()
	{return description;}
	
	/*---------------------------------------- Get name ----------------------------------------*/
	public String getName()
	{return name;}
		
}
