/*Imports*/
package lazarus.guis;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

import lazarus.items.BaseToken;
import lazarus.main.LazarusItems;
import lazarus.utilities.events.MainEventHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/*Main*/
public class ItemInfoScreen extends GuiScreen {
	/*Use to draw onto screen*/
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		
		/*Variables*/
		ItemStack item = MainEventHandler.itemInfo_item;
		BaseToken baseItem = (BaseToken) item.getItem();
	    String itemName = item.getDisplayName();
		
		/*Draw background*/
		this.drawDefaultBackground();
	    super.drawScreen(mouseX, mouseY, partialTicks);
	    
	    /*Background image*/
	    ResourceLocation background; 
	    if(item.getItem() == LazarusItems.abyssal_pearl){background = new ResourceLocation("lazarus:textures/gui/guiPearl.png");}
	    else{background = new ResourceLocation("lazarus:textures/gui/guiTemplate.png");}
	    
	    /*Item icon image*/
	    ResourceLocation icon = new ResourceLocation("lazarus:textures/items/" + item.getUnlocalizedName().substring(5)+".png");
	    /*Rarity scale image*/
	    ResourceLocation rarityScale = new ResourceLocation("lazarus:textures/gui/rarityScale.png");
	    
	    /*Draw background*/
	    this.mc.getTextureManager().bindTexture(background);   
	    this.drawModalRectWithCustomSizedTexture((width/2)-(176/2), (height/2)-(166/2), 0, 0, 176, 166, 256, 256);
	    /*Draw icon*/
	    this.mc.getTextureManager().bindTexture(icon);
	    this.drawModalRectWithCustomSizedTexture((width/2)-(32/2), (height/2)-(32/2)-45, 0, 0, 32, 32, 32, 32);
	    
	    /*If icon, draw rarity scale*/
	    if(MainEventHandler.itemInfo_isToken){
	    	this.mc.getTextureManager().bindTexture(rarityScale);
	    	this.drawModalRectWithCustomSizedTexture((width/2)-(176/2), (height/2)-(166/2), 0, 0, 176, 166, 256, 256);
		    String rarityString = "";	    
		    List<Double> amplifiers = baseItem.getAmplifiers();
		    for(double element : baseItem.getAmplifiers()){ DecimalFormat df = new DecimalFormat("#0.00");rarityString+=df.format(element)+" ";}
		    if(amplifiers.size()>0){rarityString = rarityString.substring(0,rarityString.length()-1);}
		    this.mc.fontRendererObj.drawString(rarityString, (width / 2)- (mc.fontRendererObj.getStringWidth(rarityString)/2), (height / 2) + 62, Integer.parseInt("555555", 16));
		    this.mc.fontRendererObj.drawString("Rarity amplifiers:", (width / 2)- (mc.fontRendererObj.getStringWidth("Rarity amplifiers:")/2), (height / 2) + 40, Integer.parseInt("555555", 16));
	    }
	    
	    /*Write item name*/
	    this.mc.fontRendererObj.drawString(itemName, (width / 2)- (mc.fontRendererObj.getStringWidth(itemName)/2), (height / 2) - 73, Integer.parseInt("555555", 16));
	    
	    /*Write the description*/
	    String[] splitDescription = baseItem.getDescription().split("\n");
	    int widthDesc = (width / 2)-72;
	    int hightDesc = (height / 2) - 21;
	    
	    for(String line : splitDescription)
	    {
	    	this.mc.fontRendererObj.drawString(line, widthDesc, hightDesc, Integer.parseInt("555555", 16));
	    	hightDesc += 10;
	    }
	    
	    
	}
	
	/*Don't pause the game*/@Override
	public boolean doesGuiPauseGame() {return false;}

	/*Initialise the Gui*/
	private GuiButton a;
	private GuiButton b;

	public void initGui() {
	    //this.buttonList.add(this.a = new GuiButton(0, this.width / 2 - 100, this.height / 2 - 24, "a"));
	    //this.buttonList.add(this.b = new GuiButton(1, this.width / 2 - 100, this.height / 2 + 4, "This is button b"));  
	}
	
	/*Handle button presses*/
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
	    if (button == this.a) {
	        //Main.packetHandler.sendToServer(...);
	        this.mc.displayGuiScreen(null);
	        if (this.mc.currentScreen == null)
	            this.mc.setIngameFocus();
	    }
	    if (button == this.b){
	        //Main.packetHandler.sendToServer(...);
	        this.mc.displayGuiScreen(null);
	        if (this.mc.currentScreen == null)
	            this.mc.setIngameFocus();
	    }
	}
	
	/*On close*/
	@Override
	public void onGuiClosed() 
	{
		//Minecraft.getMinecraft().displayGuiScreen(MainEventHandler.lastOpenedGui);
		//Minecraft.getMinecraft().displayGuiScreen(this);
	}
}
