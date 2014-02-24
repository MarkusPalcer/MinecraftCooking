package MinecraftCooking.machines.Grindstone;

import MinecraftCooking.ProcessingManager;
import MinecraftCooking.ProcessingManager.ProcessingRecipe;
import MinecraftCooking.network.SynchronizedTile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;

public abstract class GrindstoneTile extends SynchronizedTile implements ISidedInventory {
  private static final int[] slots_top = new int[]{0};
  private static final int[] slots_bottom = new int[]{1};
  private static final int[] slots_sides = new int[]{0, 1};
  public static final String name = "tile.grindstone.name";
  public int processingTime;
  public ArrayList<Integer> openedByPlayers = new ArrayList<Integer>();
  public int processingTimeNeeded;
  protected ItemStack[] itemStacks = new ItemStack[2];
  protected ProcessingManager.ProcessingRecipe currentRecipe = null;

  /**
   * Returns the number of slots in the inventory.
   */
  public int getSizeInventory() {
    return this.itemStacks.length;
  }

  /**
   * Returns the stack in slot i
   */
  public ItemStack getStackInSlot(int par1) {
    return this.itemStacks[par1];
  }

  /**
   * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
   * new stack.
   */
  public ItemStack decrStackSize(int par1, int par2) {
    if (this.itemStacks[par1] != null) {
      ItemStack itemstack;

      if (this.itemStacks[par1].stackSize <= par2) {
        itemstack = this.itemStacks[par1];
        this.itemStacks[par1] = null;
        return itemstack;
      } else {
        itemstack = this.itemStacks[par1].splitStack(par2);

        if (this.itemStacks[par1].stackSize == 0) {
          this.itemStacks[par1] = null;
        }

        return itemstack;
      }
    } else {
      return null;
    }
  }

  /**
   * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
   * like when you close a workbench GUI.
   */
  public ItemStack getStackInSlotOnClosing(int par1) {
    if (this.itemStacks[par1] != null) {
      ItemStack itemstack = this.itemStacks[par1];
      this.itemStacks[par1] = null;
      return itemstack;
    } else {
      return null;
    }
  }

  /**
   * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
   */
  public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
    this.itemStacks[par1] = par2ItemStack;

    if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
      par2ItemStack.stackSize = this.getInventoryStackLimit();
    }
  }

  @Override
  public String getInventoryName() {
    return name;
  }

  @Override
  public boolean hasCustomInventoryName() {
    return false;
  }

  public int getInventoryStackLimit() {
    return 64;
  }

  /**
   * Do not make give this method the name canInteractWith because it clashes with Container
   */
  public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
    return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && par1EntityPlayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
  }

  /**
   * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
   */
  public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
    return par1 != 1;
  }

  @SideOnly(Side.CLIENT)
  public int getCookProgressScaled(int par1) {
    if (this.processingTimeNeeded == 0) {
      return 0;
    } else {
      return this.processingTime * par1 / this.processingTimeNeeded;
    }
  }

  @Override
  public void openInventory() {  }

  @Override
  public void closeInventory() {  }

  public void open(int playerId) {
    if (!this.openedByPlayers.contains(playerId))
      this.openedByPlayers.add(playerId);
  }

  public void close(int playerId) {
    if (this.openedByPlayers.contains(playerId))
      this.openedByPlayers.remove((Integer) playerId);
  }

  /**
   * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
   * ticks and creates a new spawn inside its implementation.
   */
  public void updateEntity() {
    if (this.worldObj.isRemote) {
      return;
    }

    if (this.currentRecipe == null) {
      ProcessingRecipe oldRecipe = this.currentRecipe;
      this.currentRecipe = this.getMatchingRecipe();

      if (oldRecipe != this.currentRecipe) {
        this.processingTimeNeeded = this.currentRecipe.processingTime;
        this.sendTileUpdate();
      }
    }

    if (this.currentRecipe != null) {
      this.process();
    }
  }

  protected abstract void process();

  private ProcessingRecipe getMatchingRecipe() {
    ProcessingManager.ProcessingRecipe recipe = GrindstoneBlock.recipes.getRecipe(this.itemStacks[0]);

    if (recipe == null) return null;

    if (this.itemStacks[1] == null) return recipe;

    if (!this.itemStacks[1].isItemEqual(recipe.output)) return null;

    int result = itemStacks[1].stackSize + recipe.output.stackSize;
    if (result <= getInventoryStackLimit() && result <= recipe.output.getMaxStackSize()) {
      return recipe;
    } else {
      return null;
    }
  }

  public void smeltItem() {
    ItemStack itemstack = this.currentRecipe.output;
    if (this.itemStacks[1] == null) {
      this.itemStacks[1] = itemstack.copy();
    } else if (this.itemStacks[1].isItemEqual(itemstack)) {
      itemStacks[1].stackSize += itemstack.stackSize;
    }

    itemStacks[0].stackSize -= this.currentRecipe.input.stackSize;
    if (itemStacks[0].stackSize == 0) {
      itemStacks[0] = null;
    }
  }

  public void openChest() {
  }

  public void closeChest() {
  }

  /**
   * Returns an array containing the indices of the slots that can be accessed by automation on the given side of this
   * block.
   */
  public int[] getAccessibleSlotsFromSide(int par1) {
    return par1 == 0 ? slots_bottom : (par1 == 1 ? slots_top : slots_sides);
  }

  /**
   * Returns true if automation can insert the given item in the given slot from the given side. Args: Slot, item,
   * side
   */
  public boolean canInsertItem(int par1, ItemStack par2ItemStack, int par3) {
    return this.isItemValidForSlot(par1, par2ItemStack);
  }

  /**
   * Returns true if automation can extract the given item in the given slot from the given side. Args: Slot, item,
   * side
   */
  public boolean canExtractItem(int par1, ItemStack par2ItemStack, int par3) {
    if (par3 == ForgeDirection.UP.ordinal()) {
      return par1 == 0;
    } else {
      return par1 == 1;
    }
  }
}
