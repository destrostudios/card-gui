package com.destrostudios.cardgui.samples.visualization.cards.modelled;

import com.destrostudios.cardgui.samples.visualization.PaintableImage;
import com.destrostudios.cardgui.samples.visualization.materials.MaterialFactory;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture2D;

public abstract class SimpleModelledCard extends ModelledCard {

    public SimpleModelledCard(AssetManager assetManager, String modelPath, String backTexturePath, ColorRGBA sideColor) {
        this.backTexturePath = backTexturePath;
        this.sideColor = sideColor;

        initialize(assetManager, modelPath);
    }
    private String backTexturePath;
    private ColorRGBA sideColor;

    @Override
    protected Material createMaterial_Back(AssetManager assetManager) {
        return MaterialFactory.textureLighting(assetManager, backTexturePath);
    }

    @Override
    protected Material createMaterial_Side(AssetManager assetManager) {
        return MaterialFactory.colorLighting(assetManager, sideColor);
    }

    protected Texture2D flipAndCreateTexture(PaintableImage paintableImage) {
        paintableImage.flipY();
        Texture2D texture = new Texture2D();
        texture.setImage(paintableImage.getImage());
        return texture;
    }
}
