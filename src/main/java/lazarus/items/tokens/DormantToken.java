/*Imports*/
package lazarus.items.tokens;
import java.util.Arrays;
import java.util.List;

import lazarus.items.BaseToken;
import lazarus.utilities.handlers.NBTHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*Main*/
public class DormantToken extends BaseToken
{	
	/*---------------------------------------- Variables ----------------------------------------*/
	private static String name = "dormant_token";

	/*---------------------------------------- Constructor ----------------------------------------*/
	public DormantToken()
	{
		super(name);
		this.amplifiers=Arrays.asList(0.00, 0.00, 0.00, 0.00, 0.00, 0.00);
		this.description = "The base to all tokens, "
				+ "\nperform different rituals "
				+ "\nto infuse it with Abyssal "
				+ "\nenergy and transform it "
				+ "\ninto something far greater!";
		/*"-------------------------"*/
	}

	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("§oPotential to be.");
		list.add("§7§opress §c§oSpace §7§ofor §7§omore §7§oinfo");
	}

	/*---------------------------------------- On item creation ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(!stack.hasTagCompound()){NBTHandler.tokenNBT(stack);}	
	}

	/*---------------------------------------- On right click ----------------------------------------*/
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		if (world.isRemote) {

		}
		return stack;
	}

}
