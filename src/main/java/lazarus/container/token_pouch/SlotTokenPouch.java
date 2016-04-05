/*Imports*/
package lazarus.container.token_pouch;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import lazarus.items.Amsollions_token;
import lazarus.items.Token_Pouch;
import lazarus.items.Waning_token;

/*Main*/
public class SlotTokenPouch extends Slot
{
	public SlotTokenPouch(IInventory inv, int index, int xPos, int yPos) {
		super(inv, index, xPos, yPos);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		
		boolean answer = 
				(stack.getItem() instanceof Amsollions_token)
				||(stack.getItem() instanceof Waning_token)
				;
		
		
		return answer;
	}
}