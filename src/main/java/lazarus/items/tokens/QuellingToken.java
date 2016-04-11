package lazarus.items.tokens;

import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.round;

import java.util.List;

import lazarus.items.BaseItem;
import lazarus.main.LazarusItems;
import lazarus.utils.handlers.NBTHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class QuellingToken extends BaseItem{
	/*---------------------------------------- Variables ----------------------------------------*/
	public static String name = "quelling_token";	
	/*---------------------------------------- Constructor ----------------------------------------*/
	public QuellingToken(){super(name, true);}
	
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
	
	/*---------------------------------------- On item creation ----------------------------------------*/
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
	{
		if(!stack.hasTagCompound()){NBTHandler.tokenNBT(stack);}	
	}
	
	/*---------------------------------------- On explosion ----------------------------------------*/
	public static void onExplosion(ExplosionEvent event, EntityPlayer player)
	{
		DamageSource quellingExplosion = new DamageSource("quellingExplosion");
		Vec3 explosionPos = event.explosion.getPosition();
		double x = round(abs(player.getPosition().getX() - explosionPos.xCoord));
		double y = round(abs(player.getPosition().getY() - explosionPos.yCoord));
		double z = round(abs(player.getPosition().getZ() - explosionPos.zCoord));
		if(player.inventory.hasItem(LazarusItems.quelling_token)
				&& x <= 5
				&& y <= 5
				&& z <= 5)
		{
			AxisAlignedBB blocks = new AxisAlignedBB(-5, -5, -5, 5, 5, 5);
			float damage = (event.world.getBlockDensity(explosionPos, blocks) * 20);
			player.attackEntityFrom(quellingExplosion, (float)floor(damage));
			event.explosion.doExplosionB(true);
			event.setCanceled(true);
			}	
	}
}
