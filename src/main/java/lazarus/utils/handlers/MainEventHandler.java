/*Imports*/
package lazarus.utils.handlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import lazarus.items.tokens.AmplifyingToken;
import lazarus.items.tokens.CowardiceToken;
import lazarus.items.tokens.QuellingToken;
import lazarus.main.LazarusItems;
import lazarus.potions.Collapse;
import lazarus.utils.whispers.AmsollionWhispers;
import lazarus.utils.whispers.ImbrasWhispers;
import lazarus.utils.whispers.OsmodeusWhispers;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*Main*/
public class MainEventHandler {
	/*---------------------------------------- Global variables ----------------------------------------*/
	public static Map currentPlayer = new HashMap<String, EntityPlayer>();
	public static int globalFlag_Token_Pouch_Open = 0;
	public EntityPlayer mpPlayer;
	
	public static ArrayList<Entity> enderPearlArray = new ArrayList<Entity>();
	private int testInt = 0;
	
	/*---------------------------------------- Listen for guiOpens ----------------------------------------*/
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void openGui(GuiOpenEvent event)
	{	
			/*No GUI*/
			if(event.gui == null)
			{globalFlag_Token_Pouch_Open = 0;}
			/*Token Pouch opened*/
			else if(event.gui.toString().contains("lazarus.container.token_pouch.GuiTokenPouch"))
			{globalFlag_Token_Pouch_Open = 1;}		
	}
	
	/*---------------------------------------- Listen for item pickup ----------------------------------------*/
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onItemPickup(EntityItemPickupEvent event){
		String whisperTextTemp = "";
		boolean trigger = false;
		if(event.item.getEntityItem().getItem() == LazarusItems.gilded_token){whisperTextTemp = AmsollionWhispers.randomWhsiper();trigger = true;}
		if(event.item.getEntityItem().getItem() == LazarusItems.waning_token){whisperTextTemp = OsmodeusWhispers.randomWhsiper();trigger = true;}
		if(event.item.getEntityItem().getItem() == LazarusItems.amplifying_token){whisperTextTemp = ImbrasWhispers.randomWhsiper();trigger = true;}
		if(trigger)
		{
			String whisperText = "";
			String[] whisperTextSplit = whisperTextTemp.split(" ");
			for(String element : whisperTextSplit){whisperText += "§8§o";whisperText += element;whisperText+=" ";}
			IChatComponent whisper = new ChatComponentText(whisperText);
			//SoundHandler.lazarusPlaySound("mob.wither.idle", 0.1F, 0.1F);
			event.entityPlayer.addChatMessage(whisper);
			trigger = false;
		}		
	}
	
	/*---------------------------------------- Listen entity taking damage ----------------------------------------*/
	@SubscribeEvent
	public void onEntityGetHurt(LivingHurtEvent event)
	{
		/*Player attacked*/
		if(event.entity instanceof EntityPlayer)
		{
			/*Get player*/
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			/*Amplifying token*/
			if(player.inventory.hasItem(LazarusItems.amplifying_token)){AmplifyingToken.onHurt(event, player);}
		}
		
		/*Player attacking*/
		else if(event.source.getEntity() instanceof EntityPlayer)
		{
			/*Get Player*/
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			/*Amplifying token*/
			if(player.inventory.hasItem(LazarusItems.amplifying_token)){AmplifyingToken.onAttack(event, player);} 
			/*Cowardice token*/
			if(player.inventory.hasItem(LazarusItems.cowardice_token)){CowardiceToken.onMobHit(event, player);}
		}	
	}
	
	/*---------------------------------------- Player update  ----------------------------------------*/
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onPlayerUpdate(PlayerTickEvent event) 
	{	
		/*Variables*/
		World world = Minecraft.getMinecraft().theWorld;
		EntityPlayer thisPlayer = event.player;
		/*If player has collapse*/
		if(event.phase == Phase.START && event.player.isPotionActive(PotionHandler.collapse))
		{Collapse.applyEffects(thisPlayer, world);}		
	}	
	
	/*---------------------------------------- Listen for ender pearls ----------------------------------------*/
	@SubscribeEvent
	public void onEntiy(EntityEvent event)
	{
		if(event.entity!=null && !event.entity.worldObj.isRemote)
		{
			/*Ender Pearl*/
			if(event.entity.getClass().toString().contains("EntityEnderPearl"))
			{
				if(!enderPearlArray.contains(event.entity)){enderPearlArray.add(event.entity);}	
			}
			
			/*Aybssal Pearl*/
			if(event.entity.getClass().toString().contains("EntityEnderPearl"))
			{
				if(!enderPearlArray.contains(event.entity)){enderPearlArray.add(event.entity);}	
			}		
			//for (int i = 0; i < 5; i++)
    			//particleTest(double posX, double posY, double posZ, EnumParticleTypes type, World world)	
		}	
	}
	
