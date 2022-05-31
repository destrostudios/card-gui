package com.destrostudios.cardgui.samples.visualization.boxes;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;

public class ColorBox extends BackgroundBox {

    public ColorBox(AssetManager assetManager, float width, float height) {
        super(width, height);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
    }

    public void setColor(ColorRGBA color) {
        geometry.getMaterial().setColor("Color", color);
    }
}
