/**=============== Imports ===============**/
package lazarus.interfaces.guis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.input.Keyboard;

import lazarus.blocks.infusatron.TileEntityInfusatron;
import lazarus.interfaces.InterfaceLog;
import lazarus.interfaces.container.ContainerInfusatron;
import lazarus.interfaces.guis.buttons.GuiLazarusInfoButton;
import lazarus.interfaces.guis.buttons.GuiLazarusSelectButton;
import lazarus.interfaces.guis.screens.GuiTooltipContainer;
import lazarus.main.LazarusMain;
import lazarus.packets.toServer.StringPacketServer;
import lazarus.utilities.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**=============== Main ===============**/
@SideOnly(Side.CLIENT)
public class GuiInfusatron  extends GuiTooltipContainer
{
	/*--------------- Variables ---------------*/
	private final InventoryPlayer inventoryPlayer;
	private final TileEntityInfusatron tileInfusatron;
	private GuiTextField searchTextField;

	public boolean searchMode = true;
	public String seletedItem = ""; 


	GuiLazarusInfoButton modeInfoButton; 
	GuiLazarusInfoButton inputInfoButton; 
	GuiLazarusInfoButton outputInfoButton; 
	GuiLazarusInfoButton searchtInfoButton;
	GuiLazarusSelectButton confirmInfusionButton;

	/*--------------- Initialise ---------------*/
	public void initGui()
	{
		super.initGui();
		Keyboard.enableRepeatEvents(true);

		int marginHorizontal = (width - xSize) / 2;
		int marginVertical = (height - ySize) / 2;

		//Buttons
		int infoButtonXSize = 6;
		int infoButtonYSize = 6;
		modeInfoButton = new GuiLazarusInfoButton(0, marginHorizontal + 83, marginVertical + 47, infoButtonXSize, infoButtonYSize, "i");
		inputInfoButton = new GuiLazarusInfoButton(1, marginHorizontal + 83, marginVertical + 65, infoButtonXSize, infoButtonYSize, "i");
		outputInfoButton = new GuiLazarusInfoButton(2, marginHorizontal + 83, marginVertical + 83, infoButtonXSize, infoButtonYSize, "i");
		searchtInfoButton = new GuiLazarusInfoButton(3, marginHorizontal + 191, marginVertical + 31, infoButtonXSize, infoButtonYSize, "i");
		
		if(searchMode){confirmInfusionButton = new GuiLazarusSelectButton(4, marginHorizontal + 60, marginVertical + 102, 25, 16, "Confirm");}
		else{confirmInfusionButton = new GuiLazarusSelectButton(4, marginHorizontal + 60, marginVertical + 102, 25, 16, "Cancel");}

		

		buttonList.add(modeInfoButton);
		buttonList.add(inputInfoButton);
		buttonList.add(outputInfoButton);
		buttonList.add(searchtInfoButton);
		buttonList.add(confirmInfusionButton);

		//Search field
		searchTextField = new GuiTextField(0, this.fontRendererObj, guiLeft+119, guiTop+34, 66, this.fontRendererObj.FONT_HEIGHT+2);
		searchTextField.setEnableBackgroundDrawing(false);
		searchTextField.setMaxStringLength(40);
		searchTextField.setVisible(true);
		
		//Updates
		searchtInfoButton.visible = searchMode;

	}

	/*--------------- Resources Locations ---------------*/
	private static final ResourceLocation infusatronGuiTextures = new ResourceLocation("lazarus:textures/gui/infusatron/infusatron.png");
	private static final ResourceLocation infusatronSearchTextures = new ResourceLocation("lazarus:textures/gui/infusatron/search.png");

	/*--------------- Constructor ---------------*/
	public GuiInfusatron(InventoryPlayer parInventoryPlayer, TileEntityInfusatron parInventoryGrinder)
	{
		//Super
		super(new ContainerInfusatron(parInventoryPlayer, parInventoryGrinder));
		
		//Variables
		inventoryPlayer = parInventoryPlayer;
		tileInfusatron = parInventoryGrinder;
		xSize = 256;
		ySize = 243;
		
		//Reset the search
		sendSearchPacket("infusatronSearchSPLIT","");
		
		//Collect the search mode.
		NBTTagCompound tagCompound = parInventoryGrinder.getTileData();
		if(!tagCompound.hasKey("mode")){tagCompound.setBoolean("mode", true);searchMode = true;}
		else{searchMode = tagCompound.getBoolean("mode");}
		
	}

