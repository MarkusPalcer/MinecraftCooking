package mods.cooking.items;

import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import mods.cooking.BaseClass;
import mods.cooking.RecipeManager;
import mods.cooking.machines.blocks.CookingTable;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.Configuration;

public class Items {
	public static Item Flour;
	public static Item Dough;
	public static Item DoughPiece;
	public static ItemFood Breadroll;
	public static ItemFood Fishroll;
	
	public static void Init(Configuration config) {
		Dough = new CookingItem(config, 8140, "dough", "Dough");
		Flour = new CookingItem(config, 8141, "flour", "Flour");
		DoughPiece = new CookingItem(config, 8142, "doughpiece", "Piece of dough");
		Breadroll = new CookingFood(config, 8143, "breadroll", "Breadroll", 1);
		Fishroll = new CookingFood(config, 8144, "fishroll", "Fishroll", 6);
		
		if (!config.get("general", "keepVanillaBreadRecipe", false).getBoolean(false)) {
			RemoveVanillaBreadRecipe();
		}
		
		AddCookingTableRecipes(config);
		
		GameRegistry.addSmelting(Items.Dough.itemID, new ItemStack(Item.bread), 0.0f);
		GameRegistry.addSmelting(Items.DoughPiece.itemID, new ItemStack(Items.Breadroll), 0.0f);
		
		/*
		try {
			forestry.api.recipes.RecipeManagers.carpenterManager.addRecipe(1, null, new ItemStack(Items.Dough), new Object[] {"xx", 'x', new ItemStack(Items.Flour)});
		} catch (Exception e) {
			System.out.println("Could not register carpenter recipe for dough to forestry.");
		}*/
	}

	private static void AddCookingTableRecipes(Configuration config) {
		AddCookingTableRecipe(config, Items.Flour, Item.wheat, Item.wheat);
		AddCookingTableRecipe(config, Items.Dough, Items.Flour, Items.Flour, Item.bucketWater);
		AddCookingTableRecipe(config, new ItemStack(Items.DoughPiece, 4), Items.Dough);
		AddCookingTableRecipe(config, Items.Dough, Items.DoughPiece, Items.DoughPiece, Items.DoughPiece, Items.DoughPiece);
		AddCookingTableRecipe(config, Items.Fishroll, Items.Breadroll, Item.fishCooked);
	}
	
	private static void AddCookingTableRecipe(Configuration config, Item output, Object ... input) {
		ItemStack outputStack = new ItemStack(output, 1);
		
		AddCookingTableRecipe(config, outputStack, input);		
	}

	private static void AddCookingTableRecipe(Configuration config, ItemStack outputStack, Object... input) {
		if (config.get("general", "enableCookingTable", true).getBoolean(true)) {
			if (config.get("cookingTableRecipes", outputStack.getItem().getUnlocalizedName(), true).getBoolean(true)) {
				CookingTable.recipes.addShapelessRecipe(outputStack, input);
			}
			
			if (config.get("deprecatedWorkbenchRecipes", outputStack.getItem().getUnlocalizedName(), false).getBoolean(false)) {
				CraftingManager.getInstance().addShapelessRecipe(outputStack, input);
			}
		} else {
			CraftingManager.getInstance().addShapelessRecipe(outputStack, input);
		}
	}

	private static void RemoveVanillaBreadRecipe() {
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		for (IRecipe recipe : recipes) {
			if (ItemStack.areItemStacksEqual(recipe.getRecipeOutput(), new ItemStack(Item.bread))) {
				recipes.remove(recipe);
				break;
			}
		}
	}
}
