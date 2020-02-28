package com.destrostudios.cardgui.samples.visualisation;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.BoardObjectVisualizer;
import com.destrostudios.cardgui.Card;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture2D;
import com.jme3.material.RenderState;

/**
 *
 * @author Carl
 */
public abstract class SimpleCardVisualizer<CardModelType extends BoardObjectModel> implements BoardObjectVisualizer<Card<CardModelType>> {
    
    private static final String NAME_GEOMETRY = "cardGeometry";

    @Override
    public void createVisualisation(Node node, AssetManager assetManager) {
        Box box = new Box(0.4f, 0.01f, 0.6f);
        box.setBuffer(VertexBuffer.Type.TexCoord, 2, new float[]{
            1, 1, 1, 0, 0, 0, 0, 1, // back
            1, 1, 1, 0, 0, 0, 0, 1, // right
            1, 1, 1, 0, 0, 0, 0, 1, // front
            1, 1, 1, 0, 0, 0, 0, 1, // left
            1, 1, 1, 0, 0, 0, 0, 1, // top
            1, 1, 1, 0, 0, 0, 0, 1  // bottom
        });
        Geometry geometry = new Geometry(NAME_GEOMETRY, box);
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", ColorRGBA.White);
        material.setColor("Diffuse", new ColorRGBA(0.75f, 0.75f, 0.75f, 1));
        material.setColor("Specular", ColorRGBA.Black);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        geometry.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        node.attachChild(geometry);
    }

    @Override
    public void updateVisualisation(Node node, Card<CardModelType> card, AssetManager assetManager) {
        Geometry geometry = (Geometry) node.getChild(NAME_GEOMETRY);
        PaintableImage paintableImage = paintCard(card.getModel());
        Texture2D texture = new Texture2D();
        texture.setImage(paintableImage.getImage());
        geometry.getMaterial().setTexture("DiffuseMap", texture);
    }

    public abstract PaintableImage paintCard(CardModelType cardModel);
}
