package com.destrostudios.cardgui.samples.visualization.cards.modelled;

import com.destrostudios.cardgui.samples.visualization.materials.TimeMaterialParamControl;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;

public class FoilModelledCard extends SimpleModelledCard {

    public FoilModelledCard(AssetManager assetManager, String modelPath, String backTexturePath, ColorRGBA sideColor) {
        super(assetManager, modelPath, backTexturePath, sideColor);

        Geometry front = getGeometry_Front();
        front.addControl(new TimeMaterialParamControl("Time"));
    }

    @Override
    protected Material createMaterial_Front(AssetManager assetManager) {
        return new Material(assetManager, "materials/foil_card/foil_card.j3md");
    }
}
