package mods.cooking.gui;

import mods.cooking.machines.Machines;
import mods.cooking.machines.tiles.GrindstoneTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

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
				if (world.getBlockId(x, y, z) != Machines.CookingTable.blockID) return null;
				return new ContainerCookingTable(player.inventory, world, x, y, z);
			case GRINDSTONE:
				if (world.getBlockId(x, y, z) != Machines.Grindstone.blockID) return null;
				return new GrindstoneContainer(player.inventory, (GrindstoneTile)world.getBlockTileEntity(x, y, z));
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
				if (world.getBlockId(x, y, z) != Machines.CookingTable.blockID) return null;
				return new GuiCookingTable(player.inventory, world, x, y, z);
			case GRINDSTONE:
				if (world.getBlockId(x, y, z) != Machines.Grindstone.blockID) return null;
				return new GrindstoneGui(player.inventory, (GrindstoneTile)world.getBlockTileEntity(x, y, z));
			default:
				return null;
		}
	}


}
