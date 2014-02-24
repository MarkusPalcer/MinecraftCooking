package MinecraftCooking.rendering.renderers;

import MinecraftCooking.machines.Grindstone.GrindstoneTile;
import MinecraftCooking.rendering.models.GrindstoneModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;

public class GrindstoneRenderer extends TileEntitySpecialRenderer implements IItemRenderer {
  private GrindstoneModel model = new GrindstoneModel();

  @Override
  public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
    GrindstoneTile tile = (GrindstoneTile) tileEntity;
    model.UpperSlab.rotateAngleY = (float) tile.getCookProgressScaled(3600) / 3600 * (float) Math.PI * 2.0F;
    model.render(x, y, z);

  }

  @Override
  public boolean handleRenderType(ItemStack item, IItemRenderer.ItemRenderType type) {
    return true;
  }

  @Override
  public boolean shouldUseRenderHelper(IItemRenderer.ItemRenderType type, ItemStack item, IItemRenderer.ItemRendererHelper helper) {
    return true;
  }

  @Override
  public void renderItem(IItemRenderer.ItemRenderType type, ItemStack item, Object... data) {
    switch (type) {
      case ENTITY: {
        model.renderInventory(0f, 0f, 0f);
        return;
      }

      case EQUIPPED: {
        model.renderInventory(0f, 1f, 1f);
        return;
      }

      case INVENTORY: {
        model.renderInventory(0f, 0f, 0f);
        return;
      }

      default:
        return;
    }
  }
}


