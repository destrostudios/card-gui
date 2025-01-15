package com.destrostudios.cardgui.samples.visualization;

import com.destrostudios.cardgui.samples.visualization.materials.MaterialFactory;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

public abstract class SimpleModelledFlatObject extends ModelledFlatObject {

    public SimpleModelledFlatObject(String modelPath, String geometryNameFront, String geometryNameBack, String geometryNameSide, float meshWidth, float meshHeight, String backTexturePath, ColorRGBA sideColor) {
        super(modelPath, geometryNameFront, geometryNameBack, geometryNameSide, meshWidth, meshHeight);
        this.backTexturePath = backTexturePath;
        this.sideColor = sideColor;
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
