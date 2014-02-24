package MinecraftCooking.rendering;

import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class StitchedTexture extends AbstractTexture {
    private ArrayList<SubTexture> parts = new ArrayList<SubTexture>();

    public void addPart(SubTexture part) {
        this.parts.add(part);
    }

    public void addPart(ResourceLocation location, int x, int y, int width, int height) {
        SubTexture part = new SubTexture();
        part.location = location;
        part.targetX = x;
        part.targetY = y;
        part.targetWidth = width;
        part.targetHeight = height;

        this.addPart(part);
    }

    public void addPart(ResourceLocation location, int sourceX, int sourceY, int sourceWidth, int sourceHeight,  int x, int y, int width, int height) {
        PartialSubTexture part = new PartialSubTexture();
        part.location = location;
        part.targetX = x;
        part.targetY = y;
        part.targetWidth = width;
        part.targetHeight = height;
        part.sourceHeight = sourceHeight;
        part.sourceWidth = sourceWidth;
        part.sourceX = sourceX;
        part.sourceY = sourceY;

        this.addPart(part);
    }

  @Override
  public void loadTexture(IResourceManager iResourceManager) {

  }

  public class SubTexture {
        public ResourceLocation location;
        public int targetX;
        public int targetY;
        public int targetWidth;
        public int targetHeight;
    }

    public class PartialSubTexture extends SubTexture {
        public int sourceX;
        public int sourceY;
        public int sourceWidth;
        public int sourceHeight;
    }
}
