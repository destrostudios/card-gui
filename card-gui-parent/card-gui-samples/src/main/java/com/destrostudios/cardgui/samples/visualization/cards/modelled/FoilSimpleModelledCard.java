package com.destrostudios.cardgui.samples.visualization.cards.modelled;

import com.destrostudios.cardgui.samples.visualization.materials.MaterialFactory;
import com.destrostudios.cardgui.samples.visualization.materials.TimeMaterialParamControl;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;

public class FoilSimpleModelledCard extends SimpleModelledCard {

    public FoilSimpleModelledCard(AssetManager assetManager, String backTexturePath, ColorRGBA sideColor) {
        super(assetManager, backTexturePath, sideColor);

        Geometry front = getGeometry_Front();
        front.addControl(new TimeMaterialParamControl("Time"));
    }

    @Override
    protected Material createMaterial_Front(AssetManager assetManager) {
        return new Material(assetManager, "card-gui/samples/materials/foil/foil.j3md");
    }

    @Override
    protected Material createMaterial_Back(AssetManager assetManager) {
        return MaterialFactory.unshadedTexture(assetManager, backTexturePath);
    }

    @Override
    protected Material createMaterial_Side(AssetManager assetManager) {
        return MaterialFactory.unshadedColor(assetManager, sideColor);
    }
}
