package com.destrostudios.cardgui.samples.visualization.cards;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.Card;
import com.destrostudios.cardgui.samples.visualization.PaintableImage;
import com.destrostudios.cardgui.samples.visualization.SimpleAttachmentVisualizer;
import com.destrostudios.cardgui.samples.visualization.materials.MaterialFactory;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.texture.Texture2D;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class SimpleCardVisualizer<CardModelType extends BoardObjectModel> extends SimpleAttachmentVisualizer<Card<CardModelType>, Geometry> {

    private boolean lightingMaterial;

    @Override
    protected Geometry createVisualizationObject(AssetManager assetManager) {
        Geometry geometry = new Geometry();
        geometry.setMesh(createMesh());
        Material material = (lightingMaterial ? MaterialFactory.lightingTexture(assetManager) : MaterialFactory.unshaded(assetManager));
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        geometry.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        return geometry;
    }

    protected abstract Mesh createMesh();

    @Override
    public void updateVisualizationObject(Geometry geometry, Card<CardModelType> card, AssetManager assetManager) {
        PaintableImage paintableImage = paintCard(card.getModel());
        Texture2D texture = new Texture2D();
        texture.setImage(paintableImage.getImage());
        geometry.getMaterial().setTexture((lightingMaterial ? "DiffuseMap" : "ColorMap"), texture);
    }

    protected abstract PaintableImage paintCard(CardModelType cardModel);
}
