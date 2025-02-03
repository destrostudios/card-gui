package com.destrostudios.cardgui.samples.visualization.background;

import com.destrostudios.cardgui.samples.visualization.materials.PulsatingMaterialParamControl;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;

public class GlowQuad extends BackgroundQuad {

    public GlowQuad(AssetManager assetManager, float width, float height, String glowMapPath) {
        this(assetManager, width, height, glowMapPath, 0.4f, 1, 2.5f);
    }

    public GlowQuad(AssetManager assetManager, float width, float height, String glowMapPath, float alphaMinimum, float alphaMaximum, float alphaInterval) {
        super(width, height);
        Material material = new Material(assetManager, "card-gui/samples/materials/glow_quad/glow_quad.j3md");
        material.setTexture("GlowMap", assetManager.loadTexture(glowMapPath));
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        geometry.setQueueBucket(RenderQueue.Bucket.Translucent);
        geometry.addControl(new PulsatingMaterialParamControl("Alpha", alphaMinimum, alphaMaximum, alphaInterval));
    }

    public void setColor(ColorRGBA color) {
        geometry.getMaterial().setColor("Color", color);
    }
}
