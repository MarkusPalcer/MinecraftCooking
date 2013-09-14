package mods.cooking.fluids;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.TextureStitchEvent.Pre;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class Fluids {
	public static Fluid Milk;
	
	public static void Init(Configuration config) {
		Milk = RegisterFluid(new Milk());
		FluidContainerRegistry.registerFluidContainer(Milk, new ItemStack(Item.bucketMilk));
	}
	
	private static Fluid RegisterFluid(Fluid input) {
		FluidRegistry.registerFluid(input);
		return FluidRegistry.getFluid(input.getName());
	}

	public static void InitTextures(Pre event) {
		Milk.setIcons(event.map.registerIcon("cooking:milk"));
	}
}
