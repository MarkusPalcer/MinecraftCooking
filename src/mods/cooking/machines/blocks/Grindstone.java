package mods.cooking.machines.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.cooking.BaseClass;
import mods.cooking.ProcessingManager;
import mods.cooking.gui.GuiHandler;
import mods.cooking.items.Items;
import mods.cooking.machines.tiles.GrindstoneTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;

public class Grindstone extends BlockContainer {

	private Icon topIcon;

	public static final String name = "grindstone";
	
	public static ProcessingManager recipes = new ProcessingManager();
	
	public static Grindstone Create(Configuration config) {
		Grindstone retVal = new Grindstone(config.getBlock(name, 1026).getInt());
		
		GameRegistry.registerBlock(retVal, BaseClass.prefix + Grindstone.name);
		LanguageRegistry.addName(retVal, "Grindstone");
		
		GameRegistry.addShapedRecipe(new ItemStack(retVal, 1), new Object[] { "xxx", " y ", "xxx", 'x', new ItemStack(Block.stoneSingleSlab, 1), 'y', new ItemStack(Item.stick, 1)});
		
		AddRecipes(config);
		
		return retVal;
	}
	
	private static void AddRecipes(Configuration config) {
		Grindstone.recipes.addRecipe(new ItemStack(Item.wheat, 1), new ItemStack(Items.Flour, 1));
		AddVanillaRecipe(new ItemStack(Item.bone, 1), new ItemStack(Item.dyePowder, 3, 15));
	}
	
	private static void AddVanillaRecipe(ItemStack input,
			ItemStack output) {
		// TODO Auto-generated method stub
		
	}

	public Grindstone(int id)  {
		super(id, Material.rock);
		setHardness(3.5F);
		setStepSound(soundStoneFootstep);
		setUnlocalizedName("grindstone");
		setCreativeTab(BaseClass.tab);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2) {
		return par1 == 1 ? this.topIcon : this.blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		this.blockIcon = par1IconRegister.registerIcon("cooking:grindstone_side");
		this.topIcon = Block.furnaceIdle.getIcon(1, 0);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new GrindstoneTile();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if (world.isRemote) {
			return true;
		} else {
			player.openGui(BaseClass.instance, GuiHandler.GRINDSTONE, world, x, y, z);
			return true;
		}
	}
}
