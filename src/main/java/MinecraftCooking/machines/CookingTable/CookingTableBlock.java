package MinecraftCooking.machines.CookingTable;

import MinecraftCooking.BaseClass;
import MinecraftCooking.gui.GuiHandler;
import MinecraftCooking.RecipeManager;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class CookingTableBlock extends Block {
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconFront;

    public static final RecipeManager recipes = new RecipeManager();

	public CookingTableBlock() {
		super(Material.wood);
		setHardness(2.5F);
		//setStepSound(soundWoodFootstep);
		setBlockName("cookingtable");
		setCreativeTab(BaseClass.tab);
	}

    public static Block Create() {
        CookingTableBlock result = new CookingTableBlock();
        GameRegistry.registerBlock(result, "cooking:cookingtable");
        LanguageRegistry.addName(result, "Food preparation table");
        GameRegistry.addShapelessRecipe(new ItemStack(result), new ItemStack(Blocks.crafting_table), new ItemStack(Items.bucket));

        return result;
    }

    @SideOnly(Side.CLIENT)

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public IIcon getIcon(int par1, int par2)
    {
        return par1 == 1 ? this.iconTop : (par1 == 0 ? Blocks.planks.getBlockTextureFromSide(par1) : (par1 != 2 && par1 != 4 ? this.blockIcon : this.iconFront));
    }

    @SideOnly(Side.CLIENT)

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IIconRegister par1IconRegister)
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
