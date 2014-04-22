package MinecraftCooking.network;

import MinecraftCooking.network.packets.UpdatePacket;
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
    this.sendTileUpdate();
  }

  /**
   * Writes a tile entity to NBT.
   */
  @Override
  public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeToNBT(par1NBTTagCompound);
    this.write(par1NBTTagCompound);
  }

  protected abstract void write(NBTTagCompound data);

  protected abstract void read(NBTTagCompound data);

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
    if (!this.hasWorldObj() || this.worldObj.isRemote)
      return;

    NBTTagCompound serializedData = new NBTTagCompound();
    this.writeToNBT(serializedData);

    UpdatePacket packet = new UpdatePacket();
    packet.data = serializedData;

    PacketHandler.sendToAllPlayers(packet);
  }
}
