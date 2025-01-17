package com.destrostudios.cardgui.samples.visualization.cards.modelled;

import com.destrostudios.cardgui.samples.visualization.materials.MaterialFactory;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;

public class UnshadedSimpleModelledCard extends SimpleModelledCard {

    public UnshadedSimpleModelledCard(AssetManager assetManager, String backTexturePath, ColorRGBA sideColor) {
        super(assetManager, backTexturePath, sideColor);
    }

    @Override
    protected Material createMaterial_Front(AssetManager assetManager) {
        return MaterialFactory.unshadedTexture(assetManager);
    }

    @Override
    protected Material createMaterial_Back(AssetManager assetManager) {
        return MaterialFactory.unshadedTexture(assetManager, backTexturePath);
    }

    @Override
    protected Material createMaterial_Side(AssetManager assetManager) {
        return MaterialFactory.unshadedColor(assetManager, sideColor);
    }

    public void setFront(Texture texture) {
        getMaterial_Front().setTexture("ColorMap", texture);
    }
}
