package com.destrostudios.cardgui.samples.visualization;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;

public class GlowBox {

    public GlowBox(AssetManager assetManager, float width, float height) {
        geometry = new Geometry("glowBox", new Quad(width, height));
        Material material = new Material(assetManager, "materials/glow_box/glow_box.j3md");
        material.setTexture("GlowMap", assetManager.loadTexture("images/effects/card_glow.png"));
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        geometry.setQueueBucket(RenderQueue.Bucket.Translucent);
        geometry.setLocalTranslation((width / -2), 0, (height / 2));
        geometry.rotate(new Quaternion().fromAngleAxis(-1 * FastMath.HALF_PI, Vector3f.UNIT_X));
        geometry.addControl(new PulsatingMaterialParamControl("Alpha", 0.4f, 1, 2.5f));
    }
    private Geometry geometry;

    public void setColor(ColorRGBA color) {
        geometry.getMaterial().setColor("Color", color);
    }

    public Geometry getGeometry() {
        return geometry;
    }
}
