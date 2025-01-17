package com.destrostudios.cardgui.samples.visualization.cards;

import com.destrostudios.cardgui.samples.visualization.materials.MaterialFactory;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.FastMath;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import lombok.Getter;

public class CardBox {

    // TODO: Have these as setting somewhere
    private final float cardWidth = 0.8f;
    private final float cardHeight = 1.2f;
    private final float cardDepth = 0.02f;

    public CardBox(AssetManager assetManager, String backTexturePath, String sidesTexturePath) {
        this.assetManager = assetManager;
        this.backTexturePath = backTexturePath;
        this.sidesTexturePath = sidesTexturePath;
        initializeFaces();
    }
    public static final String NAME_GEOMETRY_FRONT = "cardFront";
    private AssetManager assetManager;
    private String backTexturePath;
    private String sidesTexturePath;
    @Getter
    private Node node = new Node("cardBox");

    private void initializeFaces() {
        Geometry faceFront = createAndAttachFace(NAME_GEOMETRY_FRONT, cardWidth, cardHeight, sidesTexturePath);
        faceFront.setLocalTranslation((cardWidth / -2), (cardDepth / 2), (cardHeight / 2));
        faceFront.rotate(-1 * FastMath.HALF_PI, 0, 0);

        Geometry faceBack = createAndAttachFace("cardBack", cardWidth, cardHeight, backTexturePath);
        faceBack.setLocalTranslation((cardWidth / 2), (cardDepth / -2), (cardHeight / 2));
        faceBack.rotate(-1 * FastMath.HALF_PI, 0, FastMath.PI);

        Geometry faceTop = createAndAttachFace("cardTop", cardWidth, cardDepth, sidesTexturePath);
        faceTop.setLocalTranslation((cardWidth / 2), (cardDepth / -2), (cardHeight / -2));
        faceTop.rotate(0, FastMath.PI, 0);

        Geometry faceRight = createAndAttachFace("cardRight", cardDepth, cardHeight, sidesTexturePath);
        faceRight.setLocalTranslation((cardWidth / 2), (cardDepth / 2), (cardHeight / 2));
        faceRight.rotate(-1 * FastMath.HALF_PI, 0, -1 * FastMath.HALF_PI);

        Geometry faceBottom = createAndAttachFace("cardBottom", cardWidth, cardDepth, sidesTexturePath);
        faceBottom.setLocalTranslation((cardWidth / -2), (cardDepth / -2), (cardHeight / 2));
        faceBottom.rotate(0, 0, 0);

        Geometry faceLeft = createAndAttachFace("cardLeft", cardDepth, cardHeight, sidesTexturePath);
        faceLeft.setLocalTranslation((cardWidth / -2), (cardDepth / -2), (cardHeight / 2));
        faceLeft.rotate(-1 * FastMath.HALF_PI, 0, FastMath.HALF_PI);
    }

    private Geometry createAndAttachFace(String name, float width, float height, String texturePath) {
        Quad quad = new Quad(width, height);
        Geometry geometry = new Geometry(name, quad);
        Material material = MaterialFactory.lightingTexture(assetManager);
        material.setTexture("DiffuseMap", assetManager.loadTexture(texturePath));
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setMaterial(material);
        geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        geometry.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        node.attachChild(geometry);
        return geometry;
    }
}
