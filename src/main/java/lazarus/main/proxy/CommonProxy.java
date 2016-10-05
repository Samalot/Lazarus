/**=============== Imports ===============**/
package lazarus.main.proxy;

import lazarus.blocks.infusatron.TileEntityInfusatron;
import lazarus.interfaces.InterfaceLog;
import lazarus.interfaces.container.ContainerInfusatron;
import lazarus.interfaces.container.ContainerTokenPouch;
import lazarus.interfaces.inventory.InventoryTokenPouch;
import lazarus.main.LazarusMain;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

/**=============== Main ===============**/
public class CommonProxy implements IGuiHandler  
{
	/*--------------- Initialise ---------------*/
	public void init(){}

	/*--------------- Register the renders (Items/Blocks) ---------------*/
	public void registerRenders(){}

	/*--------------- Register the renders (Entities) ---------------*/
	public void registerRenderers() {}



	/*Returns a side-appropriate EntityPlayer for use during message handling*/
	public EntityPlayer getPlayerEntity(MessageContext ctx) {return ctx.getServerHandler().playerEntity;}


	/**--------------- Stuff I dont understand ---------------**/

	/*
	 * Returns the current thread based on side during message handling,
	 * used for ensuring that the message is being handled by the main thread
	 */
	public IThreadListener getThreadFromContext(MessageContext ctx) {return ctx.getServerHandler().playerEntity.getServerForPlayer();}

	/*--------------- Armour ---------------*/
	public int addArmor(String string) {return 0;}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		//Check for tile entities at location
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

		//Infusatron
		if(ID == LazarusMain.GUI_INFUSATRON && tileEntity != null)
		{
			ContainerInfusatron infusatronContainer = new ContainerInfusatron(player.inventory,(TileEntityInfusatron)tileEntity);
			InterfaceLog.infusatronLog.put("D"+world.provider.getDimensionId()+"X"+x+"Y"+y+"Z"+z, infusatronContainer);
			return infusatronContainer; 
		}

		//Token Pouch
		if(ID == LazarusMain.GUI_TOKEN_POUCH)
		{
			return new ContainerTokenPouch(player, player.inventory, new InventoryTokenPouch(player.getHeldItem()));
		} 

		//Else return null
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		return null;
	}
}
