package MinecraftCooking.machines;

import MinecraftCooking.machines.CookingTable.CookingTableBlock;
import MinecraftCooking.machines.Grindstone.GrindstoneBlock;
import net.minecraft.block.Block;

public class Machines {
  public static Block CookingTable;
  public static Block Grindstone;

  public static void Init() {
    Grindstone = GrindstoneBlock.Create();
    CookingTable = CookingTableBlock.Create();
  }
}
