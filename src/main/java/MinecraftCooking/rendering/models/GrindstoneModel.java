package MinecraftCooking.rendering.models;

import MinecraftCooking.BaseClass;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SuppressWarnings("MagicNumber")
@SideOnly(Side.CLIENT)
public class GrindstoneModel extends ModelBase {

    public final ResourceLocation texture;

    ModelRenderer Base;
    ModelRenderer LowerSlab;
    public ModelRenderer UpperSlab;

    public GrindstoneModel() {
        textureWidth = 64;
        textureHeight = 64;
        this.texture = new ResourceLocation(BaseClass.prefix + "textures/blocks/grindstone.png");

        Base = new ModelRenderer(this, 0, 0);
        Base.addBox(-8F, 0F, -8F, 16, 6, 16);
        Base.setRotationPoint(0F, 0F, 0F);
        Base.setTextureSize(64, 64);
        Base.mirror = true;
        setRotation(Base, 0F, 0F, 0F);

        LowerSlab = new ModelRenderer(this, 0, 22);
        LowerSlab.addBox(-7F, 6F, -7F, 14, 2, 14);
        LowerSlab.setRotationPoint(0F, 0F, 0F);
        LowerSlab.setTextureSize(64, 64);
        LowerSlab.mirror = true;
        setRotation(LowerSlab, 0F, 0F, 0F);

        UpperSlab = new ModelRenderer(this, 0, 38);
        UpperSlab.addBox(-4.5F, 8F, -4.5F, 9, 5, 9);
        UpperSlab.setRotationPoint(0F, 0F, 0F);
        UpperSlab.setTextureSize(64, 64);
        UpperSlab.mirror = true;
        setRotation(UpperSlab, 0F, 0F, 0F);
    }

    public void render()
    {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(this.texture);
        Base.render(1.0F / 16.0F);
        LowerSlab.render(1.0F / 16.0F);
        UpperSlab.render(1.0F / 16.0F);
    }

    public void render(double x, double y, double z) {
        GL11.glPushMatrix();

        GL11.glTranslatef((float) x + 0.5f, (float) y, (float) z + 0.5f);

        this.render();

        GL11.glPopMatrix();
    }

    public void renderInventory(float x, float y, float z) {
        UpperSlab.rotateAngleY = 0.0f;

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glTranslatef(x,  y - 0.5F,  z);
        GL11.glRotatef(180f, 0f, 1f, 0f);

        this.render();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
