package com.destrostudios.cardgui.samples.visualization.circles;

import com.destrostudios.cardgui.samples.visualization.materials.MaterialFactory;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;

public class LightingSimpleModelledCircle extends SimpleModelledCircle {

    public LightingSimpleModelledCircle(AssetManager assetManager, String backTexturePath, ColorRGBA sideColor) {
        super(assetManager, backTexturePath, sideColor);
    }

    @Override
    protected Material createMaterial_Front(AssetManager assetManager) {
        return MaterialFactory.textureLighting(assetManager);
    }

    public void setFront(Texture texture) {
        Material material = getGeometry_Front().getMaterial();
        material.setTexture("DiffuseMap", texture);
    }
}
