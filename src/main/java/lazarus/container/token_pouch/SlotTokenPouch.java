/*Imports*/
package lazarus.container.token_pouch;
import lazarus.items.AmplifyingToken;
import lazarus.items.GildedToken;
import lazarus.items.WaningToken;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/*Main*/
public class SlotTokenPouch extends Slot
{
	public SlotTokenPouch(IInventory inv, int index, int xPos, int yPos) {
		super(inv, index, xPos, yPos);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		
		boolean answer = 
				(stack.getItem() instanceof GildedToken)
				||(stack.getItem() instanceof WaningToken)
				||(stack.getItem() instanceof AmplifyingToken)
				;
		
		
		return answer;
	}
}