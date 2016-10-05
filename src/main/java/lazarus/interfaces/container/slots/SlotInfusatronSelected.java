/**=============== Imports ===============**/
package lazarus.interfaces.container.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**=============== Main ===============**/
public class SlotInfusatronSelected extends Slot{
	
	/*--------------- Variables ---------------*/
    public final EntityPlayer thePlayer;  
	
	/*--------------- Constructor ---------------*/
	public SlotInfusatronSelected(EntityPlayer parPlayer, IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		thePlayer = parPlayer;
	}
	
	/*--------------- Can't place anything into it ---------------*/
    @Override
    public boolean isItemValid(ItemStack stack){return false;}
    
    /*--------------- Decrease stack size ---------------*/
    @Override
    public ItemStack decrStackSize(int parAmount)
    {return null;}

}
