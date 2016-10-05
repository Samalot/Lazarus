/**=============== Imports ===============**/
package lazarus.interfaces.guis;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lazarus.items.BaseItem;
import lazarus.items.BaseToken;
import lazarus.main.LazarusItems;
import lazarus.utilities.events.MainEventHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**=============== Main ===============**/
public class GuiItemInfoScreen extends GuiScreen {
	/*Varaibles*/
	ResourceLocation iconArea = new ResourceLocation("lazarus:textures/gui/infoScreen/iconArea.png");
	ResourceLocation titleArea = new ResourceLocation("lazarus:textures/gui/infoScreen/title.png"); 
	ResourceLocation subTitleArea = new ResourceLocation("lazarus:textures/gui/infoScreen/subTitle.png");
	ResourceLocation descTop = new ResourceLocation("lazarus:textures/gui/infoScreen/descTop.png"); 
    ResourceLocation descMiddle = new ResourceLocation("lazarus:textures/gui/infoScreen/descMiddle.png"); 
	ResourceLocation descBottom = new ResourceLocation("lazarus:textures/gui/infoScreen/descBottom.png");
	ResourceLocation rarityBox = new ResourceLocation("lazarus:textures/gui/infoScreen/rare.png"); 
	ResourceLocation rarityTitle = new ResourceLocation("lazarus:textures/gui/infoScreen/rarityTitle.png");
	ResourceLocation arrow = new ResourceLocation("lazarus:textures/gui/infoScreen/arrow.png"); 
	
	/*Use to draw onto screen*/
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		/*============================== Images ==============================*/
		
		/*Set reload variable*/
		if(MainEventHandler.guiReloadFlag != 1){MainEventHandler.guiReloadFlag = 1;}
		
		/*Variables*/
		ItemStack item = MainEventHandler.itemInfo_item;
		boolean isToken = (item.getItem() instanceof BaseToken) ? true : false;
	    BaseItem baseItem = (BaseItem) item.getItem();
		
		/*Draw background*/
		this.drawDefaultBackground();
	    super.drawScreen(mouseX, mouseY, partialTicks);
	    
	    /*Draw Icon Box*/ 
	    this.mc.getTextureManager().bindTexture(iconArea);
	    this.drawModalRectWithCustomSizedTexture((int)(width*0.05), (int)(width*0.05), 0, 0, 50, 50, 50, 50);
	    
	    /*Draw Title box*/
	    this.mc.getTextureManager().bindTexture(titleArea);
	    this.drawModalRectWithCustomSizedTexture((int)((width*0.05)+55), (int)(width*0.05), 0, 0, 100, 20, 100, 20);
	    
	    /*Draw icon*/
	    ResourceLocation icon = new ResourceLocation("lazarus:textures/items/" + item.getUnlocalizedName().substring(5)+".png");
	    this.mc.getTextureManager().bindTexture(icon);
	    this.drawModalRectWithCustomSizedTexture((int)(width*0.05)+9, (int)(width*0.05)+9, 0, 0, 32, 32, 32, 32);
	    
	    /*Draw rarity*/
	    if(isToken)
	    {
	    	this.mc.getTextureManager().bindTexture(rarityTitle);
		    this.drawModalRectWithCustomSizedTexture((int)(width*0.05), (int)(width*0.05)+55, 0, 0, 50, 16, 50, 16);
		    
	    	if(item.hasTagCompound())
	    	{
	    		int rarity = item.getTagCompound().getInteger("rarity");
	    		
	    		this.mc.getTextureManager().bindTexture(arrow);
			    this.drawModalRectWithCustomSizedTexture((int)(width*0.05)-10, (int)(width*0.05)+140-(rarity*12), 0, 0, 7, 11, 7, 11);
	    	}
		    this.mc.getTextureManager().bindTexture(rarityBox);
		    this.drawModalRectWithCustomSizedTexture((int)(width*0.05), (int)(width*0.05)+76, 0, 0, 50, 80, 50, 80);
	    }
	    	    
	    /*Description*/
	    int lineLen = 45;
	    ArrayList<String> descriptionWords = new ArrayList(Arrays.asList(baseItem.getDescription().split(" "))); 
	    ArrayList<String> combinedWords = new ArrayList<String>();
	    String currentLineString = "";
	    for(String element : descriptionWords)
	    {
	    	if(currentLineString.length()+element.length()>=lineLen)
	    	{
	    		combinedWords.add(currentLineString);
	    		currentLineString = element;
	    	}
	    	else if(currentLineString.length() == 0){currentLineString = element;}
	    	else{currentLineString = currentLineString + " " + element;}
	    }
	    combinedWords.add(currentLineString);	     
	    
	    /*Description Top*/
	    this.mc.getTextureManager().bindTexture(descTop);
	    this.drawModalRectWithCustomSizedTexture((int)((width*0.05)+55), (int)(width*0.05)+25, 0, 0, 250, 4, 250, 4);
	    
	    /*Description Middle*/
	    this.mc.getTextureManager().bindTexture(descMiddle);
	    for(int j=0; j<combinedWords.size(); j++)
	    {this.drawModalRectWithCustomSizedTexture((int)((width*0.05)+55), (int)(width*0.05)+29+(j*10), 0, 0, 250, 10, 250, 10);}
	    
