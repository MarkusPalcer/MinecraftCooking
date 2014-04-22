package MinecraftCooking.gui;

import MinecraftCooking.BaseClass;
import MinecraftCooking.machines.Grindstone.GrindstoneTile;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

class GrindstoneGui extends GuiContainer {
  private static final String title = "gui.grindstone.title";
  private static final ResourceLocation background = new ResourceLocation(BaseClass.prefix + "textures/gui/grindstone.png");
  private final GrindstoneTile tile;

  public GrindstoneGui(InventoryPlayer par1InventoryPlayer, GrindstoneTile tile) {
    super(new GrindstoneContainer(par1InventoryPlayer, tile));
    this.tile = tile;
  }

  /**
   * Draw the foreground layer for the GuiContainer (everything in front of the items)
   */
  protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    String s = StatCollector.translateToLocal(title);
    this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
    this.fontRendererObj.drawString(StatCollector.translateToLocal("gui.generic.inventory"), 8, this.ySize - 96 + 2, 4210752);
  }

  /**
   * Draw the background layer for the GuiContainer (everything behind the items)
   */
  protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.getTextureManager().bindTexture(background);
    int k = (this.width - this.xSize) / 2;
    int l = (this.height - this.ySize) / 2;
    this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    int i1;

    i1 = this.tile.getCookProgressScaled(24);
    this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
  }
}
