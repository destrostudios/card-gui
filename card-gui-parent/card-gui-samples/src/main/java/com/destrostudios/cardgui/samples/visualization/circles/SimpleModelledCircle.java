package com.destrostudios.cardgui.samples.visualization.circles;

import com.destrostudios.cardgui.samples.visualization.SimpleModelledFlatObject;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;

public abstract class SimpleModelledCircle extends SimpleModelledFlatObject {

    public SimpleModelledCircle(AssetManager assetManager, String backTexturePath, ColorRGBA sideColor) {
        super("card-gui/samples/models/circle/circle.j3o", "circle_0", "circle_1", "circle_2", 1, 1, backTexturePath, sideColor);
        initialize(assetManager);
    }

    @Override
    protected float getAxisPositionForTextureCoordinate_X(float x, float y, float z) {
        return x;
    }

    @Override
    protected float getAxisPositionForTextureCoordinate_Y(float x, float y, float z) {
        return z;
    }
}
