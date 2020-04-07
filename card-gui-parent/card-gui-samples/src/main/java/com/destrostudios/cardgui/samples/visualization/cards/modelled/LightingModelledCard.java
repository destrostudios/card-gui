package com.destrostudios.cardgui.samples.visualization.cards.modelled;

import com.destrostudios.cardgui.samples.visualization.PaintableImage;
import com.destrostudios.cardgui.samples.visualization.materials.MaterialFactory;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture2D;

public class LightingModelledCard extends SimpleModelledCard {

    public LightingModelledCard(AssetManager assetManager, String modelPath, String backTexturePath, ColorRGBA sideColor) {
        super(assetManager, modelPath, backTexturePath, sideColor);
    }

    @Override
    protected Material createMaterial_Front(AssetManager assetManager) {
        return MaterialFactory.textureLighting(assetManager);
    }

    public void setFront(PaintableImage image) {
        setFront(flipAndCreateTexture(image));
    }

    public void setFront(Texture2D texture) {
        Material material = getGeometry_Front().getMaterial();
        material.setTexture("DiffuseMap", texture);
    }
}
