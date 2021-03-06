package MinecraftCooking.items;

import MinecraftCooking.BaseClass;
import MinecraftCooking.machines.CookingTable.CookingTableBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.List;

public class Items {
  public static Item Flour;
  private static Item Dough;
  private static Item DoughPiece;
  private static ItemFood Breadroll;
  private static ItemFood Fishroll;

  public static void Init() {
    Dough = new CookingItem("dough");
    Flour = new CookingItem("flour");
    DoughPiece = new CookingItem("doughpiece");
    Breadroll = new CookingFood("breadroll", 1);
    Fishroll = new CookingFood("fishroll", 6);

    if (!BaseClass.config.get("general", "keepVanillaBreadRecipe", false).getBoolean(false)) {
      RemoveVanillaBreadRecipe();
    }

    AddCookingTableRecipes();

    GameRegistry.addSmelting(Items.Dough, new ItemStack(net.minecraft.init.Items.bread), 0.0f);
    GameRegistry.addSmelting(Items.DoughPiece, new ItemStack(Items.Breadroll), 0.0f);

		/*
    try {
			forestry.api.recipes.RecipeManagers.carpenterManager.addRecipe(1, null, new ItemStack(Items.Dough), new Object[] {"xx", 'x', new ItemStack(Items.Flour)});
		} catch (Exception e) {
			System.out.println("Could not register carpenter recipe for dough to forestry.");
		}*/
  }

  private static void AddCookingTableRecipes() {
    AddCookingTableRecipe(Items.Flour, net.minecraft.init.Items.wheat, net.minecraft.init.Items.wheat);
    AddCookingTableRecipe(Items.Dough, Items.Flour, Items.Flour, net.minecraft.init.Items.water_bucket);
    AddCookingTableRecipe(new ItemStack(Items.DoughPiece, 4), Items.Dough);
    AddCookingTableRecipe(Items.Dough, Items.DoughPiece, Items.DoughPiece, Items.DoughPiece, Items.DoughPiece);
    AddCookingTableRecipe(Items.Fishroll, Items.Breadroll, net.minecraft.init.Items.cooked_fished);
  }

  private static void AddCookingTableRecipe(Item output, Object... input) {
    ItemStack outputStack = new ItemStack(output, 1);

    AddCookingTableRecipe(outputStack, input);
  }

  private static void AddCookingTableRecipe(ItemStack outputStack, Object... input) {
    if (BaseClass.config.get("general", "enableCookingTable", true).getBoolean(true)) {
      if (BaseClass.config.get("cookingTableRecipes", outputStack.getItem().getUnlocalizedName(), true).getBoolean(true)) {
        CookingTableBlock.recipes.addShapelessRecipe(outputStack, input);
      }

      if (BaseClass.config.get("deprecatedWorkbenchRecipes", outputStack.getItem().getUnlocalizedName(), false).getBoolean(false)) {
        CraftingManager.getInstance().addShapelessRecipe(outputStack, input);
      }
    } else {
      CraftingManager.getInstance().addShapelessRecipe(outputStack, input);
    }
  }

  private static void RemoveVanillaBreadRecipe() {
    List recipes = CraftingManager.getInstance().getRecipeList();
    for (Object item : recipes) {
      if (item instanceof IRecipe) {
        IRecipe recipe = (IRecipe) item;
        if (ItemStack.areItemStacksEqual(recipe.getRecipeOutput(), new ItemStack(net.minecraft.init.Items.bread))) {
          recipes.remove(recipe);
          break;
        }
      }
    }
  }
}
