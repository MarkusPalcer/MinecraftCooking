package MinecraftCooking.machines.Grindstone;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class GrindstoneTileNoBuildCraft extends GrindstoneTile implements ISidedInventory {
    @Override
    public void write(NBTTagCompound data) {
        data.setShort("UsedTime", (short) this.processingTime);
        data.setShort("NeededTime", (short) this.processingTimeNeeded);
        NBTTagList items = new NBTTagList();

        for (int i = 0; i < this.itemStacks.length; ++i)
        {
            if (this.itemStacks[i] != null)
            {
                NBTTagCompound slotData = new NBTTagCompound();
                slotData.setByte("Slot", (byte) i);
                this.itemStacks[i].writeToNBT(slotData);
                items.appendTag(slotData);
            }
        }

        data.setTag("Items", items);
    }

    @Override
    public void read(NBTTagCompound data) {
        NBTTagList items = data.getTagList("Items", 10);
        this.itemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < items.tagCount(); ++i)
        {
            NBTTagCompound slotData = items.getCompoundTagAt(i);
            byte b0 = slotData.getByte("Slot");

            if (b0 >= 0 && b0 < this.itemStacks.length)
            {
                this.itemStacks[b0] = ItemStack.loadItemStackFromNBT(slotData);
            }
        }

        this.processingTime = data.getShort("UsedTime");
        this.processingTimeNeeded = data.getShort("NeededTime");
    }

    protected void process() {
        float power = this.openedByPlayers.size();

        if (power <= 0) {
            return;
        }

        if (this.currentRecipe.isCraftableBy(this.itemStacks[0])) {
            this.processingTime += power;

            if (this.processingTime >= this.currentRecipe.processingTime)
            {
                this.processingTime -= this.currentRecipe.processingTime;
                this.smeltItem();
            }
        } else {
            this.processingTime = 0;
            this.processingTimeNeeded = 0;
            this.currentRecipe = null;
        }

        this.sendTileUpdate();
    }
}

