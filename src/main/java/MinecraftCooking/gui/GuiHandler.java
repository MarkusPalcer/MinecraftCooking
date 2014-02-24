package MinecraftCooking.gui;

import MinecraftCooking.machines.Machines;
import MinecraftCooking.machines.Grindstone.GrindstoneTile;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
	public static final int COOKING_TABLE = 80;
	public static final int GRINDSTONE = 81;


	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;

		switch (ID) {
			case COOKING_TABLE:
				if (world.getBlock(x, y, z) != Machines.CookingTable) return null;
				return new ContainerCookingTable(player.inventory, world, x, y, z);
			case GRINDSTONE:
				if (world.getBlock(x, y, z) != Machines.Grindstone) return null;
				return new GrindstoneContainer(player.inventory, (GrindstoneTile)world.getTileEntity(x, y, z));
			default:
				return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,	int x, int y, int z) {
		if (!world.blockExists(x, y, z))
			return null;

		switch (ID) {
			case COOKING_TABLE:
				if (world.getBlock(x, y, z) != Machines.CookingTable) return null;
				return new GuiCookingTable(player.inventory, world, x, y, z);
			case GRINDSTONE:
				if (world.getBlock(x, y, z) != Machines.Grindstone) return null;
				return new GrindstoneGui(player.inventory, (GrindstoneTile)world.getTileEntity(x, y, z));
			default:
				return null;
		}
	}


}
