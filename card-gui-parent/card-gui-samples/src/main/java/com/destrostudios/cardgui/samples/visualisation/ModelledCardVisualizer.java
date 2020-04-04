package com.destrostudios.cardgui.samples.visualisation;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.Card;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.texture.Texture2D;

/**
 *
 * @author Carl
 */
public abstract class ModelledCardVisualizer<CardModelType extends BoardObjectModel> extends SimpleAttachmentVisualizer<Card<CardModelType>, Node> {

    public ModelledCardVisualizer(String modelPath, String backTexturePath, ColorRGBA sideColor) {
        this.modelPath = modelPath;
        this.backTexturePath = backTexturePath;
        this.sideColor = sideColor;
    }
    private String modelPath;
    private String backTexturePath;
    private ColorRGBA sideColor;

    @Override
    protected Node createAttachment(AssetManager assetManager) {
        ModelledCard modelledCard = new ModelledCard(assetManager, modelPath, backTexturePath, sideColor);
        return modelledCard.getNode();
    }

    @Override
    protected void updateAttachment(Node node, Card<CardModelType> card, AssetManager assetManager) {
        Geometry front = (Geometry) node.getChild("front");
        Geometry image = (Geometry) node.getChild("image");
        front.getMaterial().setTexture("DiffuseMap", flipAndCreateTexture(paintCard_Background(card.getModel())));
        image.getMaterial().setTexture("DiffuseMap", flipAndCreateTexture(paintCard_Image(card.getModel())));
    }

    private Texture2D flipAndCreateTexture(PaintableImage paintableImage) {
        paintableImage.flipY();
        Texture2D texture = new Texture2D();
        texture.setImage(paintableImage.getImage());
        return texture;
    }

    public abstract PaintableImage paintCard_Background(CardModelType cardModel);

    public abstract PaintableImage paintCard_Image(CardModelType cardModel);
}
