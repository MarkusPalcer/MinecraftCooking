package MinecraftCooking.fluids;

import net.minecraftforge.fluids.Fluid;

class Milk extends Fluid {
  private static final String name = "milk";

  public Milk() {
    super(name);
    setDensity(1035);
    setUnlocalizedName("milk");
  }
}
