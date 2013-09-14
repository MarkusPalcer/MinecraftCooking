package mods.cooking.machines;

import buildcraft.BuildCraftFactory;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import mods.cooking.BaseClass;
import mods.cooking.items.Items;
import mods.cooking.machines.blocks.Grindstone;
import mods.cooking.machines.blocks.MilkerBlock;
import mods.cooking.machines.blocks.CookingTable;
import mods.cooking.machines.tiles.GrindstoneTile;
import mods.cooking.machines.tiles.MilkerTile;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;

public class Machines {
	public static Block Milker;
	public static Block CookingTable;
	public static Grindstone Grindstone;
	
	public static void Init(Configuration config) {
		Milker = new MilkerBlock(config.getBlock("milker", 1024).getInt());
		CookingTable = new CookingTable(config.getBlock("cookingtable", 1025).getInt());
		Grindstone = Grindstone.Create(config);
		
		GameRegistry.registerBlock(Milker, "cooking:milker");
		GameRegistry.registerBlock(CookingTable, "cooking:cookingtable");
		
		LanguageRegistry.addName(Milker, "Milker");
		LanguageRegistry.addName(CookingTable, "Food preparation table");
		
		GameRegistry.registerTileEntity(MilkerTile.class, "cooking:milker");
		GameRegistry.registerTileEntity(GrindstoneTile.class, BaseClass.prefix + Grindstone.name);
		
		GameRegistry.addShapelessRecipe(new ItemStack(Milker), new ItemStack(BuildCraftFactory.pumpBlock), new ItemStack(Item.bucketMilk));
		GameRegistry.addShapelessRecipe(new ItemStack(CookingTable), new ItemStack(Block.workbench), new ItemStack(Item.bucketEmpty));
	}

}
