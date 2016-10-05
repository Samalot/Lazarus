/**=============== Imports ===============**/
package lazarus.interfaces.container;

import java.util.ArrayList;
import java.util.List;

import lazarus.blocks.infusatron.InfusatronCatalogue;
import lazarus.blocks.infusatron.TileEntityInfusatron;
import lazarus.blocks.infusatron.TileEntityInfusatron.slotEnum;
import lazarus.interfaces.container.slots.SlotInfusatronCatalogue;
import lazarus.interfaces.container.slots.SlotInfusatronOutput;
import lazarus.interfaces.container.slots.SlotInfusatronSelected;
import lazarus.items.BaseItem;
import lazarus.main.LazarusItems;
import lazarus.main.LazarusMain;
import lazarus.packets.toClient.StringPacketClient;
import lazarus.packets.toServer.StringPacketServer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**=============== Main ===============**/
public class ContainerInfusatron extends Container
{
	/*--------------- Variables ---------------*/
	private final TileEntityInfusatron tileInfusatron;
	private final int sizeInventory;
	private EntityPlayer player;

	private InfusatronCatalogue catalogue= null;
	private Item currentInputItem = null;
	private Item currentSelectedItem = null;
	private int inputItemChangedFlag = 1;
	private int selectItemChangedFlag = 1;
	public String searchKeyword = "";

	//Slots
	ArrayList<SlotInfusatronCatalogue> catalogueSlotLog = new ArrayList<SlotInfusatronCatalogue>();
	Slot inputSlot = null;
	SlotInfusatronSelected selectSlot = null;

	public boolean searchMode = true;

