package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.samples.visualization.*;
import com.destrostudios.cardgui.samples.visualization.cards.modelled.FoilModelledCard;
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
        foilModelledCard = new FoilModelledCard(assetManager, "models/card/card.j3o", "images/cardbacks/magic.png", ColorRGBA.Black);
        node.attachChild(foilModelledCard.getNode());
        glowBox = new GlowBox(assetManager, 1.05f, 1.43f);
    }
    private boolean minified;
    private Node node;
    private FoilModelledCard foilModelledCard;
    private GlowBox glowBox;

    public void updateCardFront(MyCardModel cardModel) {
        int textureWidth = 400;
        int textureHeight = 560;

        // Back
        PaintableImage imageBack = new PaintableImage(textureWidth, textureHeight);
        imageBack.setBackground(Color.BLACK);
        BufferedImage imageBackground = FileAssets.getImage("images/templates/template_" + (minified ? "rect" : "full") + "_" + cardModel.getColor().ordinal() + ".png", imageBack.getWidth(), imageBack.getHeight());
        imageBack.paintSameSizeImage(new PaintableImage(imageBackground));

        // Foil
        PaintableImage imageFoil = new PaintableImage(textureWidth, textureHeight);
        imageFoil.setBackground_Alpha(0);
        String imagePath = "images/cards/" + cardModel.getName() + ".png";
        int imageX = 36;
        int imageY = (minified ? 36 : 68);
        int imageWidth = 328;
        int imageHeight = (minified ? 488 : 242);
        for (int x = 0; x < imageWidth; x++) {
            for (int y = 0; y < imageHeight; y++) {
                imageFoil.setPixel(imageX + x, imageY + y, 255, 255, 255, 255);
            }
        }
        imageFoil.paintImage(FileAssets.getImage(imagePath, imageWidth, imageHeight), imageX, imageY);
        if (cardModel.isDamaged()) {
            for (int x = 0; x < imageWidth; x++) {
                for (int y = 0; y < imageHeight; y++) {
                    imageFoil.setPixel_Red(imageX + x, imageY + y, 255);
                }
            }
        }

        // Front
        PaintableImage imageFront = new PaintableImage(textureWidth, textureHeight);
        imageFront.setBackground_Alpha(0);

        foilModelledCard.setFront(imageBack, imageFoil, imageFront);
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
