package MinecraftCooking.gui;

import MinecraftCooking.BaseClass;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
class GuiCookingTable extends GuiContainer {

  public GuiCookingTable(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5) {
    super(new ContainerCookingTable(par1InventoryPlayer, par2World, par3, par4, par5));
  }

  /**
   * Draw the foreground layer for the GuiContainer (everything in front of the items)
   */
  protected void drawGuiContainerForegroundLayer(int par1, int par2) {
    this.fontRendererObj.drawString(StatCollector.translateToLocal("gui.cookingtable.title"), 28, 6, 4210752);
    this.fontRendererObj.drawString(StatCollector.translateToLocal("gui.generic.inventory"), 8, this.ySize - 96 + 2, 4210752);
  }

  /**
   * Draw the background layer for the GuiContainer (everything behind the items)
   */
  protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    this.mc.renderEngine.bindTexture(new ResourceLocation(BaseClass.prefix + "textures/gui/crafting.png"));
    int k = (this.width - this.xSize) / 2;
    int l = (this.height - this.ySize) / 2;
    this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
  }
}
