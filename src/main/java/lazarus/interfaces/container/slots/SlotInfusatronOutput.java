/**=============== Imports ===============**/
package lazarus.interfaces.container.slots;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

/**=============== Main ===============**/
public class SlotInfusatronOutput  extends Slot
{
	/*--------------- Variables ---------------*/
    private final EntityPlayer thePlayer; // The player that is using the GUI where this slot resides. 
    private int numGrinderOutput;

    /*--------------- Constructor ---------------*/
    public SlotInfusatronOutput(EntityPlayer parPlayer, IInventory parIInventory, int parSlotIndex, int parXDisplayPosition, int parYDisplayPosition)
    {
        super(parIInventory, parSlotIndex, parXDisplayPosition, parYDisplayPosition);
        thePlayer = parPlayer;
    }

    /*--------------- Can't place anything into it ---------------*/
    @Override
    public boolean isItemValid(ItemStack stack){return false;}

    @Override
    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
    {
        onCrafting(stack);
        super.onPickupFromSlot(playerIn, stack);
    }

    @Override
    protected void onCrafting(ItemStack parItemStack, int parAmountGround)
    {
        numGrinderOutput += parAmountGround;
        onCrafting(parItemStack);
    }

    @Override
    protected void onCrafting(ItemStack parItemStack)
    {

    }
}