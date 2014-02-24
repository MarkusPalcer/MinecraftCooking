package MinecraftCooking;

import MinecraftCooking.fluids.Fluids;
import MinecraftCooking.gui.GuiHandler;
import MinecraftCooking.items.Items;
import MinecraftCooking.machines.Machines;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = "MinecraftCooking", name = "MinecraftCooking", version = "1.0.0.0", dependencies = "required-after:Forge@[9.10.0.800,)")
public class BaseClass {

  public static final String prefix = "cooking:";

  @SidedProxy(clientSide = "MinecraftCooking.ClientProxy", serverSide = "cooking.CommonProxy")
  public static CommonProxy Proxy;

  @Mod.Instance("cooking")
  public static BaseClass instance;
  public static Configuration config;
  public static CreativeTabs tab = new CreativeTabs("cooking") {
    public Item getTabIconItem() {
      return Items.Flour;
    }
  };
  private boolean buildCraftPresent;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    config = new Configuration(event.getSuggestedConfigurationFile());
    config.load();
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) {
    RegisterStringLocalizations();

    try {
      Class.forName("buildcraft.BuildCraftCore");
      buildCraftPresent = true;
    } catch (ClassNotFoundException e) {
      buildCraftPresent = false;
    }

    NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

    Fluids.Init();
    Items.Init();
    Machines.Init();

    config.save();

    Proxy.InitRendering();
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  public void textureHook(TextureStitchEvent.Pre event) {
    Fluids.InitTextures(event);
  }

  private void RegisterStringLocalizations() {
    LanguageRegistry.instance().addStringLocalization("itemGroup.cooking", "Cooking");
    LanguageRegistry.instance().addStringLocalization("container.cookingtable", "Food preparation");
    LanguageRegistry.instance().addStringLocalization("container.grindstone", "Grindstone");
  }

  @Mod.EventHandler
  public void postInit(FMLPostInitializationEvent event) {

  }

  public boolean hasBuildCraft() {
    return buildCraftPresent;
  }
}
