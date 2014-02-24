package MinecraftCooking.machines;

import buildcraft.BuildCraftFactory;
import MinecraftCooking.machines.Milker.MilkerBlock;
import MinecraftCooking.machines.Milker.MilkerTile;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BuildCraftMachines {
    public static Block Milker;

    public static void Init() {
        Milker = new MilkerBlock();
        GameRegistry.registerBlock(Milker, "cooking:milker");
        LanguageRegistry.addName(Milker, "Milker");
        GameRegistry.addShapelessRecipe(new ItemStack(Milker), new ItemStack(BuildCraftFactory.pumpBlock), new ItemStack(Items.milk_bucket));
        GameRegistry.registerTileEntity(MilkerTile.class, "cooking:milker");
    }
}
