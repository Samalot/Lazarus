/**=============== Imports ===============**/
package lazarus.interfaces.guis.buttons;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**=============== Main ===============**/
public class GuiLazarusSelectButton extends GuiButton{
	
	protected static final ResourceLocation buttonTextures = new ResourceLocation("lazarus:textures/gui/infusatron/selectButton.png");
	private String displayString;
	
	/*--------------- Constructor ---------------*/
	public GuiLazarusSelectButton(int buttonId, int x, int y, int widthIn, int heightIn, String textInput) 
	{
		super(buttonId, x, y, widthIn, heightIn, textInput);
		displayString = textInput;
	}
	
	
	public void setText(String text){displayString = text;}
	
	/*--------------- Draw the button ---------------*/
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRendererObj;
            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int k = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + k * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + k * 20, this.width / 2, this.height);
            this.mouseDragged(mc, mouseX, mouseY);
            int l = 14737632;
            if (packedFGColour != 0){l = packedFGColour;}
            else if (!this.enabled){l = 10526880;}
            else if (this.hovered){l = 16777120;}

            GL11.glPushMatrix();
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			mc.fontRendererObj.drawString(this.displayString, ((this.xPosition + this.width / 2)*2)-17, (4+(this.yPosition + (this.height - 8) / 2)*2)-8, 3);
            GL11.glPopMatrix();
        }
    }

}
