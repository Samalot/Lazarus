//========================================================//
// The following code is taken from CoolAlias.            //
// https://github.com/coolAlias                           //
// His code is available under the GPL3 license, a copy of//
// the license can be found in the doccuments folder.     //
//========================================================//

/*Imports*/
package lazarus.interfaces.container.slots;
import lazarus.items.tokens.AmplifyingToken;
import lazarus.items.tokens.CowardiceToken;
import lazarus.items.tokens.DormantToken;
import lazarus.items.tokens.GildedToken;
import lazarus.items.tokens.QuellingToken;
import lazarus.items.tokens.WaningToken;
import lazarus.utilities.events.MainEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

/*Main*/
public class SlotTokenPouch extends Slot
{
	public SlotTokenPouch(IInventory inv, int index, int xPos, int yPos) {
		super(inv, index, xPos, yPos);
	}

	@Override
	public boolean isItemValid(ItemStack stack) 
	{
		boolean answer =  
				(stack.getItem() instanceof GildedToken)
				||(stack.getItem() instanceof WaningToken)
				||(stack.getItem() instanceof AmplifyingToken)
				||(stack.getItem() instanceof CowardiceToken)
				||(stack.getItem() instanceof DormantToken)
				||(stack.getItem() instanceof QuellingToken)
				;
				
		for(int i = 0; i<this.inventory.getSizeInventory(); i++)
		{
			if(this.inventory.getStackInSlot(i) != null &&this.inventory.getStackInSlot(i).getItem() == stack.getItem())
			{
				IChatComponent whisper = new ChatComponentText("§5§oFoolish §5§ohuman, §5§oa §5§opouch §5§ocan §5§oonly §5§otake §5§oone §5§oof §5§oeach §5§otoken.");
				if(System.nanoTime() - MainEventHandler.lastDuplicateTokenFlag > 5000000000L)
				{
					Minecraft.getMinecraft().thePlayer.addChatMessage(whisper);MainEventHandler.lastDuplicateTokenFlag = System.nanoTime();
				}

				answer = false;
			}
		} 
		return answer;
	}
}