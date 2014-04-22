package MinecraftCooking;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class ProcessingManager {
  private final ArrayList<ProcessingRecipe> recipes = new ArrayList<ProcessingRecipe>();

  public void addRecipe(ItemStack input, ItemStack output) {
    this.addRecipe(new ProcessingRecipe(input, output));
  }

  public void addRecipe(ItemStack input, ItemStack output, int smeltingDuration) {
    this.addRecipe(new ProcessingRecipe(input, output, smeltingDuration));
  }

  void addRecipe(ProcessingRecipe recipe) {
    this.recipes.add(recipe);
  }

  public ProcessingRecipe getRecipe(ItemStack input) {
    if (input == null || input.stackSize == 0) {
      return null;
    }

    for (ProcessingRecipe recipe : this.recipes) {
      if (recipe.isCreatableBy(input)) {
        return recipe;
      }
    }

    return null;
  }

  public class ProcessingRecipe {
    public static final int DEFAULT_PROCESSING_TIME = 200;

    public final ItemStack input;
    public final ItemStack output;
    public final int processingTime;

    public ProcessingRecipe(ItemStack in, ItemStack out, int duration) {
      this.input = in.copy();
      this.output = out.copy();
      this.processingTime = duration;
    }

    public ProcessingRecipe(ItemStack in, ItemStack out) {
      this(in, out, DEFAULT_PROCESSING_TIME);
    }

    public boolean isCreatableBy(ItemStack input) {
      return input != null
          && input.isItemEqual(this.input)
          && input.stackSize >= this.input.stackSize;
    }

    @Override
    public String toString() {
      return String.valueOf(this.input) + " -" + this.processingTime + "-> " + this.output;
    }
  }
}
