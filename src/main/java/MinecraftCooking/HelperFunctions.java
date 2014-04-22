package MinecraftCooking;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.List;

public class HelperFunctions {
  public static void removeWorkbenchRecipesFor(Item outcome) {
    List recipes = CraftingManager.getInstance().getRecipeList();

    for (Object item : recipes) {
      if (item instanceof IRecipe) {
        IRecipe recipe = (IRecipe) item;
        ItemStack output = recipe.getRecipeOutput();

        if (output != null && output.getItem() == outcome) {
          recipes.remove(recipe);
        }
      }
    }
  }
}
