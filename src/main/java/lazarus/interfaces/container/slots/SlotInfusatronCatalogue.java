/**=============== Imports ===============**/
package lazarus.interfaces.container.slots;

import lazarus.blocks.infusatron.TileEntityInfusatron;
import lazarus.interfaces.InterfaceLog;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**=============== Main ===============**/
public class SlotInfusatronCatalogue extends Slot{

	/*--------------- Variables ---------------*/
	public final EntityPlayer thePlayer;  
	private TileEntityInfusatron tileInfusatron;

	/*--------------- Constructor ---------------*/
	public SlotInfusatronCatalogue(EntityPlayer parPlayer, IInventory inventoryIn, int index, int xPosition, int yPosition, TileEntityInfusatron tileInfusatron) {
		super(inventoryIn, index, xPosition, yPosition);
		thePlayer = parPlayer;
		this.tileInfusatron = tileInfusatron;
	}

	/*--------------- Can't place anything into it ---------------*/
	@Override
	public boolean isItemValid(ItemStack stack){return false;}

	/*--------------- Decrease stack size ---------------*/
	@Override
	public ItemStack decrStackSize(int parAmount)
	{return null;}


	public boolean getActivated()
	{
		if(InterfaceLog.infusatronModeClient.containsKey(tileInfusatron)){return InterfaceLog.infusatronModeClient.get(tileInfusatron);}
		else{return true;}
	}
	
	
	/*--------------- Slot texture ---------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public String getSlotTexture()
	{
		if(getActivated()){return backgroundName;}
		else{return null;}
	}

	/*--------------- Can hover ---------------*/
	@Override
	@SideOnly(Side.CLIENT)
	public boolean canBeHovered()
	{
		return getActivated();
	}

}
