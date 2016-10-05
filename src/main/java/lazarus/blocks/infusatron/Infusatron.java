/**=============== Imports ===============**/
package lazarus.blocks.infusatron;
import java.util.Random;

import lazarus.interfaces.InterfaceLog;
import lazarus.main.LazarusBlocks;
import lazarus.main.LazarusMain;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**=============== Main ===============**/
public class Infusatron extends BlockContainer{

	/*--------------- Variables ---------------*/
	public static final PropertyDirection FACING = PropertyDirection.create("facing",EnumFacing.Plane.HORIZONTAL);
	private static boolean hasTileEntity;

	/*--------------- Constructor ---------------*/
	public Infusatron(Material materialIn) 
	{
		super(materialIn); /*Set the material*/
		setCreativeTab(LazarusMain.tabLazarus); /*Creative tab*/
		setBlockBounds(0.0F, 0.0F, 0.0F, 0.8F, 1.75F, 0.85F); /*Extend the hit box*/
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH)); /*Direction*/
		stepSound = soundTypeMetal; /*Step sound*/
		blockParticleGravity = 1.0F; /*Block gravity*/
		lightOpacity = 0; /*Cast a light shadow*/
		setTickRandomly(false);
		useNeighborBrightness = true;
	}

	/*--------------- Item drop ---------------*/
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{return Item.getItemFromBlock(LazarusBlocks.infusatron);}

	/*--------------- On Block Added ---------------*/
	@Override
	public void onBlockAdded(World parWorld, BlockPos parBlockPos, IBlockState parIBlockState)
	{
		if (!parWorld.isRemote)
		{
			// Rotate block if the front side is blocked
			Block blockToNorth = parWorld.getBlockState(parBlockPos.north()).getBlock();
			Block blockToSouth = parWorld.getBlockState(parBlockPos.south()).getBlock();
			Block blockToWest = parWorld.getBlockState(parBlockPos.west()).getBlock();
			Block blockToEast = parWorld.getBlockState(parBlockPos.east()).getBlock();
			EnumFacing enumfacing = (EnumFacing)parIBlockState.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && blockToNorth.isFullBlock() && !blockToSouth.isFullBlock())
			{enumfacing = EnumFacing.SOUTH;}

			else if (enumfacing == EnumFacing.SOUTH && blockToSouth.isFullBlock() && !blockToNorth.isFullBlock())
			{enumfacing = EnumFacing.NORTH;}

			else if (enumfacing == EnumFacing.WEST && blockToWest.isFullBlock() && !blockToEast.isFullBlock())
			{enumfacing = EnumFacing.EAST;}

			else if (enumfacing == EnumFacing.EAST && blockToEast.isFullBlock() && !blockToWest.isFullBlock())
			{enumfacing = EnumFacing.WEST;}

			parWorld.setBlockState(parBlockPos, parIBlockState.withProperty(FACING, enumfacing), 2);
		}
	}

	/*--------------- Open GUI on click ---------------*/
	@Override
	public boolean onBlockActivated(World parWorld, BlockPos parBlockPos, IBlockState parIBlockState, EntityPlayer parPlayer, EnumFacing parSide, float hitX, float hitY, float hitZ)
	{
		if (!parWorld.isRemote)
		{
			parPlayer.openGui(LazarusMain.instance, 
					LazarusMain.GUI_INFUSATRON, 
					parWorld, 
					parBlockPos.getX(), 
					parBlockPos.getY(), 
					parBlockPos.getZ()); 
		}        
		return true;
	}

	/*--------------- Create tile entity ---------------*/
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntityInfusatron();
	}

	/*--------------- On block placement ---------------*/
	@Override
	public IBlockState onBlockPlaced( World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{return getDefaultState().withProperty(FACING,placer.getHorizontalFacing().getOpposite());}

	@Override
	public void onBlockPlacedBy(
			World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{worldIn.setBlockState(pos, state.withProperty(FACING,placer.getHorizontalFacing().getOpposite()),2);}

	/*--------------- On block break drop inventory ---------------*/
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!hasTileEntity)
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TileEntityInfusatron)
			{
				InventoryHelper.dropInventoryItems(worldIn, pos, 
						(TileEntityInfusatron)tileentity);
				
				String key = "D"+worldIn.provider.getDimensionId()+"X"+pos.getX()+"Y"+pos.getY()+"Z"+pos.getZ();
				if(InterfaceLog.infusatronLog.containsKey(key)){InterfaceLog.infusatronLog.remove(key);}
				if(InterfaceLog.infusatronGUILog.containsKey(key)){InterfaceLog.infusatronGUILog.remove(key);}
				if(InterfaceLog.infusatronModeClient.containsKey(tileentity)){InterfaceLog.infusatronModeClient.remove(tileentity);}
				
				worldIn.removeTileEntity(pos);
				tileentity.invalidate();
				worldIn.updateComparatorOutputLevel(pos, this);		
			}
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World worldIn, BlockPos pos){return Item.getItemFromBlock(LazarusBlocks.infusatron);}

	@Override
	public int getRenderType(){return 3;}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state){return getDefaultState().withProperty(FACING, EnumFacing.SOUTH);}


	@Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        if (enumfacing.getAxis() == EnumFacing.Axis.Y){enumfacing = EnumFacing.NORTH;}
        return getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state){return ((EnumFacing)state.getValue(FACING)).getIndex();}

    @Override
    protected BlockState createBlockState(){return new BlockState(this, new IProperty[] {FACING});}

    @SideOnly(Side.CLIENT)
    static final class SwitchEnumFacing
    {
        static final int[] enumFacingArray = new int[EnumFacing.values().length];

        static
        {
            try{enumFacingArray[EnumFacing.WEST.ordinal()] = 1;}
            catch (NoSuchFieldError var4)
            {;}

            try{enumFacingArray[EnumFacing.EAST.ordinal()] = 2;}
            catch (NoSuchFieldError var3)
            {;}

            try{enumFacingArray[EnumFacing.NORTH.ordinal()] = 3;}
            catch (NoSuchFieldError var2)
            {;}

            try
            {enumFacingArray[EnumFacing.SOUTH.ordinal()] = 4;}
            catch (NoSuchFieldError var1)
            {;}
        }
    }

	/*Particles*/
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		Random r = new Random();
		/*Particle effects*/
		double d0 = (double)((float)pos.getX() + (0.1 + (0.6 - 0.1) * r.nextDouble()));
		double d1 = (double)((float)pos.getY() + (0.3 + (0.85 - 0.3) * r.nextDouble()));
		double d2 = (double)((float)pos.getZ() + (0.3 + (0.7 - 0.3) * r.nextDouble()));
		worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, 0.0, 0.0, 0.0, new int[0]);
		worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, 0.0, 0.0, 0.0, new int[0]);
		worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, 0.0, 0.0, 0.0, new int[0]);
		worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, 0.0, 0.0, 0.0, new int[0]);
		worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, 0.0, 0.0, 0.0, new int[0]);
		worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, 0.0, 0.0, 0.0, new int[0]);
	}

	/*Check for space to place*/
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{return (worldIn.getBlockState(pos.up(1)).getBlock() instanceof BlockAir) ? true : false;}

	/*Make transparent*/
	@Override
	public boolean isOpaqueCube(){return false;}
	@Override
	public EnumWorldBlockLayer getBlockLayer(){return EnumWorldBlockLayer.CUTOUT_MIPPED;}
	@Override
	public boolean isFullCube(){return false;}
}