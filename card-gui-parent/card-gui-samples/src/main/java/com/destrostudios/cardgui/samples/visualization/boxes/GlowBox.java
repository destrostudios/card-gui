package com.destrostudios.cardgui.samples.visualization.boxes;

import com.destrostudios.cardgui.samples.visualization.materials.PulsatingMaterialParamControl;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;

public class GlowBox extends BackgroundBox {

    public GlowBox(AssetManager assetManager, float width, float height) {
        this(assetManager, width, height, 0.4f, 1, 2.5f);
    }

    public GlowBox(AssetManager assetManager, float width, float height, float alphaMinimum, float alphaMaximum, float alphaInterval) {
        super(width, height);
        Material material = new Material(assetManager, "materials/glow_box/glow_box.j3md");
        material.setTexture("GlowMap", assetManager.loadTexture("images/effects/card_glow.png"));
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        geometry.setQueueBucket(RenderQueue.Bucket.Translucent);
        geometry.addControl(new PulsatingMaterialParamControl("Alpha", alphaMinimum, alphaMaximum, alphaInterval));
    }

    public void setColor(ColorRGBA color) {
        geometry.getMaterial().setColor("Color", color);
    }
}
