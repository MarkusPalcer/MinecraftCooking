package mods.cooking.fluids;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class TextureProxyBlock extends Block {

	public Icon Milk;
	
	public TextureProxyBlock(int id) {
		super(id, Material.water);
		
		setUnlocalizedName("cookingFluidTextureProxy");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister reg) {
		super.registerIcons(reg);
		
		this.Milk = reg.registerIcon("cooking:milk");
	}
}
