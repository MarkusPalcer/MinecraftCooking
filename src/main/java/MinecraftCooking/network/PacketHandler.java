package MinecraftCooking.network;

import MinecraftCooking.BaseClass;
import MinecraftCooking.network.packets.IPacket;
import MinecraftCooking.network.packets.UpdatePacket;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;

import java.util.EnumMap;

public class PacketHandler extends FMLIndexedMessageToMessageCodec<IPacket> {
  private static EnumMap<Side, FMLEmbeddedChannel> channels;

  private PacketHandler() {
    addDiscriminator(0, UpdatePacket.class);
  }

  public static void Init() {
    channels = NetworkRegistry.INSTANCE.newChannel(BaseClass.prefix + "CHANNEL", new PacketHandler());
  }

  public static void sendToAllPlayers(IPacket packet) {
    channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
    channels.get(Side.SERVER).writeOutbound(packet);
  }

  public static void sendToPlayer(EntityPlayer entityplayer, IPacket packet) {
    channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
    channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(entityplayer);
    channels.get(Side.SERVER).writeOutbound(packet);
  }

  public static void sendToServer(IPacket packet) {
    channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
    channels.get(Side.CLIENT).writeOutbound(packet);
  }

  @Override
  public void encodeInto(ChannelHandlerContext ctx, IPacket packet, ByteBuf data) throws Exception {
    packet.writeBytes(data);
  }

  @Override
  public void decodeInto(ChannelHandlerContext ctx, ByteBuf data, IPacket packet) {
    packet.readBytes(data);
    switch (FMLCommonHandler.instance().getEffectiveSide()) {
      case CLIENT:
        packet.executeClient(Minecraft.getMinecraft().thePlayer);
        break;
      case SERVER:
        INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
        packet.executeServer(((NetHandlerPlayServer) netHandler).playerEntity);
        break;
    }
  }
}
