package mods.cooking.machines.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.cooking.BaseClass;
import mods.cooking.RecipeManager;
import mods.cooking.gui.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class CookingTable extends Block {
    @SideOnly(Side.CLIENT)
    private Icon iconTop;
    @SideOnly(Side.CLIENT)
    private Icon iconFront;
    
    public static final RecipeManager recipes = new RecipeManager();
    
	public CookingTable(int id) {
		super(id, Material.wood);
		setHardness(2.5F);
		setStepSound(soundWoodFootstep);
		setUnlocalizedName("cookingtable");
		setCreativeTab(BaseClass.tab);	
	}

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.iconTop : (par1 == 0 ? Block.planks.getBlockTextureFromSide(par1) : (par1 != 2 && par1 != 4 ? this.blockIcon : this.iconFront));
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("cooking:cookingtable_side");
        this.iconTop = par1IconRegister.registerIcon("cooking:cookingtable_top");
        this.iconFront = par1IconRegister.registerIcon("cooking:cookingtable_front");
    }

	@Override
	public boolean onBlockActivated(World world, int x, int y,
			int z, EntityPlayer player, int par6, float par7,
			float par8, float par9) {
		if (world.isRemote) {
			return true;
		} else {
			player.openGui(BaseClass.instance, GuiHandler.COOKING_TABLE, world, x, y, z);
			return true;
		}	
	}
}
