package MinecraftCooking.items;

import MinecraftCooking.BaseClass;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

public class CookingItem extends Item {

	public CookingItem(String key, String defaultName) {
		super();

		this.setUnlocalizedName(key);
		this.setCreativeTab(BaseClass.tab);

		GameRegistry.registerItem(this, "cooking:" + key);
		LanguageRegistry.addName(this, defaultName);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegistry) {
		this.itemIcon = iconRegistry.registerIcon("cooking:" + this.getUnlocalizedName());
	}
}
