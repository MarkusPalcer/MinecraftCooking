package MinecraftCooking.gui;

import MinecraftCooking.machines.CookingTable.CookingTableBlock;
import MinecraftCooking.machines.Machines;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

class ContainerCookingTable extends ContainerWorkbench {

  private final World world;
  private final int posX;
  private final int posY;
  private final int posZ;

  public ContainerCookingTable(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5) {
    super(par1InventoryPlayer, par2World, par3, par4, par5);
    this.world = par2World;
    this.posX = par3;
    this.posY = par4;
    this.posZ = par5;
  }

  @Override
  public void onCraftMatrixChanged(IInventory par1iInventory) {
    this.craftResult.setInventorySlotContents(0, CookingTableBlock.recipes.findMatchingRecipe(this.craftMatrix, this.world));
  }

  @Override
  public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
    return this.world.getBlock(this.posX, this.posY, this.posZ) == Machines.CookingTable
        && par1EntityPlayer.getDistanceSq((double) this.posX + 0.5D, (double) this.posY + 0.5D, (double) this.posZ + 0.5D) <= 64.0D;
  }
}
