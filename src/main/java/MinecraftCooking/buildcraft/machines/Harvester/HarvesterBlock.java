package MinecraftCooking.buildcraft.machines.Harvester;

import buildcraft.BuildCraftCore;
import MinecraftCooking.BaseClass;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@SuppressWarnings("FieldCanBeLocal")
public class HarvesterBlock extends Block {
    private static final String name = "harvester";

    public static HarvesterBlock Create() {
       HarvesterBlock result = new HarvesterBlock(Material.rock);

       GameRegistry.registerBlock(result, BaseClass.prefix + name);
       LanguageRegistry.addName(result, "Harvester");
       GameRegistry.addRecipe(new ItemStack(result), " X ", "   ",  "Y Y", 'X', Items.diamond_hoe, 'Y', BuildCraftCore.ironGearItem);

       return result;
    }

    private HarvesterBlock(Material par2Material) {
        super(par2Material);
        setHardness(2.5F);
        setBlockName("harvester");
        setCreativeTab(BaseClass.tab);
    }
}
