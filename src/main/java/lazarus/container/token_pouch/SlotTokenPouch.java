//========================================================//
// The following code is taken from CoolAlias.            //
// https://github.com/coolAlias                           //
// His code is available under the GPL3 license, a copy of//
// the license can be found in the doccuments folder.     //
//========================================================//

/*Imports*/
package lazarus.container.token_pouch;
import lazarus.items.tokens.AmplifyingToken;
import lazarus.items.tokens.GildedToken;
import lazarus.items.tokens.WaningToken;
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