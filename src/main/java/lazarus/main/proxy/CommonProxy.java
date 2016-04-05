/*Imports*/
package lazarus.main.proxy;

import lazarus.container.token_pouch.ContainerTokenPouch;
import lazarus.container.token_pouch.GUI_Token_Pouch;
import lazarus.container.token_pouch.InventoryTokenPouch;
import lazarus.main.LazarusMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/*Main*/
public class CommonProxy implements IGuiHandler{
		/*Register the renders*/
		public void registerRenders(){}
		
		public int addArmor(String string) {return 0;}

		/*Returns a side-appropriate EntityPlayer for use during message handling*/
		public EntityPlayer getPlayerEntity(MessageContext ctx) {return ctx.getServerHandler().playerEntity;}
		
		/**
		 * Returns the current thread based on side during message handling,
		 * used for ensuring that the message is being handled by the main thread
		 */
		public IThreadListener getThreadFromContext(MessageContext ctx) {return ctx.getServerHandler().playerEntity.getServerForPlayer();}

		@Override
		public Object getServerGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
			if (guiId == LazarusMod.GUI_ITEM_INV)  {
				return new ContainerTokenPouch(player, player.inventory, new InventoryTokenPouch(player.getHeldItem()));
			} else {
				return null;
			}
		}

		@Override
		public Object getClientGuiElement(int guiId, EntityPlayer player, World world, int x, int y, int z) {
			if (guiId == LazarusMod.GUI_ITEM_INV)  {
				return new GUI_Token_Pouch(player, player.inventory, new InventoryTokenPouch(player.getHeldItem()));
			} else {
				return null;
			}
		}
		
		
		
		
		
}
