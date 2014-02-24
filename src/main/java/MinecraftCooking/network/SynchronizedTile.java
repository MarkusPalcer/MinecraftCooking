package MinecraftCooking.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class SynchronizedTile extends TileEntity {

    protected SynchronizedTile() {
    }

    /**
     * Reads a tile entity from NBT.
     */
    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.read(par1NBTTagCompound);
        PacketHandler.updateTile(this);
    }

    /**
     * Writes a tile entity to NBT.
     */
    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        this.write(par1NBTTagCompound);
    }

    public abstract void write(NBTTagCompound data);

    public abstract void read(NBTTagCompound data);

    public int getXCord() {
        return this.xCoord;
    }

    public int getYCord() {
        return this.yCoord;
    }

    public int getZCord() {
        return this.zCoord;
    }

    protected void sendTileUpdate() {
        if (this.hasWorldObj() && !this.worldObj.isRemote)
            PacketHandler.updateTile(this);
    }
}
