package MinecraftCooking;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecipeManager {
  private final List recipes = new ArrayList();

  public ShapedRecipes addRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj) {
    String s = "";
    int i = 0;
    int j = 0;
    int k = 0;

    if (par2ArrayOfObj[i] instanceof String[]) {
      String[] strings = (String[]) par2ArrayOfObj[i++];

      for (String s1 : strings) {
        ++k;
        j = s1.length();
        s = s + s1;
      }
    } else {
      while (par2ArrayOfObj[i] instanceof String) {
        String s2 = (String) par2ArrayOfObj[i++];
        ++k;
        j = s2.length();
        s = s + s2;
      }
    }

    HashMap hashmap;

    for (hashmap = new HashMap(); i < par2ArrayOfObj.length; i += 2) {
      Character character = (Character) par2ArrayOfObj[i];
      ItemStack stack = null;

      if (par2ArrayOfObj[i + 1] instanceof Item) {
        stack = new ItemStack((Item) par2ArrayOfObj[i + 1]);
      } else if (par2ArrayOfObj[i + 1] instanceof Block) {
        stack = new ItemStack((Block) par2ArrayOfObj[i + 1], 1, 32767);
      } else if (par2ArrayOfObj[i + 1] instanceof ItemStack) {
        stack = (ItemStack) par2ArrayOfObj[i + 1];
      }

      hashmap.put(character, stack);
    }

    ItemStack[] stacks = new ItemStack[j * k];

    for (int i1 = 0; i1 < j * k; ++i1) {
      char c0 = s.charAt(i1);

      if (hashmap.containsKey(Character.valueOf(c0))) {
        stacks[i1] = ((ItemStack) hashmap.get(Character.valueOf(c0))).copy();
      } else {
        stacks[i1] = null;
      }
    }

    ShapedRecipes shapedrecipes = new ShapedRecipes(j, k, stacks, par1ItemStack);
    this.recipes.add(shapedrecipes);
    return shapedrecipes;
  }

  public void addShapelessRecipe(ItemStack par1ItemStack, Object... par2ArrayOfObj) {
    ArrayList arraylist = new ArrayList();

    for (Object object1 : par2ArrayOfObj) {
      if (object1 instanceof ItemStack) {
        arraylist.add(((ItemStack) object1).copy());
      } else if (object1 instanceof Item) {
        arraylist.add(new ItemStack((Item) object1));
      } else {
        if (!(object1 instanceof Block)) {
          throw new RuntimeException("Invalid shapeless recipe!");
        }

        arraylist.add(new ItemStack((Block) object1));
      }
    }

    this.recipes.add(new ShapelessRecipes(par1ItemStack, arraylist));
  }

  public ItemStack findMatchingRecipe(InventoryCrafting par1InventoryCrafting, World par2World) {
    int i = 0;
    ItemStack itemstack = null;
    ItemStack stack = null;
    int j;

    for (j = 0; j < par1InventoryCrafting.getSizeInventory(); ++j) {
      ItemStack stack1 = par1InventoryCrafting.getStackInSlot(j);

      if (stack1 != null) {
        if (i == 0) {
          itemstack = stack1;
        }

        if (i == 1) {
          stack = stack1;
        }

        ++i;
      }
    }

    if (i == 2 && itemstack != null && stack != null && itemstack.isItemEqual(stack) && itemstack.stackSize == 1 && stack.stackSize == 1 && itemstack.getItem().isRepairable()) {
      Item item = itemstack.getItem();
      int k = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
      int l = item.getMaxDamage() - stack.getItemDamageForDisplay();
      int i1 = k + l + item.getMaxDamage() * 5 / 100;
      int j1 = item.getMaxDamage() - i1;

      if (j1 < 0) {
        j1 = 0;
      }

      return new ItemStack(itemstack.getItem(), 1, j1);
    } else {
      for (j = 0; j < this.recipes.size(); ++j) {
        IRecipe irecipe = (IRecipe) this.recipes.get(j);

        if (irecipe.matches(par1InventoryCrafting, par2World)) {
          return irecipe.getCraftingResult(par1InventoryCrafting);
        }
      }

      return null;
    }
  }

  public List getRecipeList() {
    return this.recipes;
  }
}
