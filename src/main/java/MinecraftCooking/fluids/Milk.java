package MinecraftCooking.fluids;

import net.minecraftforge.fluids.Fluid;

/**
 * Created by Nighty on 23.02.14.
 */
public class Milk extends Fluid {
  public static final String name = "milk";

  public Milk() {
    super(name);
    setDensity(1035);
    setUnlocalizedName("milk");
  }
}
