package MinecraftCooking.buildcraft.machines;

import MinecraftCooking.buildcraft.machines.Grindstone.GrindstoneTileBuildCraft;
import MinecraftCooking.machines.Grindstone.GrindstoneBlock;
import buildcraft.BuildCraftFactory;
import MinecraftCooking.buildcraft.machines.Milker.MilkerBlock;
import MinecraftCooking.buildcraft.machines.Milker.MilkerTile;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BuildCraftMachines {
    private static Block Milker;

	public static final int MAX_ENERGY = 15000;

    public static void Init() {
      GrindstoneBlock.entityClass = GrindstoneTileBuildCraft.class;

      Milker = new MilkerBlock();
      GameRegistry.registerBlock(Milker, "cooking:milker");
      LanguageRegistry.addName(Milker, "Milker");
      GameRegistry.addShapelessRecipe(new ItemStack(Milker), new ItemStack(BuildCraftFactory.pumpBlock), new ItemStack(Items.milk_bucket));
      GameRegistry.registerTileEntity(MilkerTile.class, "cooking:milker");
    }
}
