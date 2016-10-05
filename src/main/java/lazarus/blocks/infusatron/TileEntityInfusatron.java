/**=============== Imports ===============**/
package lazarus.blocks.infusatron;

import lazarus.interfaces.container.ContainerInfusatron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**=============== Main ===============**/
public class TileEntityInfusatron extends TileEntityLockable 
implements IUpdatePlayerListBox, ISidedInventory
{
	// enumerate the slots
	public enum slotEnum {INPUT_SLOT, OUTPUT_SLOT}

	private static final int[] slotsTop = new int[] {slotEnum.INPUT_SLOT.ordinal()};
	private static final int[] slotsBottom = new int[] {slotEnum.OUTPUT_SLOT.ordinal()};
	private static final int[] slotsSides = new int[] {};
	private ItemStack[] infusatronItemStackArray = new ItemStack[28];
	private int timeCanGrind; 
	private int currentItemGrindTime;
	private int ticksGrindingItemSoFar;
	private int ticksPerItem;
	private String infusatronCustomName;

	@Override
	public boolean shouldRefresh(World parWorld, BlockPos parPos, IBlockState parOldState, IBlockState parNewState)
	{return false;}

	@Override
	public int getSizeInventory()
	{return infusatronItemStackArray.length;}

	@Override
	public ItemStack getStackInSlot(int index){
	
	//System.out.println("TEST = " + (index-37));
	
	return infusatronItemStackArray[index];}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		if (infusatronItemStackArray[index] != null)
		{
			ItemStack itemstack;

			if (infusatronItemStackArray[index].stackSize <= count)
			{
				itemstack = infusatronItemStackArray[index];
				infusatronItemStackArray[index] = null;
				return itemstack;
			}
			else
			{
				itemstack = infusatronItemStackArray[index].splitStack(count);
				if (infusatronItemStackArray[index].stackSize == 0)
				{infusatronItemStackArray[index] = null;}

				return itemstack;
			}
		}
		else{return null;}
	}

	/**
	 * When some containers are closed they call this on each slot, then 
	 * drop whatever it returns as an EntityItem -
	 * like when you close a workbench GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int index)
	{
		if (infusatronItemStackArray[index] != null)
		{
			ItemStack itemstack = infusatronItemStackArray[index];
			infusatronItemStackArray[index] = null;
			return itemstack;
		}
		else{return null;}
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		boolean isSameItemStackAlreadyInSlot = stack != null 
				&& stack.isItemEqual(infusatronItemStackArray[index]) 
				&& ItemStack.areItemStackTagsEqual(stack, 
						infusatronItemStackArray[index]);
		infusatronItemStackArray[index] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit())
		{
			stack.stackSize = getInventoryStackLimit();
		}

		// if input slot, reset the grinding timers
		if (index == slotEnum.INPUT_SLOT.ordinal() 
				&& !isSameItemStackAlreadyInSlot)
		{
			ticksPerItem = timeToInfusreOneItem(stack);
			ticksGrindingItemSoFar = 0;
			markDirty();
		}
	}

	@Override
	public String getName()
	{return hasCustomName() ? infusatronCustomName : "container.infusatron";}

	@Override
	public boolean hasCustomName()
	{return infusatronCustomName != null && infusatronCustomName.length() > 0;}

	public void setCustomInventoryName(String parCustomName)
	{infusatronCustomName = parCustomName;}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		infusatronItemStackArray = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbtTagCompound = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbtTagCompound.getByte("Slot");

			if (b0 >= 0 && b0 < infusatronItemStackArray.length)
			{
				infusatronItemStackArray[b0] = ItemStack.loadItemStackFromNBT(
						nbtTagCompound);
			}
		}

		timeCanGrind = compound.getShort("GrindTime");
		ticksGrindingItemSoFar = compound.getShort("CookTime");
		ticksPerItem = compound.getShort("CookTimeTotal");

		if (compound.hasKey("CustomName", 8))
		{infusatronCustomName = compound.getString("CustomName");}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setShort("GrindTime", (short)timeCanGrind);
		compound.setShort("CookTime", (short)ticksGrindingItemSoFar);
		compound.setShort("CookTimeTotal", (short)ticksPerItem);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < infusatronItemStackArray.length; ++i)
		{
			if (infusatronItemStackArray[i] != null)
			{
				NBTTagCompound nbtTagCompound = new NBTTagCompound();
				nbtTagCompound.setByte("Slot", (byte)i);
				infusatronItemStackArray[i].writeToNBT(nbtTagCompound);
				nbttaglist.appendTag(nbtTagCompound);
			}
		}
		
		compound.setTag("Items", nbttaglist);
		if (hasCustomName()){compound.setString("CustomName", infusatronCustomName);}
	}

	@Override
	public int getInventoryStackLimit(){return 64;}

	public boolean infusingSomething(){return true;}

	// this function indicates whether container texture should be drawn
	@SideOnly(Side.CLIENT)
	public static boolean func_174903_a(IInventory parIInventory)
	{return true ;}

	@Override
	public void update()
	{
		boolean hasBeenInfusing = infusingSomething();
		boolean changedInfusingState = false;

		if (infusingSomething())
		{--timeCanGrind;}

		if (!worldObj.isRemote)
		{
			// if something in input slot
			if (infusatronItemStackArray[slotEnum.INPUT_SLOT.ordinal()] != 
					null)
			{                
				// start grinding
				if (!infusingSomething() && canInfuse())
				{
					timeCanGrind = 150;

					if (infusingSomething())
					{
						changedInfusingState = true;
					}
				}

				// continue grinding
				if (infusingSomething() && canInfuse())
				{
					++ticksGrindingItemSoFar;

					// check if completed grinding an item
					if (ticksGrindingItemSoFar == ticksPerItem)
					{
						ticksGrindingItemSoFar = 0;
						ticksPerItem = timeToInfusreOneItem(
								infusatronItemStackArray[0]);
						grindItem();
						changedInfusingState = true;
					}
				}
				else
				{
					ticksGrindingItemSoFar = 0;
				}
			}

			// started or stopped grinding, update block to change to active 
			// or inactive model
			if (hasBeenInfusing != infusingSomething())
			{
				changedInfusingState = true;
			}
		}

		if (changedInfusingState)
		{markDirty();}
	}

	public int timeToInfusreOneItem(ItemStack parItemStack)
	{return 200;}

	private boolean canInfuse()
	{
		// if nothing in input slot
		if (infusatronItemStackArray[slotEnum.INPUT_SLOT.ordinal()] == null)
		{
			return false;
		}
		
		/* check if it has a grinding recipe
		else 
		{
			ItemStack itemStackToOutput = GrinderRecipes.instance()
					.getGrindingResult(grinderItemStackArray[
					                                         slotEnum.INPUT_SLOT.ordinal()]);
			if (itemStackToOutput == null) return false; 
			if (grinderItemStackArray[slotEnum.OUTPUT_SLOT.ordinal()] 
					== null) return true; 
			if (!grinderItemStackArray[slotEnum.OUTPUT_SLOT.ordinal()]
					.isItemEqual(itemStackToOutput)) return false; 
			int result = grinderItemStackArray[slotEnum.OUTPUT_SLOT.ordinal()]
					.stackSize + itemStackToOutput.stackSize;
			return result <= getInventoryStackLimit() 
					&& result <= grinderItemStackArray[slotEnum
					                                   .OUTPUT_SLOT.ordinal()].getMaxStackSize();
		}*/
		return true;
	}

	public void grindItem()
	{
		/*
		if (canGrind())
		{
			ItemStack itemstack = GrinderRecipes.instance()
					.getGrindingResult(grinderItemStackArray[
					                                         slotEnum.INPUT_SLOT.ordinal()]);

			// check if output slot is empty
			if (grinderItemStackArray[slotEnum.OUTPUT_SLOT.ordinal()] == null)
			{
				grinderItemStackArray[slotEnum.OUTPUT_SLOT.ordinal()] = 
						itemstack.copy();
			}
			else if (grinderItemStackArray[slotEnum.OUTPUT_SLOT.ordinal()]
					.getItem() == itemstack.getItem())
			{
				grinderItemStackArray[slotEnum.OUTPUT_SLOT.ordinal()]
						.stackSize += itemstack.stackSize; 
			}

			--grinderItemStackArray[slotEnum.INPUT_SLOT.ordinal()].stackSize;

			if (grinderItemStackArray[slotEnum.INPUT_SLOT.ordinal()]
					.stackSize <= 0)
			{
				grinderItemStackArray[slotEnum.INPUT_SLOT.ordinal()] = null;
			}
		}
		*/
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer playerIn)
	{
		return worldObj.getTileEntity(pos) != this ? false : 
			playerIn.getDistanceSq(pos.getX()+0.5D, pos.getY()+0.5D, 
					pos.getZ()+0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer playerIn) {}

	@Override
	public void closeInventory(EntityPlayer playerIn) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		return index == slotEnum.INPUT_SLOT.ordinal() ? true : false; 
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return side == EnumFacing.DOWN ? slotsBottom : 
			(side == EnumFacing.UP ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int parSlotIndex, ItemStack parStack, EnumFacing parFacing)
	{
		return true;
	}

	@Override
	public String getGuiID(){return "lazarus:infusatron";}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, 
			EntityPlayer playerIn)
	{
		return new ContainerInfusatron(playerInventory, this);
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return timeCanGrind;
		case 1:
			return currentItemGrindTime;
		case 2:
			return ticksGrindingItemSoFar;
		case 3:
			return ticksPerItem;
		default:
			return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
		case 0:
			timeCanGrind = value;
			break;
		case 1:
			currentItemGrindTime = value;
			break;
		case 2:
			ticksGrindingItemSoFar = value;
			break;
		case 3:
			ticksPerItem = value;
			break;
		default:
			break;
		}
	}

	@Override
	public int getFieldCount(){return 4;}

	@Override
	public void clear()
	{
		for (int i = 0; i < infusatronItemStackArray.length; ++i)
		{
			infusatronItemStackArray[i] = null;
		}
	}
}
