package MinecraftCooking;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;
import java.util.List;

public class HelperFunctions {
    public static int removeWorkbenchRecipesFor(Item outcome) {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        int removed = 0;

        for (IRecipe recipe : recipes) {
          ItemStack output = recipe.getRecipeOutput();
          if (output != null && output.getItem() == outcome) {
                recipes.remove(recipe);
                removed++;
            }
        }

        return removed;
    }

    public static List<IRecipe> getWorkbenchRecipesFor(Item outcome) {
        List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
        List<IRecipe> result = new ArrayList<IRecipe>();

        for (IRecipe recipe : recipes) {
          ItemStack output = recipe.getRecipeOutput();
          if (output != null && output.getItem() == outcome) {
              result.add(recipe);
          }
        }

        return result;
    }
}
