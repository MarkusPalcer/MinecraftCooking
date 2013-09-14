package mods.cooking.gui;

import org.lwjgl.opengl.GL11;

import mods.cooking.machines.tiles.GrindstoneTile;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

public class GrindstoneGui extends GuiContainer {
	private static final ResourceLocation background = new ResourceLocation("cooking", "textures/gui/grindstone.png");
    private GrindstoneTile tile;

    public GrindstoneGui(InventoryPlayer par1InventoryPlayer, GrindstoneTile tile)
    {
        super(new GrindstoneContainer(par1InventoryPlayer, tile));
        this.tile = tile;
    }

    @Override
	public void onGuiClosed() {
		super.onGuiClosed();
	}
    
    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        String s = this.tile.isInvNameLocalized() ? this.tile.getInvName() : I18n.func_135053_a(this.tile.getInvName());
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.func_135053_a("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.func_110434_K().func_110577_a(background);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;

        i1 = this.tile.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
    }
}
