package com.destrostudios.cardgui.samples.visualization.background;

import com.jme3.asset.AssetManager;

public class SimpleGlowQuad extends GlowQuad {

    public enum Shape {
        SQUARE,
        RECT,
        CIRCLE
    }

    public SimpleGlowQuad(AssetManager assetManager, float width, float height, Shape shape) {
        super(assetManager, width, height, getGlowMapPath(shape));
    }

    public SimpleGlowQuad(AssetManager assetManager, float width, float height, Shape shape, float alphaMinimum, float alphaMaximum, float alphaInterval) {
        super(assetManager, width, height, getGlowMapPath(shape), alphaMinimum, alphaMaximum, alphaInterval);
    }

    private static String getGlowMapPath(Shape shape) {
        return "card-gui/samples/textures/glow_" + shape.name().toLowerCase() + ".png";
    }
}
