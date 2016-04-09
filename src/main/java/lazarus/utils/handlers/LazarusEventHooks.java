package lazarus.utils.handlers;

import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;

public class LazarusEventHooks {
	@EventHandler
	public void onEntityUpdate(LivingUpdateEvent event) 
	{
		if (event.entityLiving.isPotionActive(PotionHandler.collapse)) 
		{
			System.out.println("HEREEE");
		}
		
		if (event.entityLiving.getActivePotionEffect(PotionHandler.collapse).getDuration()==0) 
		{
			event.entityLiving.removePotionEffect(PotionHandler.collapse.id);
			return;
		}
		
	}	
}
