package MinecraftCooking.gui;

import MinecraftCooking.machines.Grindstone.GrindstoneTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GrindstoneContainer extends Container {
	private GrindstoneTile tile;

	public GrindstoneContainer(InventoryPlayer inventory, GrindstoneTile tile) {
		this.tile = tile;
		this.tile.open(inventory.player.getEntityId());

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
    	this.tile.close(player.getEntityId());
		super.onContainerClosed(player);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.tile.isUseableByPlayer(entityplayer);
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
                slot.putStack(null);
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
