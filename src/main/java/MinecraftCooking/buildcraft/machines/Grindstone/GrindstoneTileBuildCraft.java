package MinecraftCooking.buildcraft.machines.Grindstone;

import MinecraftCooking.machines.Grindstone.GrindstoneTile;
import MinecraftCooking.machines.Machines;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.core.utils.Utils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

@SuppressWarnings("ALL")
public class GrindstoneTileBuildCraft extends GrindstoneTile implements IPowerReceptor {

    private PowerHandler powerProvider;

    public GrindstoneTileBuildCraft() {
        powerProvider = new PowerHandler(this, PowerHandler.Type.MACHINE);
        powerProvider.configure(1, 200, 25, Machines.MAX_ENERGY);
        powerProvider.configurePowerPerdition(1, 5);
    }

    @Override
    public void write(NBTTagCompound data) {
        data.setShort("UsedTime", (short) this.processingTime);
        data.setShort("NeededTime", (short) this.processingTimeNeeded);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.itemStacks.length; ++i)
        {
            if (this.itemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.itemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        data.setTag("Items", nbttaglist);
        this.powerProvider.writeToNBT(data, "power");
    }

    @Override
    public void read(NBTTagCompound data) {
        NBTTagList nbttaglist = data.getTagList("Items", Utils.NBTTag_Types.NBTTagCompound.ordinal());
        this.itemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.itemStacks.length)
            {
                this.itemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.processingTime = data.getShort("UsedTime");
        this.processingTimeNeeded = data.getShort("NeededTime");

        if (data.hasKey("power"))
            this.powerProvider.readFromNBT(data, "power");
    }

    @Override
    protected void process() {
        double energyToUse = 1 + this.powerProvider.getEnergyStored() / 100;
        double power = this.powerProvider.useEnergy(1, energyToUse, true) + this.openedByPlayers.size();

        if (power <= 0) {
            return;
        }

        if (this.currentRecipe.isCraftableBy(this.itemStacks[0])) {
            this.processingTime += power;

            if (this.processingTime >= this.currentRecipe.processingTime)
            {
                this.processingTime -= this.currentRecipe.processingTime;
                this.smeltItem();
                //todo:this.onInventoryChanged();
            }
        } else {
            this.processingTime = 0;
            this.processingTimeNeeded = 0;
            this.currentRecipe = null;
        }

        this.sendTileUpdate();
    }


    @Override
    public PowerHandler.PowerReceiver getPowerReceiver(ForgeDirection side) {
        return this.powerProvider.getPowerReceiver();
    }

    @Override
    public void doWork(PowerHandler workProvider) {
    }

    @Override
    public World getWorld() {
        return this.worldObj;
    }
}
