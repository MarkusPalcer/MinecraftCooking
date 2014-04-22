package MinecraftCooking.items;

import MinecraftCooking.BaseClass;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

class CookingItem extends Item {

  public CookingItem(String key) {
    super();

    this.setUnlocalizedName(key);
    this.setCreativeTab(BaseClass.tab);

    GameRegistry.registerItem(this, BaseClass.prefix + key);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister iconRegistry) {
    this.itemIcon = iconRegistry.registerIcon(BaseClass.prefix + this.getUnlocalizedName());
  }
}
