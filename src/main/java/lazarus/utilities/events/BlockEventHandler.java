/**=============== Imports ===============**/
package lazarus.utilities.events;

import lazarus.blocks.infusatron.Infusatron;
import lazarus.main.LazarusBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**=============== Main ===============**/
public class BlockEventHandler {
	/*---------------------------------------- Variables  ----------------------------------------*/
	
	/*---------------------------------------- Block Place  ----------------------------------------*/
	
	
	@SubscribeEvent
	public void getServerPlayer(PlaceEvent event)
	{
		/*
		System.out.println(event);

		if(event.placedBlock.getBlock() instanceof Infusatron)
		{
			Block blockAbove = event.world.getBlockState(event.pos.up(1)).getBlock();
			if(!(blockAbove instanceof BlockAir)){event.setCanceled(true);}
			
		}	*/	
	}

	
	
}
