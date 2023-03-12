package com.destrostudios.cardgui.samples.visualization.cards.modelled;

import com.destrostudios.cardgui.samples.visualization.materials.MaterialFactory;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

public abstract class SimpleModelledCard extends ModelledCard {

    public SimpleModelledCard(AssetManager assetManager, String backTexturePath, ColorRGBA sideColor) {
        this.backTexturePath = backTexturePath;
        this.sideColor = sideColor;

        initialize(assetManager, "card-gui/samples/models/card/card.j3o");
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
}
