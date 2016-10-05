package lazarus.items.tokens;

import static java.lang.Math.abs;
import static java.lang.Math.floor;
import static java.lang.Math.round;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lazarus.blocks.infusatron.RitualElement;
import lazarus.items.BaseToken;
import lazarus.main.LazarusItems;
import lazarus.utilities.handlers.NBTHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class QuellingToken extends BaseToken
{
	/*---------------------------------------- Variables ----------------------------------------*/
	public static String name = "quelling_token";
	public ArrayList<RitualElement> ritualElements = new ArrayList<RitualElement>();
	/*---------------------------------------- Constructor ----------------------------------------*/
	public QuellingToken()
	{
		super(name);
		this.amplifiers=Arrays.asList(1.00, 1.25, 1.50, 1.75, 2.00, 2.50);
		this.description = "Sacrifice yourself for your craft becasue when you have returned to the dust, it shall live on. Your destruction will prevent the real destruction of that around you.";
		this.subDescription.add("Explosions within a 5 block distance will do no block damage");
		this.subDescription.add("Explosions with a 5 block distance will deal extra damage to the beholder.");
		this.subDescription.add("Rarity controls the block distance for detection.");
		ritual();
	}

	/*---------------------------------------- Tooltip ----------------------------------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add("Explosion Protection");
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

	/*---------------------------------------- On explosion ----------------------------------------*/
	public static void onExplosion(ExplosionEvent event, EntityPlayer player)
	{
		DamageSource quellingExplosion = new DamageSource("quellingExplosion");
		Vec3 explosionPos = event.explosion.getPosition();
		double x = round(abs(player.getPosition().getX() - explosionPos.xCoord));
		double y = round(abs(player.getPosition().getY() - explosionPos.yCoord));
		double z = round(abs(player.getPosition().getZ() - explosionPos.zCoord));
		if(x <= 5 && y <= 5 && z <= 5)
		{
			AxisAlignedBB blocks = new AxisAlignedBB(-5, -5, -5, 5, 5, 5);
			float damage = (event.world.getBlockDensity(explosionPos, blocks) * 20);
			player.attackEntityFrom(quellingExplosion, 10);
			event.explosion.doExplosionB(true);
			event.setCanceled(true);
		}	
	}
	
	/*---------------------------------------- Create ritual ----------------------------------------*/
	public void ritual()
	{
		ritualElements.add(new RitualElement("Have 30 experience levels."));
	}
}
