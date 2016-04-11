//========================================================//
// The following code is taken from CoolAlias.            //
// https://github.com/coolAlias                           //
// His code is available under the GPL3 license, a copy of//
// the license can be found in the doccuments folder.     //
//========================================================//

package lazarus.container.token_pouch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import lazarus.container.AbstractInventory;
import lazarus.items.TokenPouch;
import lazarus.items.tokens.AmplifyingToken;
import lazarus.items.tokens.GildedToken;
import lazarus.items.tokens.WaningToken;

public class InventoryTokenPouch extends AbstractInventory
{
	private String name = "Token Pouch";

	/** The key used to store and retrieve the inventory from NBT */
	private static final String SAVE_KEY = "ItemInventory";

	/** Defining your inventory size this way is handy */
	public static final int INV_SIZE = 10;

	/** Provides NBT Tag Compound to reference */
	private final ItemStack invStack;

	public InventoryTokenPouch(ItemStack stack) {
		inventory = new ItemStack[INV_SIZE];
		this.invStack = stack;
		if (!invStack.hasTagCompound()) {
			invStack.setTagCompound(new NBTTagCompound());
		}
		readFromNBT(invStack.getTagCompound());
	}

	@Override
	public String getCommandSenderName() {return name;}

	@Override
	public boolean hasCustomName() {return name.length() > 0;}

	@Override
	public int getInventoryStackLimit() {return 64;}

	/**
	 * For inventories stored in ItemStacks, it is critical to implement this method
	 * in order to write the inventory to the ItemStack's NBT whenever it changes.
	 */
	@Override
	public void markDirty() {
		for (int i = 0; i < getSizeInventory(); ++i) {
			if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0)
				inventory[i] = null;
		}
		writeToNBT(invStack.getTagCompound());
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		// this will close the inventory if the player tries to move
		// the item that opened it, but you need to return this method
		// from the Container's canInteractWith method
		// an alternative would be to override the slotClick method and
		// prevent the current item slot from being clicked
		return player.getHeldItem() == invStack;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		
		boolean answer = 
				(stack.getItem() instanceof GildedToken)
				||(stack.getItem() instanceof WaningToken)
				||(stack.getItem() instanceof AmplifyingToken)
				;
		
		
		return answer;
	}

	@Override
	protected String getNbtKey() {
		return SAVE_KEY;
	}

	@Override
	public String getName() {return null;}
}