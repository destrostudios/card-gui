package com.destrostudios.cardgui.samples.visualisation;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.Card;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture2D;
import com.jme3.material.RenderState;

/**
 *
 * @author Carl
 */
public abstract class SimpleCardVisualizer<CardModelType extends BoardObjectModel> extends SimpleAttachmentVisualizer<Card<CardModelType>, Geometry> {

    public SimpleCardVisualizer(float width, float height) {
        this.width = width;
        this.height = height;
    }
    private float width;
    private float height;

    @Override
    protected Geometry createAttachment(AssetManager assetManager) {
        Geometry geometry = new Geometry();
        Box box = new Box(width, 0.01f, height);
        box.setBuffer(VertexBuffer.Type.TexCoord, 2, new float[]{
            1, 0, 0, 0, 0, 1, 1, 1, // top
            0, 0, 0, 1, 1, 1, 1, 0, // right
            0, 1, 1, 1, 1, 0, 0, 0, // bottom
            1, 1, 1, 0, 0, 0, 0, 1, // left
            0, 0, 0, 1, 1, 1, 1, 0, // front
            0, 0, 0, 1, 1, 1, 1, 0  // back
        });
        geometry.setMesh(box);
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", ColorRGBA.White);
        material.setColor("Diffuse", new ColorRGBA(0.75f, 0.75f, 0.75f, 1));
        material.setColor("Specular", ColorRGBA.Black);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        geometry.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        return geometry;
    }

    @Override
    public void updateAttachment(Geometry geometry, Card<CardModelType> card, AssetManager assetManager) {
        PaintableImage paintableImage = paintCard(card.getModel());
        Texture2D texture = new Texture2D();
        texture.setImage(paintableImage.getImage());
        geometry.getMaterial().setTexture("DiffuseMap", texture);
    }

    public abstract PaintableImage paintCard(CardModelType cardModel);
}
