package MinecraftCooking;

import MinecraftCooking.fluids.Fluids;
import MinecraftCooking.gui.GuiHandler;
import MinecraftCooking.items.Items;
import MinecraftCooking.machines.Machines;
import MinecraftCooking.network.PacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.config.Configuration;

import java.util.logging.Level;
import java.util.logging.Logger;

@Mod(modid = "MinecraftCooking", name = "MinecraftCooking", version = "1.0.0.0", dependencies = "required-after:Forge@[10.12.0.1024,)")
public class BaseClass {
  public static final String prefix = "MinecraftCooking:";

  public static final CreativeTabs tab = new CreativeTabs("MinecraftCooking") {
    public Item getTabIconItem() {
      return Items.Flour;
    }
  };

  public static final Logger logger = Logger.getLogger("MinecraftCooking");

  @Mod.Instance("MinecraftCooking")
  public static BaseClass instance;

  public static Configuration config;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    config = new Configuration(event.getSuggestedConfigurationFile());
    config.load();

    InitializeIntegration("MinecraftCooking.buildcraft.BaseClass", "BuildCraft");

    NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

    Fluids.Init();
    Items.Init();
    Machines.Init();
    PacketHandler.Init();
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {
    config.save();
  }

  @SuppressWarnings("SameParameterValue")
  private void InitializeIntegration(String className, String integrationName) {
    logger.log(Level.FINE, "Loading integration for " + integrationName);

    try {
      Class.forName(className).getMethod("Init").invoke(null);
    } catch (Exception ex) {
      logger.log(Level.WARNING, "Error while loading integration for " + integrationName, ex);
    }
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void textureHook(TextureStitchEvent.Pre event) {
    Fluids.InitTextures(event);
  }

  @SuppressWarnings("EmptyMethod")
  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event) {
  }
}
