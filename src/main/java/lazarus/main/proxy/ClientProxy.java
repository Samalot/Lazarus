/*Imports*/
package lazarus.main.proxy;

import lazarus.guis.ItemInfoScreen;
import lazarus.guis.container.token_pouch.GuiTokenPouch;
import lazarus.guis.container.token_pouch.InventoryTokenPouch;
import lazarus.main.LazarusItems;
import lazarus.main.LazarusMain;
import lazarus.potions.Collapse;
import lazarus.utilities.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/*Main*/
public class ClientProxy extends CommonProxy{
	
	/*Register the renders*/
	@Override
	public void registerRenders(){
		LazarusItems.registerRenders();
	}

	@Override
	public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
		if (guiId == LazarusMain.GUI_TOKEN_POUCH)  {return new GuiTokenPouch(player, player.inventory, new InventoryTokenPouch(player.getHeldItem()));} 
		if (guiId == LazarusMain.GUI_ITEM_INFO){return new ItemInfoScreen();}
		
		
		else {return null;}
	}
	
}
