package sekwah.mods.narutomod.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import sekwah.mods.narutomod.client.gui.components.GuiNarutoSlider;
import sekwah.mods.narutomod.settings.NarutoSettings;

//
// GuiBuffBar implements a simple status bar at the top of the screen which 
// shows the current buffs/debuffs applied to the character.
//
public class GuiOptionsMenu extends GuiScreen {
    private static final ResourceLocation guiBackground = new ResourceLocation("narutomod:textures/gui/blankGui.png");

    private int guiWidth = 248;
    private int guiHeight = 166;

    private String[] pageTitles = {"naruto.gui.generalOptions", "naruto.gui.chakraGui", "naruto.gui.jutsuSettings"};

    private boolean needsUpdate = false;

    // private int currentPage = 1; // now stored in the settings so it stays on the same page

    /** The current mouse x coordinate */
    //protected int mouseX;

    /** The current mouse y coordinate */
    //protected int mouseY;
    //protected double field_74117_m;
    //protected double field_74115_n;

    /**
     * Whether the Mouse Button is down or not
     */
    //private int isMouseButtonDown;
    public GuiOptionsMenu() {

    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui() {
        this.updateButtons();
    }

    public void updateButtons() {
        this.buttonList.clear();

        int guiX = (this.width - this.guiWidth) / 2;
        int guiY = (this.height - this.guiHeight) / 2;

        this.buttonList.add(new GuiButton(1, this.width / 2 + 34, this.height / 2 + 54, 80, 20, I18n.format("naruto.gui.done")));
        if (NarutoSettings.settingsPage == 1) {
            GuiButton firstPersonButton = new GuiButton(4, guiX + 9, guiY + 20, 110, 20, I18n.format("naruto.gui.firstPerson") + ": " + I18n.format("naruto.gui.off"));
            firstPersonButton.enabled = false;
            this.buttonList.add(firstPersonButton);
            /**if(NarutoSettings.experimentalFirstPersonMode == 0){
             this.buttonList.add(new GuiButton(4, guiX + 9, guiY + 20, 110, 20, I18n.format("naruto.gui.firstPerson") + ": " + I18n.format("naruto.gui.on")));
             }
             else if(NarutoSettings.experimentalFirstPersonMode == 1){
             this.buttonList.add(new GuiButton(4, guiX + 9, guiY + 20, 110, 20, I18n.format("naruto.gui.firstPerson") + ": " + I18n.format("naruto.gui.off")));
             }
             else if(NarutoSettings.experimentalFirstPersonMode == 2){
             this.buttonList.add(new GuiButton(4, guiX + 9, guiY + 20, 110, 20, I18n.format("naruto.gui.firstPerson") + ": " + I18n.format("naruto.gui.jutsutoggle")));
             }*/
        } else if (NarutoSettings.settingsPage == 2) {
            this.buttonList.add(new GuiNarutoSlider(0, guiX + 9, guiY + 20, 110, EnumNarutoOptions.CHAKRA_BAR_OFFSETX, I18n.format("naruto.gui.chakraGUIOffset") + " X"));
            this.buttonList.add(new GuiNarutoSlider(0, guiX + 130, guiY + 20, 110, EnumNarutoOptions.CHAKRA_BAR_OFFSETY, I18n.format("naruto.gui.chakraGUIOffset") + " Y"));

            // TODO Finish the damn thing you dirty scrub, noone will love you unless you actually do your work. T_T (I really love myself don't I?)
            this.buttonList.add(new GuiButton(6, guiX + 9, guiY + 43, 110, 20, I18n.format("naruto.gui.chakraGUICorner") + ": " + NarutoSettings.chakraGUICorner));
        } else if (NarutoSettings.settingsPage == 3) {
            this.buttonList.add(new GuiNarutoSlider(0, guiX + 9, guiY + 20, 110, EnumNarutoOptions.JUTSU_DELAY, I18n.format("naruto.gui.jutsuDelay")));
        }

        GuiButton lastPageButton = new GuiButton(2, guiX + 9, guiY + 137, 15, 20, "<");
        lastPageButton.enabled = NarutoSettings.settingsPage > 1;
        this.buttonList.add(lastPageButton);

        GuiButton nextPageButton = new GuiButton(3, guiX + 86, guiY + 137, 15, 20, ">");
        nextPageButton.enabled = NarutoSettings.settingsPage < this.pageTitles.length;
        this.buttonList.add(nextPageButton);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.id == 1) {
            this.mc.displayGuiScreen((GuiScreen) null);
            this.mc.setIngameFocus();
        }
        if (par1GuiButton.id == 2) {
            if(NarutoSettings.settingsPage > 1){
                --NarutoSettings.settingsPage;
                this.needsUpdate = true;
            }
        }
        if (par1GuiButton.id == 3) {
            if(NarutoSettings.settingsPage < this.pageTitles.length){
                ++NarutoSettings.settingsPage;
                this.needsUpdate = true;
            }
        }
        if (par1GuiButton.id == 4) {
            // TODO update the first person to work again
            if (NarutoSettings.experimentalFirstPersonMode >= 2) {
                NarutoSettings.changeSettingInt(EnumNarutoOptions.FIRSTPERSON, 0);
            } else {
                NarutoSettings.changeSettingInt(EnumNarutoOptions.FIRSTPERSON, ++NarutoSettings.experimentalFirstPersonMode);
            }
            this.needsUpdate = true;
        }
        if (par1GuiButton.id == 6) {
            if (NarutoSettings.chakraGUICorner >= 4) {
                NarutoSettings.changeSettingInt(EnumNarutoOptions.CHAKRA_BAR_CORNER, 1);
            } else {
                NarutoSettings.changeSettingInt(EnumNarutoOptions.CHAKRA_BAR_CORNER, ++NarutoSettings.chakraGUICorner);
            }
            this.needsUpdate = true;
        }

        super.actionPerformed(par1GuiButton);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    /**protected void keyTyped(char par1, int par2)
     {
     if (par2 == this.mc.gameSettings.keyBindInventory.keyCode)
     {
     this.mc.displayGuiScreen((GuiScreen)null);
     this.mc.setIngameFocus();
     }
     else
     {
     super.keyTyped(par1, par2);
     }
     }*/

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3) {

        this.drawDefaultBackground();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        this.mc.getTextureManager().bindTexture(guiBackground);

        int guiX = (this.width - this.guiWidth) / 2;
        int guiY = (this.height - this.guiHeight) / 2;

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);


        this.drawTexturedModalRect(guiX, guiY, 0, 0, this.guiWidth, this.guiHeight);

        if(this.needsUpdate){
            this.needsUpdate = false;
            this.updateButtons();
        }


        this.drawTitleAndText();
        
        

        //this.drawDefaultBackground();

        super.drawScreen(par1, par2, par3);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {

    }

    /**
     * Draws the "Achievements" title at the top of the GUI.
     */
    protected void drawTitleAndText() {
        int guiX = (this.width - this.guiWidth) / 2;
        int guiY = (this.height - this.guiHeight) / 2;

        this.fontRendererObj.drawString(I18n.format("naruto.gui.options.title") + " - " + I18n.format(pageTitles[NarutoSettings.settingsPage - 1]), guiX + 12, guiY + 10, 4210752);

        this.fontRendererObj.drawString("Page " + NarutoSettings.settingsPage + " of " + this.pageTitles.length, guiX + 25, guiY + 143, 4210752);
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame() {
        return true;
    }
}
