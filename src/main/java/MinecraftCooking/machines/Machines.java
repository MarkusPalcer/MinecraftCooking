package MinecraftCooking.machines;

import MinecraftCooking.BaseClass;
import MinecraftCooking.machines.CookingTable.CookingTableBlock;
import MinecraftCooking.machines.Grindstone.GrindstoneBlock;
import net.minecraft.block.Block;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Nighty on 23.02.14.
 */
public class Machines {
  public static final int MAX_ENERGY = 15000;

  public static Block CookingTable;
  public static Block Grindstone;

  public static void Init() {
    Grindstone = GrindstoneBlock.Create();
    CookingTable = CookingTableBlock.Create();

    if (BaseClass.instance.hasBuildCraft()) {
      try {
        Class.forName("cooking.machinesOld.BuildCraftMachines").getMethod("Init").invoke(null);
      } catch (ClassNotFoundException ignored) {
      } catch (NoSuchMethodException ignored) {
      } catch (InvocationTargetException ignored) {
      } catch (IllegalAccessException ignored) {
      }
    }
  }
}
