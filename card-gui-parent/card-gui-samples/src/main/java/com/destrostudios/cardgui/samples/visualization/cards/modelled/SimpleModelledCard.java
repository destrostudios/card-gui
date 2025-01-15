package com.destrostudios.cardgui.samples.visualization.cards.modelled;

import com.destrostudios.cardgui.samples.visualization.SimpleModelledFlatObject;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public abstract class SimpleModelledCard extends SimpleModelledFlatObject {

    public SimpleModelledCard(AssetManager assetManager, String backTexturePath, ColorRGBA sideColor) {
        super("card-gui/samples/models/card/card.j3o", "front", "back", "side", 2, 2.8f, backTexturePath, sideColor);
        initialize(assetManager);
    }

    @Override
    protected void initialize(AssetManager assetManager) {
        super.initialize(assetManager);
        node.rotate(new Quaternion().fromAngleAxis(-1 * FastMath.HALF_PI, Vector3f.UNIT_X));
        node.scale(0.2f);
    }

    @Override
    protected float getAxisPositionForTextureCoordinate_X(float x, float y, float z) {
        return x;
    }

    @Override
    protected float getAxisPositionForTextureCoordinate_Y(float x, float y, float z) {
        return y;
    }
}
