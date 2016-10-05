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
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*Main*/
public class AmplifyingToken extends BaseToken
{
	/*---------------------------------------- Variables ----------------------------------------*/
	public static String name = "amplifying_token";

	/*---------------------------------------- Constructor ----------------------------------------*/
	public AmplifyingToken()
	{
		super(name);
		this.amplifiers=Arrays.asList(1.00, 1.25, 1.50, 1.75, 2.00, 2.50);
		this.description = "The great strength infused within this token is unwieldy. Your attacks will be amplified, dealing significantly more damage... but at what cost?";
		this.subDescription.add("Damage dealt multiplied by " + Double.toString(onAttack()));
		this.subDescription.add("Damage recieved multiplied by " + Double.toString(onHurt()));
		this.subDescription.add("Rarity scales the attack multipliers.");
	}

	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("Multiply your damage!");
		/*If item has been initialised*/
		if(stack.hasTagCompound())
		{
			int rarity = stack.getTagCompound().getInteger("rarity");
			String rarityInfo = NBTHandler.getRarityInfo(rarity);
			list.add("Rarity: " + rarityInfo);
		}
		/*Otherwise*/
		list.add("§7§opress §c§oSpace §7§ofor §7§omore §7§oinfo");
	}

	/*---------------------------------------- On item creation ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(!stack.hasTagCompound()){NBTHandler.tokenNBT(stack);}	
	}

	/*---------------------------------------- On attack ----------------------------------------*/
	public static double onAttack(){return 2;}

	/*---------------------------------------- On hurt ----------------------------------------*/
	public static double onHurt(){return 3;}
}