	/*---------------------------------------- Moniter registed ender pearls  ----------------------------------------*/
	@SubscribeEvent
	public void onPearlUpdate(WorldTickEvent event) 
	{
		//Word object from event.
		World world = event.world;	
		//Only run in the start phase of a tick and only if there are ender pearls to track
		if(event.phase == Phase.START && enderPearlArray.size() > 0)
		{
			//Create a temp array, used ot remove pearls at the end.
			ArrayList<Entity> enderPearlArrayTemp = new ArrayList<Entity>();
			//Itterate over every pearl in the tracking list.
			for(Entity element : enderPearlArray)
			{
				//Calculate position and motion information
				Vec3 vec3 = new Vec3(element.posX, element.posY, element.posZ);
				Vec3 vec31 = new Vec3(element.posX + element.motionX, element.posY + element.motionY, element.posZ + element.motionZ);
				MovingObjectPosition movingobjectposition = element.worldObj.rayTraceBlocks(vec3, vec31);
				if (movingobjectposition != null){
					//If current position is in a nether portal block.
					if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && element.worldObj.getBlockState(movingobjectposition.getBlockPos()).getBlock() == Blocks.portal)
			        {	   
						//Create the abyssal pearl.
			            ItemStack newToken = new ItemStack(LazarusItems.abyssal_pearl);
			            //Offset the pearls position, so it isnt in the portal.
			            int xOffset;
			            if(element.motionX>0){xOffset = 1;}else{xOffset = -1;}
			            int yOffset;
			            if(element.motionY>0){yOffset = 1;}else{yOffset = -1;}
			            int zOffset;
			            if(element.motionZ>0){zOffset = 1;}else{zOffset = -1;}			    
			            //Create the entity at the given location.	
			            Entity entityToken = new EntityItem(world, element.posX-xOffset, element.posY, element.posZ-zOffset, newToken);
			            //Set the motion.
			            entityToken.setVelocity(-element.motionX/4, element.motionY/4, -element.motionZ/4);
			            
			            //entityToken.setCustomNameTag("Abyssal_Pearl");
			            	

			            System.out.println(entityToken.getName());			      
			            //Spawn the new pearl.
			            
			            if(!world.isRemote){
			            	world.spawnEntityInWorld(entityToken);
			            }
			            
			            System.out.println("t3");		
			            //Remove the origional ender pearl.
			            element.setDead();	
					}
				}
				//If ender pearl is not dead (still airborn), red-add it to the array.
				if(!element.isDead){enderPearlArrayTemp.add(element);}
			}
			//Refresh the array.
			enderPearlArray = enderPearlArrayTemp;
		}
	}	
	
	/*---------------------------------------- Listen for explosions ----------------------------------------*/
	@SubscribeEvent
	public void onExplosion(ExplosionEvent event)
	{
		for(Object element : currentPlayer.keySet()){
			/*Variables*/
			String stringElement = (String) element;
			EntityPlayer elementPlayer = (EntityPlayer) currentPlayer.get(stringElement);
			/*Quelling Token*/
			if(elementPlayer.inventory.hasItem(LazarusItems.quelling_token)){QuellingToken.onExplosion(event, elementPlayer);}
		}	
	}
	
	/*---------------------------------------- Listen for players joining ----------------------------------------*/
	@SubscribeEvent
	public void getServerPlayer(PlayerEvent.PlayerLoggedInEvent event)
	{
		EntityPlayer mpPlayer = event.player;
		currentPlayer.put(mpPlayer.getUniqueID().toString(), mpPlayer);
	}
	
	/*---------------------------------------- Listen for players leaving ----------------------------------------*/
	@SubscribeEvent
	public void getServerPlayer(PlayerEvent.PlayerLoggedOutEvent event)
	{
		EntityPlayer mpPlayer = event.player;
		currentPlayer.remove(mpPlayer.getUniqueID().toString());
	}
	
	public void particleTest(double posX, double posY, double posZ, EnumParticleTypes type, World world)
	{
		Random rand = new Random();
		double d0 = (double)((float)posX + rand.nextInt(10)-5);
        double d1 = (double)((float)posY + rand.nextInt(10)-5);
        double d2 = (double)((float)posZ + rand.nextInt(10)-5);
        double d3 = ((double)rand.nextInt(100)/100 - 0.5D) * 0.5D;
        double d4 = ((double)rand.nextInt(100)/100 - 0.5D) * 0.5D;
        double d5 = ((double)rand.nextInt(100)/100 - 0.5D) * 0.5D;
		world.spawnParticle(type, d0, d1, d2, d3, d4, d5, new int[0]);
	}
}
