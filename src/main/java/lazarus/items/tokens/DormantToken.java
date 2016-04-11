/*Imports*/
package lazarus.items.tokens;
import java.util.List;

import lazarus.items.BaseItem;
import lazarus.utils.handlers.KeyboardHandler;
import lazarus.utils.handlers.NBTHandler;
import lazarus.utils.handlers.SoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*Main*/
public class DormantToken extends BaseItem
{	
	/*---------------------------------------- Variables ----------------------------------------*/
	private static String name = "dormant_token";
	
	/*---------------------------------------- Constructor ----------------------------------------*/
	public DormantToken(){super(name, false);}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("§oPotential to be.");
	}
	
	/*---------------------------------------- On item creation ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(!stack.hasTagCompound()){NBTHandler.tokenNBT(stack);}	
	}
	
	/*---------------------------------------- On right click ----------------------------------------*/
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
		return stack;
    }
	
}
