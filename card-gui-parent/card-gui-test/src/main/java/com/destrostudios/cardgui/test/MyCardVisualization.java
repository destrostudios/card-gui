package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.samples.visualization.*;
import com.destrostudios.cardgui.samples.visualization.cards.modelled.FoilModelledCard;
import com.destrostudios.cardgui.samples.visualization.cards.modelled.SimpleModelledCard;
import com.destrostudios.cardgui.test.files.FileAssets;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MyCardVisualization extends CustomAttachmentVisualization<Node> {

    public MyCardVisualization(AssetManager assetManager, boolean minified) {
        this.minified = minified;
        node = new Node();
        foilModelledCard = new FoilModelledCard(assetManager, "models/card/card.j3o", "images/cardbacks/magic.png", ColorRGBA.Black);
        node.attachChild(foilModelledCard.getNode());
        glowBox = new GlowBox(assetManager, 1.05f, 1.43f);
    }
    private static HashMap<String, Texture> cachedTextures = new HashMap<>();
    private boolean minified;
    private Node node;
    private FoilModelledCard foilModelledCard;
    private GlowBox glowBox;

    public void updateCardFront(MyCardModel cardModel) {
        int textureWidth = 400;
        int textureHeight = 560;

        String imagePathBackground = "images/templates/template_" + (minified ? "rect" : "full") + "_" + cardModel.getColor().ordinal() + ".png";
        String imagePathArtwork = "images/cards/" + cardModel.getName() + ".png";

        int artworkTiles = (("Copy Cat".equals(cardModel.getName())) ? 20 : 1);
        int artworkX = 36;
        int artworkY = (minified ? 36 : 68);
        int artworkWidth = 328;
        int artworkHeight = (minified ? 488 : 242);

        // Background
        String backgroundKey = "background_" + minified + "_" + cardModel.getColor().ordinal();
        Texture textureBackground = cachedTextures.computeIfAbsent(backgroundKey, key -> {
            PaintableImage imageBackground = new PaintableImage(textureWidth, textureHeight);
            imageBackground.setBackground(Color.BLACK);
            BufferedImage bufferedImageBackground = FileAssets.getImage(imagePathBackground, imageBackground.getWidth(), imageBackground.getHeight());
            imageBackground.paintSameSizeImage(new PaintableImage(bufferedImageBackground));
            return SimpleModelledCard.flipAndCreateTexture(imageBackground);
        });

        // Artwork
        String artworkKey = "artwork_" + minified + "_" + cardModel.getName();
        Texture textureArtwork = cachedTextures.computeIfAbsent(artworkKey, key -> {
            PaintableImage imageArtwork;
            if (artworkTiles == 1) {
                imageArtwork = new PaintableImage(textureWidth, textureHeight);
                imageArtwork.setBackground_Alpha(0);
                imageArtwork.paintImage(FileAssets.getImage(imagePathArtwork, artworkWidth, artworkHeight), artworkX, artworkY);
            } else {
                imageArtwork = new PaintableImage(artworkTiles * textureWidth, textureHeight);
                imageArtwork.setBackground_Alpha(0);
                PaintableImage srcImage = new PaintableImage(FileAssets.getImage(imagePathArtwork, artworkTiles * artworkWidth, artworkHeight));
                for (int i = 0; i < artworkTiles; i++) {
                    imageArtwork.paintImage(srcImage, i * artworkWidth, 0, artworkWidth, artworkHeight, (i * textureWidth) + artworkX, artworkY, artworkWidth, artworkHeight);
                }
            }
            return SimpleModelledCard.flipAndCreateTexture(imageArtwork);
        });

        // Damaged (Not caching this one to represent something that could be more dynamic and might have to be redrawn each time)
        PaintableImage imageDamaged = new PaintableImage(textureWidth, textureHeight);
        imageDamaged.setBackground_Alpha(0);
        if (cardModel.isDamaged()) {
            for (int x = 0; x < artworkWidth; x++) {
                for (int y = 0; y < artworkHeight; y++) {
                    imageDamaged.setPixel(artworkX + x, artworkY + y, 255, 0, 0, 128);
                }
            }
        }
        Texture textureDamaged = SimpleModelledCard.flipAndCreateTexture(imageDamaged);

        // FoilMap
        String foilMapKey = "foilmap";
        Texture textureFoilMap = cachedTextures.computeIfAbsent(foilMapKey, key -> {
            PaintableImage imageFoilMap = new PaintableImage(textureWidth, textureHeight);
            imageFoilMap.setBackground_Alpha(0);
            for (int x = 0; x < artworkWidth; x++) {
                for (int y = 0; y < artworkHeight; y++) {
                    imageFoilMap.setPixel_Alpha(artworkX + x, artworkY + y, 255);
                }
            }
            return SimpleModelledCard.flipAndCreateTexture(imageFoilMap);
        });

        Material material = foilModelledCard.getMaterial_Front();
        material.setTexture("DiffuseMap1", textureBackground);
        material.setTexture("DiffuseMap2", textureArtwork);
        material.setInt("DiffuseMapTiles2", artworkTiles);
        material.setTexture("DiffuseMap3", textureDamaged);
        material.setTexture("FoilMap", textureFoilMap);
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