	/*--------------- Foreground ---------------*/
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		//Variables
		String s = tileInfusatron.getDisplayName().getUnformattedText();
		fontRendererObj.drawString(inventoryPlayer.getDisplayName().getUnformattedText(), 44, ySize - 92, 4210752);
		//Tooltips
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	/*--------------- Background ---------------*/
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{		

		//Variables
		int marginHorizontal = (width - xSize) / 2;
		int marginVertical = (height - ySize) / 2;
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		//Draw main gui
		mc.getTextureManager().bindTexture(infusatronGuiTextures);


		drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, xSize, ySize);

		//If search mode, draw catalogue
		if(searchMode)
		{
			mc.getTextureManager().bindTexture(infusatronSearchTextures);
			drawTexturedModalRect(marginHorizontal, marginVertical, 0, 0, xSize, ySize);
			searchTextField.drawTextBox();
		}

		//Not search mode
		else
		{
			fontRendererObj.drawString(seletedItem, marginHorizontal+92,marginVertical+30, 4210752);
		}
	}
	/*--------------- Button litener ---------------*/
	public void actionPerformed(GuiButton button)
	{
		
		String key = "D"+tileInfusatron.getWorld().provider.getDimensionId()
		+"X"+tileInfusatron.getPos().getX()
		+"Y"+tileInfusatron.getPos().getY()
		+"Z"+tileInfusatron.getPos().getZ();
		ContainerInfusatron containerTarget = InterfaceLog.infusatronLog.get(key);	
		
		int buttonId = button.id;
		switch (buttonId)
		{
		case 4: 	
			if(searchMode && seletedItem.length()>1)
			{
				searchMode=false;
				searchtInfoButton.visible = false;
				searchtInfoButton.enabled = false;
				confirmInfusionButton.setText("Cancel");
				sendSearchPacket("infusatronModeSetSPLIT","false");
			}
			else
			{	
				searchMode=true;
				searchtInfoButton.visible = true;
				searchtInfoButton.enabled = true;
				confirmInfusionButton.setText("Confirm");
				sendSearchPacket("infusatronModeSetSPLIT","true");
			}
		}
		//Collect the search mode.
		NBTTagCompound tagCompound = tileInfusatron.getTileData();
		tagCompound.setBoolean("mode", searchMode);
		tileInfusatron.writeToNBT(tagCompound);
		InterfaceLog.infusatronModeClient.put(tileInfusatron, searchMode);
	}

	/*--------------- On Close ---------------*/
	public void onGuiClosed()
	{
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}

	/*--------------- On mouse click ---------------*/
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		searchTextField.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/*--------------- On typing ---------------*/
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {		
		if(!searchTextField.isFocused()) {
			super.keyTyped(typedChar, keyCode);
		}
		else 
		{
			if(keyCode == 1) {this.mc.thePlayer.closeScreen();}
			searchTextField.textboxKeyTyped(typedChar, keyCode);
			sendSearchPacket("infusatronSearchSPLIT",searchTextField.getText().toLowerCase());	
		}
	}

	/*--------------- Update Screen ---------------*/
	@Override
	public void updateScreen() {
		super.updateScreen();
		searchTextField.updateCursorCounter();
	}


	/*--------------- Tooltips ---------------*/
	@Override
	protected String GetButtonTooltip(int buttonId)
	{
		switch (buttonId)
		{
		case 0: return "Selected Item.";
		case 1: return "Input Item.";
		case 2: return "Output Item.";
		case 3: if(searchMode){return "Search: Filter by keyword.";}	
		default: return null;
		}
	}

	/*--------------- Tooltips ---------------*/
	public void sendSearchPacket(String code, String message)
	{
		LazarusMain.network.sendToServer(new StringPacketServer(
				code +
				tileInfusatron.getWorld().provider.getDimensionId() + "SPLIT" + 
				tileInfusatron.getPos().getX() + "SPLIT" + 
				tileInfusatron.getPos().getY() + "SPLIT" + 
				tileInfusatron.getPos().getZ() + "SPLIT" + 		
				message
				));
	}
}