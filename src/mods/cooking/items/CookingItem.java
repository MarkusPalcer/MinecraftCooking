package mods.cooking.items;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.cooking.BaseClass;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;

public class CookingItem extends Item {

	public CookingItem(Configuration config, int defaultID, String key, String defaultName) {
		super(config.getItem(key, defaultID).getInt());
		
		this.setUnlocalizedName(key);
		this.setCreativeTab(BaseClass.tab);
		
		GameRegistry.registerItem(this, "cooking:" + key);
		LanguageRegistry.addName(this, defaultName);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegistry) {
		this.itemIcon = iconRegistry.registerIcon("cooking:" + this.getUnlocalizedName());
	}
	
	

}
