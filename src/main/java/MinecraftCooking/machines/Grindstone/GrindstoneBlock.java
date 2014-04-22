package MinecraftCooking.machines.Grindstone;

import MinecraftCooking.BaseClass;
import MinecraftCooking.gui.GuiHandler;
import MinecraftCooking.HelperFunctions;
import MinecraftCooking.ProcessingManager;
import MinecraftCooking.items.Items;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GrindstoneBlock extends BlockContainer {

	public static final String name = "grindstone";

	public static ProcessingManager recipes = new ProcessingManager();

  public static Class entityClass;

	public static GrindstoneBlock Create() {
		GrindstoneBlock retVal = new GrindstoneBlock();

		GameRegistry.registerBlock(retVal, BaseClass.prefix + name);
		LanguageRegistry.addName(retVal, "Grindstone");

		GameRegistry.addShapedRecipe(new ItemStack(retVal, 1), new Object[] { "xxx", " y ", "xxx", 'x', new ItemStack(Blocks.stone_slab, 1), 'y', new ItemStack(net.minecraft.init.Items.stick, 1)});

		AddRecipes();

    if (entityClass == null)
    {
        entityClass = GrindstoneTileNoBuildCraft.class;
    }

    GameRegistry.registerTileEntity(entityClass, BaseClass.prefix + name);

    return retVal;
	}

    private static void AddRecipes() {
		GrindstoneBlock.recipes.addRecipe(new ItemStack(net.minecraft.init.Items.wheat, 2), new ItemStack(Items.Flour, 1), 400);
        RemoveVanillaRecipeFor(Items.Flour);
	}

    private static void RemoveVanillaRecipeFor(Item item) {
        if (!BaseClass.config.get(GrindstoneBlock.name, "keepVanilla" + item.getUnlocalizedName() + "Recipe", "false").getBoolean(false)) {
            HelperFunctions.removeWorkbenchRecipesFor(item);
        }
    }

    protected GrindstoneBlock()  {
		super(Material.rock);
		setHardness(3.5F);
		//todo: setStepSound(soundStoneFootstep);
		setBlockName("grindstone");
		setCreativeTab(BaseClass.tab);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
        try {
            return (GrindstoneTile) entityClass.newInstance();
        } catch (InstantiationException e) {
            return new GrindstoneTileNoBuildCraft();
        } catch (IllegalAccessException e) {
            return new GrindstoneTileNoBuildCraft();
        }
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

    @Override
    public boolean hasTileEntity(int metadata)
    {
        return true;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }
}
