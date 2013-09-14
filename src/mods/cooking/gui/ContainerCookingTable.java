package mods.cooking.gui;

import mods.cooking.RecipeManager;
import mods.cooking.machines.Machines;
import mods.cooking.machines.blocks.CookingTable;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;

public class ContainerCookingTable extends ContainerWorkbench {

	protected World world;
	protected int posX, posY, posZ;
	
	public ContainerCookingTable(InventoryPlayer par1InventoryPlayer,
			World par2World, int par3, int par4, int par5) {
		super(par1InventoryPlayer, par2World, par3, par4, par5);
		this.world = par2World;
		this.posX = par3;
		this.posY = par4;
		this.posZ = par5;
	}

	@Override
	public void onCraftMatrixChanged(IInventory par1iInventory) {
		this.craftResult.setInventorySlotContents(0, CookingTable.recipes.findMatchingRecipe(this.craftMatrix, this.world));
	}
	
	@Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.world.getBlockId(this.posX, this.posY, this.posZ) != Machines.CookingTable.blockID ? false : par1EntityPlayer.getDistanceSq((double)this.posX + 0.5D, (double)this.posY + 0.5D, (double)this.posZ + 0.5D) <= 64.0D;
    }
}
