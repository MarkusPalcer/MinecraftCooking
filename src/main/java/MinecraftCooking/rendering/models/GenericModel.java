package MinecraftCooking.rendering.models;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public abstract class GenericModel<TTileEntity> extends ModelBase {
    public abstract void render(TTileEntity tile);

    ArrayList<ModelRenderer> parts = new ArrayList<ModelRenderer>();

    protected ResourceLocation texture;
    protected float inventoryScale = 1.0F;

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.render(f5);
    }

    public void renderInventory() {
        this.render(inventoryScale);
    }

    public void render(float scale) {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(texture);
        for (ModelRenderer part : this.parts) {
            part.render(scale);
        }
    }
}
