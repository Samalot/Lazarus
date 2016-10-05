/**=============== Imports ===============**/
package lazarus.main.proxy;

import lazarus.blocks.infusatron.TileEntityInfusatron;
import lazarus.entity.EntityOsmodeus;
import lazarus.entity.model.ModelOsmodeus;
import lazarus.entity.render.RenderOsmodeus;
import lazarus.interfaces.InterfaceLog;
import lazarus.interfaces.container.ContainerInfusatron;
import lazarus.interfaces.guis.GuiInfusatron;
import lazarus.interfaces.guis.GuiTokenPouch;
import lazarus.interfaces.guis.GuiItemInfoScreen;
import lazarus.interfaces.inventory.InventoryTokenPouch;
import lazarus.main.LazarusBlocks;
import lazarus.main.LazarusItems;
import lazarus.main.LazarusMain;
import lazarus.main.LazarusTileEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**=============== Main ===============**/
public class ClientProxy extends CommonProxy
{
	/*--------------- Initialise ---------------*/
	@Override
	public void init(){}

	/*--------------- Register the renders (Items/Blocks) ---------------*/
	@Override
	public void registerRenders(){
		LazarusItems.registerRenders();
		LazarusBlocks.registerRenders();
	}

	/*--------------- Register the renders (Entities) ---------------*/
	@Override
	public void registerRenderers()
	{
		float shadowSize = 0.0F;
		RenderManager rm = Minecraft.getMinecraft().getRenderManager();
		RenderingRegistry.registerEntityRenderingHandler(EntityOsmodeus.class, new RenderOsmodeus(rm, new ModelOsmodeus(), shadowSize));
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		//Check for tile entities at location
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

		//Infusatron
		if(ID == LazarusMain.GUI_INFUSATRON && tileEntity != null)
		{
			InterfaceLog.infusatronModeClient.put((TileEntityInfusatron)tileEntity, true);
			GuiInfusatron infusatronGUI = new GuiInfusatron(player.inventory,(TileEntityInfusatron)tileEntity);
			InterfaceLog.infusatronGUILog.put("D"+world.provider.getDimensionId()+"X"+x+"Y"+y+"Z"+z, infusatronGUI);
			return infusatronGUI; 
		}

		//Token Pouch
		else if (ID == LazarusMain.GUI_TOKEN_POUCH)
		{
			return new GuiTokenPouch(player, player.inventory, new InventoryTokenPouch(player.getHeldItem()));
		} 

		//Item info screen
		else if (ID == LazarusMain.GUI_ITEM_INFO)
		{
			return new GuiItemInfoScreen();
		}

		//Else return null
		return null;
	}

}
