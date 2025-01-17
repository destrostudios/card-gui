package com.destrostudios.cardgui.samples.visualization;

import com.jme3.math.ColorRGBA;

public abstract class SimpleModelledFlatObject extends ModelledFlatObject {

    public SimpleModelledFlatObject(String modelPath, String geometryNameFront, String geometryNameBack, String geometryNameSide, float meshWidth, float meshHeight, String backTexturePath, ColorRGBA sideColor) {
        super(modelPath, geometryNameFront, geometryNameBack, geometryNameSide, meshWidth, meshHeight);
        this.backTexturePath = backTexturePath;
        this.sideColor = sideColor;
    }
    protected String backTexturePath;
    protected ColorRGBA sideColor;
}
