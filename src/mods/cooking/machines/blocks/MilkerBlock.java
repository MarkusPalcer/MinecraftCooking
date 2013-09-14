package mods.cooking.machines.blocks;

import java.util.List;

import buildcraft.api.power.IPowerReceptor;
import buildcraft.core.BlockBuildCraft;
import buildcraft.factory.TileQuarry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.cooking.BaseClass;
import mods.cooking.machines.tiles.MilkerTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class MilkerBlock extends BlockContainer {
	private Icon textureTop;
	private Icon textureSide;
	
	public MilkerBlock(int id) {
		super(id, Material.rock);
		
		setHardness(1.5F);
		setResistance(10F);
		setStepSound(soundStoneFootstep);
		setCreativeTab(BaseClass.tab);		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.textureSide = par1IconRegister.registerIcon("cooking:milker_side");
		this.textureTop = par1IconRegister.registerIcon("cooking:milker_top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int i, int j) {
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
		MilkerTile tile = (MilkerTile) par1World.getBlockTileEntity(par2, par3, par4);
		if (tile == null) return;
		
		tile.searchArea = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D).addCoord(par2, par3, par4).expand(5.0D, 5.0D, 2.0D);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new MilkerTile();
	}
}
