package MinecraftCooking.fluids;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class Fluids {
  private static Fluid Milk;

  public static void Init() {
    Milk = RegisterFluid(new Milk());
    FluidContainerRegistry.registerFluidContainer(new FluidContainerRegistry.FluidContainerData(
        new FluidStack(Milk, FluidContainerRegistry.BUCKET_VOLUME),
        new ItemStack(Items.milk_bucket),
        new ItemStack(Items.bucket),
        false));
  }

  private static Fluid RegisterFluid(Fluid input) {
    FluidRegistry.registerFluid(input);
    return FluidRegistry.getFluid(input.getName());
  }

  public static void InitTextures(Pre event) {
    Milk.setIcons(event.map.registerIcon("cooking:milk"));
  }
}