	/*--------------- Constructor ---------------*/
	public ContainerInfusatron(InventoryPlayer parInventoryPlayer, TileEntityInfusatron parIInventory)
	{     
		tileInfusatron = parIInventory;
		player = parInventoryPlayer.player;
		sizeInventory = tileInfusatron.getSizeInventory();
		catalogue = new InfusatronCatalogue();

		//Collect the search mode.
		NBTTagCompound tagCompound = parIInventory.getTileData();
		if(!tagCompound.hasKey("mode")){tagCompound.setBoolean("mode", true);}
		else{searchMode = tagCompound.getBoolean("mode");}

		//Input Slot
		inputSlot = new Slot(tileInfusatron, 0, 64, 66);
		addSlotToContainer(inputSlot);

		//Output Slot
		addSlotToContainer(new SlotInfusatronOutput(parInventoryPlayer.player, tileInfusatron, 1, 64, 84));

		//Output Slot
		selectSlot = new SlotInfusatronSelected(parInventoryPlayer.player, tileInfusatron, 2, 64, 48);
		addSlotToContainer(selectSlot);

		//Player Inventory Slots
		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				addSlotToContainer(new Slot(parInventoryPlayer, (i*9)+j+9, 46+j*18, 162+i*18));
			}
			addSlotToContainer(new Slot(parInventoryPlayer, i, 46 + i * 18, 220));
		}

		//Hotbar slots
		for (int i = 0; i < 9; ++i)
		{
			addSlotToContainer(new Slot(parInventoryPlayer, i, 46 + i * 18, 220));
		}

		//Catalouge Slots	
		for (int i = 0; i < 5; ++i)
		{
			for (int j = 0; j < 5; ++j)
			{		
				SlotInfusatronCatalogue newCatalogueSlot= new SlotInfusatronCatalogue(parInventoryPlayer.player, tileInfusatron, 3+j+(i*5), 100+j*18, 48+i*18, tileInfusatron);
				addSlotToContainer(newCatalogueSlot);
				catalogueSlotLog.add(newCatalogueSlot);	
			}
		}
		if(!searchMode){clearCatalogue();refersh();};
	}

	/*--------------- Update the search word ---------------*/ 
	public void setSearch(String input){searchKeyword = input;}


	/*--------------- referesh container to client ---------------*/ 
	public void refersh()
	{
		if(!player.worldObj.isRemote)
		{
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			playerMP.sendContainerToPlayer(this);
		}
	}

	/*--------------- Update the catalouge ---------------*/ 
	public void updateCatalogue()
	{  	
		if(searchMode){	
			//Check if needs to be cleared
			if((!inputSlot.getHasStack() )
					||(!(inputSlot.getStack().getItem() instanceof BaseItem))
					||(!(catalogue.hasKey((BaseItem) inputSlot.getStack().getItem()))))
			{clearCatalogue();}

			else
			{
				clearCatalogue();
				ItemStack inputItem = inputSlot.getStack();
				List<BaseItem> items = catalogue.getItems((BaseItem)inputItem.getItem());

				items = filterItems(items);
				for(int i=0; i<items.size(); i++)
				{
					putStackInSlot(catalogueSlotLog.get(i).slotNumber,new ItemStack(items.get(i)));
				}
			}
			refersh();
		}
	}

	/*--------------- Filter the catalouge ---------------*/
	public List<BaseItem> filterItems(List<BaseItem> input)
	{
		List<BaseItem> output = new ArrayList<BaseItem>();
		if(searchKeyword == null || searchKeyword.equals("") || !(searchKeyword.trim().length() > 0))
		{
			return input;
		}

		else
		{
			for(BaseItem item : input)
			{
				if(item.getName().contains(searchKeyword.toLowerCase()))
				{
					output.add(item);
				}
			}
		}
		return output;
	}

	/*--------------- clear the catalouge ---------------*/ 
	public void clearCatalogue()
	{
		for(Slot slot : catalogueSlotLog)
		{
			putStackInSlot(slot.slotNumber, null);
		}
	}

	@Override
	public void addCraftingToCrafters(ICrafting listener)
	{
		super.addCraftingToCrafters(listener);
		listener.func_175173_a(this, tileInfusatron);
	}

	/*--------------- On changes (tick update) ---------------*/
	@Override
	public void detectAndSendChanges()
	{	
		//Update the flags
		if((currentInputItem==null)&&(inputSlot.getHasStack())){inputItemChangedFlag = 1;}
		else if((currentInputItem!=null)&&(!inputSlot.getHasStack())){inputItemChangedFlag = 1;}
		else if((inputSlot.getHasStack())&&(inputSlot.getStack().getItem()!=currentInputItem)){inputItemChangedFlag = 1;}

		if((currentSelectedItem==null)&&(selectSlot.getHasStack())){selectItemChangedFlag = 1;}
		else if((currentSelectedItem!=null)&&(!selectSlot.getHasStack())){selectItemChangedFlag = 1;}
		else if((selectSlot.getHasStack())&&(selectSlot.getStack().getItem()!=currentSelectedItem)){selectItemChangedFlag = 1;}


		//Update Catalogue.
		if((inputItemChangedFlag==1))
		{
			inputItemChangedFlag = 0;
			putStackInSlot(2, null);
			if(inputSlot.getHasStack()){currentInputItem = inputSlot.getStack().getItem();}
			else{currentInputItem = null;}
			updateCatalogue();
		} 

		//Update Catalogue.
		if((selectItemChangedFlag==1))
		{
			selectItemChangedFlag = 0;
			if(selectSlot.getHasStack()){currentSelectedItem = selectSlot.getStack().getItem();}
			else{currentSelectedItem = null;}
			//Send pacjet
			if(!player.worldObj.isRemote)
			{
				String inputName = "";
				if(currentSelectedItem != null){sendPacket("selectedItemSPLIT",currentSelectedItem.getItemStackDisplayName(new ItemStack(currentInputItem)));}
				else{sendPacket("selectedItemResetSPLIT"," ");}
			}
		} 
		super.detectAndSendChanges();
	}


	/*--------------- On Slot click ---------------*/
	@Override
	public ItemStack slotClick(int slotId, int clickedButton, int mode, EntityPlayer playerIn)
	{
		if(slotId == 2){return null;}

		else if((slotId<42 || slotId>66))
		{
			return super.slotClick(slotId, clickedButton, mode, playerIn);
		}

		else if((slotId>41)&&(slotId<67))
		{
			if(tileInfusatron.getStackInSlot(slotId-39) != null){putStackInSlot(2, tileInfusatron.getStackInSlot(slotId-39));}
			if(!playerIn.worldObj.isRemote)
			{
				EntityPlayerMP playerMP = (EntityPlayerMP) playerIn;
				playerMP.sendContainerToPlayer(this);
			}
			return null;

		}
		return super.slotClick(slotId, clickedButton, mode, playerIn);
	}

	/*--------------- Can Interact with ---------------*/
	@Override
	public boolean canInteractWith(EntityPlayer playerIn){return tileInfusatron.isUseableByPlayer(playerIn);}

	/*--------------- Transfer Stack ---------------*/
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int slotIndex)
	{
		ItemStack itemStack1 = null;
		Slot slot = (Slot)inventorySlots.get(slotIndex);

		if(slot == inputSlot){updateCatalogue();}


		if (slot != null && slot.getHasStack())
		{
			ItemStack itemStack2 = slot.getStack();
			itemStack1 = itemStack2.copy();

			if (slotIndex == TileEntityInfusatron.slotEnum.OUTPUT_SLOT.ordinal())
			{
				if (!mergeItemStack(itemStack2, sizeInventory, sizeInventory+36, true))
				{return null;}
				slot.onSlotChange(itemStack2, itemStack1);
			}
			else if (slotIndex != TileEntityInfusatron.slotEnum.INPUT_SLOT.ordinal())
			{
				if (slotIndex >= sizeInventory 
						&& slotIndex < sizeInventory+27) // player inventory slots
				{
					if (!mergeItemStack(itemStack2, sizeInventory+27, sizeInventory+36, false))
					{return null;}
				}
				else if (slotIndex >= sizeInventory+27 
						&& slotIndex < sizeInventory+36 
						&& !mergeItemStack(itemStack2, sizeInventory+1, 
								sizeInventory+27, false)) // hotbar slots
				{return null;}
			}
			else if (!mergeItemStack(itemStack2, sizeInventory, sizeInventory+36, false))
			{return null;}

			if (itemStack2.stackSize == 0)
			{slot.putStack((ItemStack)null);}
			else{slot.onSlotChanged();}

			if (itemStack2.stackSize == itemStack1.stackSize)
			{return null;}

			slot.onPickupFromSlot(playerIn, itemStack2);
		}
		return itemStack1; 
	}

	/*--------------- On Close ---------------*/
	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		if(playerIn.worldObj.isRemote)
		{
			LazarusMain.network.sendToServer(new StringPacketServer(
					"infusatronSearchSPLIT" + 
							tileInfusatron.getWorld().provider.getDimensionId() + "SPLIT" + 
							tileInfusatron.getPos().getX() + "SPLIT" + 
							tileInfusatron.getPos().getY() + "SPLIT" + 
							tileInfusatron.getPos().getZ() + "SPLIT" + 		
							""
					));
		}

		else
		{
			searchKeyword = "";
			updateCatalogue();
		}
		super.onContainerClosed(playerIn);
	}

	/*--------------- Send Packet ---------------*/
	public void sendPacket(String code, String message)
	{
		LazarusMain.network.sendToAll(new StringPacketClient(
				code +
				tileInfusatron.getWorld().provider.getDimensionId() + "SPLIT" + 
				tileInfusatron.getPos().getX() + "SPLIT" + 
				tileInfusatron.getPos().getY() + "SPLIT" + 
				tileInfusatron.getPos().getZ() + "SPLIT" + 		
				message
				));
	}
}