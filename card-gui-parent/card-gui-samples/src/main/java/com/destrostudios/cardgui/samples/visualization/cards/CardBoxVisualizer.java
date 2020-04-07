package com.destrostudios.cardgui.samples.visualization.cards;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.Card;
import com.destrostudios.cardgui.samples.visualization.PaintableImage;
import com.destrostudios.cardgui.samples.visualization.SimpleAttachmentVisualizer;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.texture.Texture2D;

/**
 *
 * @author Carl
 */
public abstract class CardBoxVisualizer<CardModelType extends BoardObjectModel> extends SimpleAttachmentVisualizer<Card<CardModelType>, Node> {

    public CardBoxVisualizer(String backTexturePath, String sidesTexturePath) {
        this.backTexturePath = backTexturePath;
        this.sidesTexturePath = sidesTexturePath;
    }
    private String backTexturePath;
    private String sidesTexturePath;

    @Override
    protected Node createVisualizationObject(AssetManager assetManager) {
        CardBox cardBox = new CardBox(assetManager, backTexturePath, sidesTexturePath);
        return cardBox.getNode();
    }

    @Override
    protected void updateVisualizationObject(Node node, Card<CardModelType> card, AssetManager assetManager) {
        Geometry faceFront = (Geometry) node.getChild(CardBox.NAME_GEOMETRY_FRONT);
        PaintableImage paintableImage = paintCard(card.getModel());
        Texture2D texture = new Texture2D();
        texture.setImage(paintableImage.getImage());
        faceFront.getMaterial().setTexture("DiffuseMap", texture);
    }

    protected abstract PaintableImage paintCard(CardModelType cardModel);
}
