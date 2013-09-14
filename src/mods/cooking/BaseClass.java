package mods.cooking;

import java.util.ArrayList;
import java.util.List;

import buildcraft.core.DefaultProps;
import buildcraft.core.network.PacketHandler;

import mods.cooking.fluids.Fluids;
import mods.cooking.gui.GuiHandler;
import mods.cooking.items.Items;
import mods.cooking.machines.Machines;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid="cooking", name="cooking", version="1.0.0.0", dependencies = "required-after:Forge@[9.10.0.800,)")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class BaseClass {

	public static final String prefix = "cooking:";
	
	@Instance("cooking")
	public static BaseClass instance;
	
	public static CreativeTabs tab = new CreativeTabs("cooking") {
		public ItemStack getIconItemStack() {
			return new ItemStack(Machines.CookingTable);
		}
	};
	
	private Configuration config;
		
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		RegisterStringLocalizations();
		
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandler());
		
		Fluids.Init(config);
		Items.Init(config);
		Machines.Init(config);
		
		config.save();
	}
	
	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
	public void textureHook(TextureStitchEvent.Pre event){ 
		Fluids.InitTextures(event);
	}
	
	private void RegisterStringLocalizations() {
		LanguageRegistry.instance().addStringLocalization("itemGroup.cooking", "Cooking");
		LanguageRegistry.instance().addStringLocalization("container.cookingtable", "Food preparation");
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
