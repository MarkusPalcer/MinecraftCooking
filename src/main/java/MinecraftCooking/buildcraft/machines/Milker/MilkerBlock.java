package MinecraftCooking.buildcraft.machines.Milker;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import MinecraftCooking.BaseClass;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class MilkerBlock extends BlockContainer {
	private IIcon textureTop;
	private IIcon textureSide;

	public MilkerBlock() {
		super(Material.rock);

		setHardness(1.5F);
		setResistance(10F);
		//todo:setStepSound(soundStoneFootstep);
		setCreativeTab(BaseClass.tab);
	}



	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		this.textureSide = par1IconRegister.registerIcon("cooking:milker_side");
		this.textureTop = par1IconRegister.registerIcon("cooking:milker_top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		// If no metadata is set, then this is an icon.
		if (j == 0 && i == 3)
			return textureSide;

		if (i == j)
			return textureSide;

		switch (i) {
		case 1:
			return textureTop;
		default:
			return textureSide;
		}
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase,
				par6ItemStack);
		MilkerTile tile = (MilkerTile) par1World.getTileEntity(par2, par3, par4);
		if (tile == null) return;

		tile.searchArea = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D).addCoord(par2, par3, par4).expand(5.0D, 5.0D, 2.0D);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new MilkerTile();
	}
}
