package MinecraftCooking.network.packets;

import MinecraftCooking.BaseClass;
import MinecraftCooking.network.SynchronizedTile;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.io.IOException;
import java.util.logging.Level;

public class UpdatePacket implements IPacket {
  public NBTTagCompound data;

  @Override
  public void readBytes(ByteBuf bytes) {
    short length = bytes.readShort();
    byte[] compressed = new byte[length];
    bytes.readBytes(compressed);

    try {
      data = CompressedStreamTools.decompress(compressed);
    } catch (IOException e) {
      BaseClass.logger.log(Level.WARNING, "Error while trying to receive an update packet: ", e);
    }
  }

  @Override
  public void writeBytes(ByteBuf bytes) {
    byte[] compressed;
    try {
      compressed = CompressedStreamTools.compress(data);
      bytes.writeShort(compressed.length);
      bytes.writeBytes(compressed);
    } catch (IOException e) {
      BaseClass.logger.log(Level.WARNING, "Error while trying to send an update packet: ", e);
    }
  }

  @Override
  public void executeClient(EntityPlayer player) {
    if (this.data == null) {
      return;
    }

    int x = data.getInteger("x");
    int y = data.getInteger("y");
    int z = data.getInteger("z");

    World world = player.getEntityWorld();

    if (!world.blockExists(x, y, z)) {
      return;
    }

    TileEntity entity = world.getTileEntity(x, y, z);

    if (entity instanceof SynchronizedTile) {
      SynchronizedTile synchronizedTile = (SynchronizedTile) entity;
      synchronizedTile.readFromNBT(this.data);
    }
  }

  @Override
  public void executeServer(EntityPlayer player) {
    this.executeClient(player);
  }
}
