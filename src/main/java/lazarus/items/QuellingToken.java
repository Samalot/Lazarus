package lazarus.items;

import java.util.List;

import lazarus.utils.handlers.NBTHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class QuellingToken extends BaseItem{
	/*---------------------------------------- Variables ----------------------------------------*/
	public static String name = "quelling_token";	
	/*---------------------------------------- Constructor ----------------------------------------*/
	public QuellingToken(){
		super(name, true);
		this.setMaxStackSize(1); /*Stack size - max 1*/
	}
	
	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		/*If item has been initialised*/
		if(stack.hasTagCompound())
		{
			int rarity = stack.getTagCompound().getInteger("rarity");
			list.add("Rarity: " + NBTHandler.getRarityInfo(rarity));
		}
		/*Otherwise*/
		else{list.add("§oWont anyone think of the blocks?");}
	}
}
