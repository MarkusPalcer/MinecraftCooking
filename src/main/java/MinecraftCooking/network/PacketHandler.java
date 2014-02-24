package MinecraftCooking.network;

import net.minecraft.nbt.NBTTagCompound;

public class PacketHandler {
  public static void updateTile(SynchronizedTile tile) {
    NBTTagCompound tags = new NBTTagCompound();
    tags.setInteger("PacketType", PacketType.TileUpdate);
    tags.setInteger("X", tile.getXCord());
    tags.setInteger("Y", tile.getYCord());
    tags.setInteger("Z", tile.getZCord());

    NBTTagCompound serializedData = new NBTTagCompound();
    tile.writeToNBT(serializedData);
    tags.setTag("Data", serializedData);

      /* TODO: Update sending and receiving of network packages
        */
  }
}