	    /*Description Bottom*/
	    this.mc.getTextureManager().bindTexture(descBottom);
	    this.drawModalRectWithCustomSizedTexture((int)((width*0.05)+55), (int)(width*0.05)+29+(combinedWords.size()*10), 0, 0, 250, 4, 250, 4);
	    
	    /*Ability title*/
	    /*Abilities*/
	    if(!(baseItem.subDescription == null || baseItem.subDescription.size() == 0))
	    {
	    	this.mc.getTextureManager().bindTexture(subTitleArea);
	    	this.drawModalRectWithCustomSizedTexture((int)((width*0.05)+55), (int)(width*0.05)+38+(combinedWords.size()*10), 0, 0, 100, 16, 100, 16);
	    }
	    
	    /*Sub description boxes*/
	    ArrayList<ArrayList<String>> subLinesCollection = new ArrayList<ArrayList<String>>();
	    int offset = (int)(width*0.05)+59+(combinedWords.size()*10);
	    for(int x=0; x<baseItem.subDescription.size();x++)
	    {
	    	//Draw top
	    	this.mc.getTextureManager().bindTexture(descTop);
	    	this.drawModalRectWithCustomSizedTexture((int)((width*0.05)+55), offset, 0, 0, 250, 4, 250, 4);
	    	offset += 4;
	    	
	    	//Collect and split sub description
	    	String initialText = baseItem.subDescription.get(x);
	    	ArrayList<String> initialTextSplit = new ArrayList(Arrays.asList(initialText.split(" "))); 

	    	//Seperate description into lines.
	    	ArrayList<String> combinedWordsSub = new ArrayList<String>();
		    String currentLineStringSub = "";
		    for(String element : initialTextSplit)
		    {
		    	if(currentLineStringSub.length()+element.length()>=lineLen)
		    	{
		    		combinedWordsSub.add(currentLineStringSub);
		    		currentLineStringSub = element;
		    	}
		    	else if(currentLineStringSub.length() == 0){currentLineStringSub = element;}
		    	else{currentLineStringSub = currentLineStringSub + " " + element;}
		    }
		    combinedWordsSub.add(currentLineStringSub);	
		    
		    //Record the lines to the global log.
		    subLinesCollection.add(combinedWordsSub);
		    
		    //Draw the correct amount of middle sections
		    this.mc.getTextureManager().bindTexture(descMiddle);
		    for(int y=0; y<combinedWordsSub.size();y++)
		    {
		    	this.drawModalRectWithCustomSizedTexture((int)((width*0.05)+55), offset, 0, 0, 250, 10, 250, 10);
		    	offset +=10;
		    }
		    //Draw bottom section
		    this.mc.getTextureManager().bindTexture(descBottom);
		    this.drawModalRectWithCustomSizedTexture((int)((width*0.05)+55), offset, 0, 0, 250, 4, 250, 4);
		    offset+=9;	
	    }
	    	
	    	
	    /*============================== Text ==============================*/
	    
	    /*Rarity*/
	    if(isToken)
	    {
		    BaseToken token = (BaseToken) item.getItem();
		    List<Double> amplifiers = token.getAmplifiers();
		    this.mc.fontRendererObj.drawString("Rarity", 
		    		(int)(width*0.05)+5,
		    		(int)(width*0.05)+59, 
		    		Integer.parseInt("272727", 16));
		    
		    for(int i = 0; i<6 ;i++)
		    {
		    	double element = amplifiers.get(5-i);
		    	this.mc.fontRendererObj.drawString(Integer.toString((int)(element*100))+"%", 
		    			(int)(width*0.05)+19, 
		    			(int)(width*0.05)+82+(i*12), 
		    			Integer.parseInt("555555", 16));
		    }
	    }
	    
	    /*Description*/
	    for(int j=0; j<combinedWords.size(); j++)
	    {
	    	this.mc.fontRendererObj.drawString(combinedWords.get(j), 
	    			(int)((width*0.05)+60), 
	    			(int)((width*0.05)+30+(j*9)), 
	    			Integer.parseInt("555555", 16));
	    }
	    
	    /*Item Name*/
	    this.mc.fontRendererObj.drawString(item.getDisplayName(), 
	    		(int)((width*0.05)+60),
	    		(int)((width*0.05)+6), 
	    		Integer.parseInt("272727", 16));
	    
	    /*Abilities*/
	    if(!(baseItem.subDescription == null || baseItem.subDescription.size() == 0))
	    {
	    	this.mc.fontRendererObj.drawString("Abilities", 
		    		(int)((width*0.05)+60),
		    		(int)(width*0.05)+42+(combinedWords.size()*10), 
		    		Integer.parseInt("272727", 16));
	    }
	    
	    
	    /*Sub description*/
	    int offset2x = (int)((width*0.05)+60);
	    int offset2y = (int)(width*0.05)+65+(combinedWords.size()*10);
	    
	    /*Over each box*/
	    for(ArrayList<String> element : subLinesCollection)
	    {
	    	/*Over each line*/
	    	for(String line : element)
	    	{
	    		this.mc.fontRendererObj.drawString(line, 
		    			offset2x, 
		    			offset2y, 
		    			Integer.parseInt("555555", 16));
	    		offset2y += 10;
	    	}
	    	offset2y += 13;
	    }
    
	}
	
	/*Don't pause the game*/@Override
	public boolean doesGuiPauseGame() {return false;}	
}
