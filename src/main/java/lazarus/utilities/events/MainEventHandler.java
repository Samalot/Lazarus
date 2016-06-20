/*Imports*/
package lazarus.utilities.events;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lazarus.guis.container.token_pouch.GuiTokenPouch;
import lazarus.items.tokens.AmplifyingToken;
import lazarus.items.tokens.CowardiceToken;
import lazarus.items.tokens.QuellingToken;
import lazarus.main.LazarusItems;
import lazarus.main.LazarusMain;
import lazarus.potions.Collapse;
import lazarus.utilities.handlers.InventoryHandler;
import lazarus.utilities.handlers.KeyboardHandler;
import lazarus.utilities.handlers.NBTHandler;
import lazarus.utilities.handlers.PotionHandler;
import lazarus.utilities.handlers.whispers.AmsollionWhispers;
import lazarus.utilities.handlers.whispers.ImbrasWhispers;
import lazarus.utilities.handlers.whispers.OsmodeusWhispers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
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
	public static List<String> tokens = Arrays.asList("waning", "gilded","amplifying", "dormant", "cowardice", "quelling");
	
	public static Map currentPlayer = PlayerInstanceHandler.currentPlayer;
	public static int globalFlag_Token_Pouch_Open = 0;
	public static EntityPlayer mpPlayer;
	public static GuiScreen lastOpenedGui;
	public static ArrayList<Entity> enderPearlArray = new ArrayList<Entity>();
	public static long lastDuplicateTokenFlag = System.nanoTime();
	
	/*---------------------------------------- Listen for guiOpens ----------------------------------------*/
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void openGui(GuiOpenEvent event){globalFlag_Token_Pouch_Open = (event.gui instanceof GuiTokenPouch) ? 1 : 0;}

	/*---------------------------------------- Listen for item pickup ----------------------------------------*/
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onItemPickup(EntityItemPickupEvent event){
		String whisperTextTemp = "";
		boolean trigger = false;

		if(!event.item.getEntityItem().hasTagCompound() && event.item.getEntityItem().getItem() == LazarusItems.abyssal_pearl)
		{
			NBTHandler.pearlNBT(event.item.getEntityItem());
			EntityPlayer player = event.entityPlayer;
			World world = player.worldObj;
			if(!player.isPotionActive(PotionHandler.collapse))
			{
				world.playSoundAtEntity(player, "lazarus:spooky", 10.0F, 1.0F);
				player.addPotionEffect(new PotionEffect(PotionHandler.collapse.id, 400, 0));
				IChatComponent whisper = new ChatComponentText("§5§oYour §5§ohead §5§ostrains, §5§olike §5§osomething §5§ois §5§otearing §5§oat §5§othe §5§oseams §5§oof §5§oyour §5§omind.");
				player.addChatMessage(whisper);
			}	
		}

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
		World world = event.entityLiving.worldObj;
		/*Player attacked*/
		if(!world.isRemote && event.entity instanceof EntityPlayer)
		{			
			EntityPlayer player = (EntityPlayer) event.entityLiving; /*Get player*/
			if(InventoryHandler.scanInventory(player, AmplifyingToken.class, "amplifying_token")){event.ammount *= AmplifyingToken.onHurt();}
		}

		/*Player attacking*/
		if(event.source.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.source.getEntity(); /*Get Player*/	
			/*Amplifying token*/
			if(InventoryHandler.scanInventory(player, AmplifyingToken.class, "amplifying_token")){event.ammount *= AmplifyingToken.onAttack();}
			/*Cowardice token*/
			if(InventoryHandler.scanInventory(player, CowardiceToken.class, "cowardice_token")){CowardiceToken.onMobHit(player);}
		}		
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
		}
	}

	/*---------------------------------------- Moniter registed ender pearls  ----------------------------------------*/
	@SubscribeEvent
	public void onPearlUpdate(WorldTickEvent event) 
	{
		//Word object from event.
		World world = event.world;	
		//Only run in the start phase of a tick and only if there are ender pearls to track
		if(!world.isRemote && enderPearlArray.size() > 0)
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
						double slowDownScaler = 10;
						int xOffset;
						if(element.motionX>0){xOffset = 1;}else{xOffset = -1;}			           
						int yOffset;
						if(element.motionY>0){yOffset = 1;}else{yOffset = -1;}			            
						int zOffset;
						if(element.motionZ>0){zOffset = 1;}else{zOffset = -1;}

						//Create the entity at the given location.	
						EntityItem entityToken = new EntityItem(world, element.posX-xOffset, element.posY, element.posZ-zOffset, newToken);
						//Set the motion.
						entityToken.motionX = -element.motionX/slowDownScaler;
						entityToken.motionY = -element.motionY/slowDownScaler;
						entityToken.motionZ = -element.motionZ/slowDownScaler;		          

						//Spawn effects.		  
						world.playSoundEffect(entityToken.posX, entityToken.posY, entityToken.posZ, "random.explode", 1.0F, 1.0F);	

						//Spawn the new pearl.			   
						world.spawnEntityInWorld(entityToken);				            
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
			
			if(InventoryHandler.scanInventory(elementPlayer, QuellingToken.class, "quelling_token")){QuellingToken.onExplosion(event, elementPlayer);}
		}	
	}

	/*---------------------------------------- Listen for tooltips being shown ----------------------------------------*/
	public static ItemStack itemInfo_item;
	public static boolean itemInfo_isToken = false;
	@SubscribeEvent
	public void onTooltipShop(ItemTooltipEvent event)
	{
		/*Player*/
		EntityPlayer player = event.entityPlayer;
		/*World*/
		World world = player.worldObj;

		if(world.isRemote)
		{	
			itemInfo_item = event.itemStack;	
			if(KeyboardHandler.isSpaceDown()){
				if(itemInfo_item.getItem() == LazarusItems.amplifying_token
						|| itemInfo_item.getItem() == LazarusItems.cowardice_token
						|| itemInfo_item.getItem() == LazarusItems.gilded_token
						|| itemInfo_item.getItem() == LazarusItems.quelling_token
						|| itemInfo_item.getItem() == LazarusItems.waning_token
						){
					itemInfo_isToken = true;
					player.openGui(LazarusMain.instance, LazarusMain.GUI_ITEM_INFO, world, (int) player.posX, (int) player.posY, (int) player.posZ);
				}
				if(itemInfo_item.getItem() == LazarusItems.token_pouch
						|| itemInfo_item.getItem() == LazarusItems.abyssal_pearl
						|| itemInfo_item.getItem() == LazarusItems.dormant_token
						){
					itemInfo_isToken = false;
					player.openGui(LazarusMain.instance, LazarusMain.GUI_ITEM_INFO, world, (int) player.posX, (int) player.posY, (int) player.posZ);
				}
			}
		}
	}	
}
