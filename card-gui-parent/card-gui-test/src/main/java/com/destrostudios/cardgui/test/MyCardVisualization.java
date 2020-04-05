package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.samples.visualization.CustomAttachmentVisualization;
import com.destrostudios.cardgui.samples.visualization.GlowBox;
import com.destrostudios.cardgui.samples.visualization.ModelledCard;
import com.destrostudios.cardgui.samples.visualization.PaintableImage;
import com.destrostudios.cardgui.test.files.FileAssets;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MyCardVisualization extends CustomAttachmentVisualization<Node> {

    public MyCardVisualization(AssetManager assetManager, boolean minified) {
        this.minified = minified;
        node = new Node();
        modelledCard = new ModelledCard(assetManager, "models/card/card.j3o", "images/cardbacks/magic.png", ColorRGBA.Black);
        node.attachChild(modelledCard.getNode());
        glowBox = new GlowBox(assetManager, 1.05f, 1.43f);
    }
    private boolean minified;
    private Node node;
    private ModelledCard modelledCard;
    private GlowBox glowBox;

    public void updateCardFront(MyCardModel cardModel) {
        PaintableImage paintableImage = new PaintableImage(400, 560);
        paintableImage.setBackground(Color.BLACK);
        BufferedImage imageBackground = FileAssets.getImage("images/templates/template_" + (minified ? "rect" : "full") + "_" + cardModel.getColor().ordinal() + ".png");
        paintableImage.paintImage(new PaintableImage(imageBackground), 0, 0, paintableImage.getWidth(), paintableImage.getHeight());
        String imagePath = "images/cards/" + cardModel.getName() + ".png";
        int imageX = 36;
        int imageY = (minified ? 36 : 68);
        int imageWidth = 328;
        int imageHeight = (minified ? 488 : 242);
        paintableImage.paintImage(FileAssets.getImage(imagePath, imageWidth, imageHeight), imageX, imageY);
        if (cardModel.isDamaged()) {
            for (int x = 0; x < imageWidth; x++) {
                for (int y = 0; y < imageHeight; y++) {
                    paintableImage.setPixel_Red(imageX + x, imageY + y, 255);
                }
            }
        }
        modelledCard.setFront(paintableImage);
    }

    public void setGlow(ColorRGBA colorRGBA) {
        glowBox.setColor(colorRGBA);
        node.attachChild(glowBox.getGeometry());
    }

    public void removeGlow() {
        node.detachChild(glowBox.getGeometry());
    }

    @Override
    public Node getSpatial() {
        return node;
    }
}
