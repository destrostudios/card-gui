package com.destrostudios.cardgui.samples.visualization.background;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.texture.Texture;

public class TextureQuad extends BackgroundQuad {

    public TextureQuad(AssetManager assetManager, float width, float height) {
        super(width, height);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
    }

    public void setTexture(Texture texture) {
        geometry.getMaterial().setTexture("ColorMap", texture);
    }
}
