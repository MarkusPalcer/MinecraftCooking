package mods.cooking.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.cooking.machines.tiles.GrindstoneTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class GrindstoneContainer extends Container {
	private GrindstoneTile tile;
	private int lastCookTime;
	private int lastMaxCookTime;
	
	public GrindstoneContainer(InventoryPlayer inventory, GrindstoneTile tile) {
		this.tile = tile;
		this.tile.openedByPlayers.add(inventory.player.entityId);
		
		this.addSlotToContainer(new Slot(tile, 0, 56, 35));
		this.addSlotToContainer(new OutputSlot(inventory.player, tile, 1, 116, 35));
		
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
        }
	}

    @Override
	public void onContainerClosed(EntityPlayer player) {
    	this.tile.openedByPlayers.remove((Integer)player.entityId);
		super.onContainerClosed(player);
	}

	public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.tile.processingTime);
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastCookTime != this.tile.processingTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tile.processingTime);
                this.lastCookTime = this.tile.processingTime;
            }
            
            if (this.lastMaxCookTime != this.tile.processingTimeNeeded) {
            	icrafting.sendProgressBarUpdate(this, 1, this.tile.processingTimeNeeded);
            	this.lastMaxCookTime = this.tile.processingTimeNeeded;
            }
        }
    }
    
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.tile.isUseableByPlayer(entityplayer);
	}
	
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
		switch (par1) {
		case 0:
			this.tile.processingTime = par2;
			break;
		case 1:
			this.tile.processingTimeNeeded = par2;
			break;
		default:
			break;
		}
    }
	
	/**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (slot.inventory instanceof GrindstoneTile) {
            	if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
            		return null;
            	}
            	
            	slot.onSlotChange(itemstack1, itemstack);
            } else {
            	if (!this.mergeItemStack(itemstack1, 0, 1, true)) {
            		return null;
            	}
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize)
            {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }
}
