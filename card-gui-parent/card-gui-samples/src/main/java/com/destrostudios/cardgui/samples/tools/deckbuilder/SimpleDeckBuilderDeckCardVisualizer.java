package com.destrostudios.cardgui.samples.tools.deckbuilder;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.samples.visualization.PaintableImage;
import com.destrostudios.cardgui.samples.visualization.SampleImages;
import com.destrostudios.cardgui.samples.visualization.cards.SimpleCardQuadVisualizer;

import java.awt.image.BufferedImage;

public abstract class SimpleDeckBuilderDeckCardVisualizer<CardModelType extends BoardObjectModel> extends SimpleCardQuadVisualizer<DeckBuilderDeckCardModel<CardModelType>> {

    public SimpleDeckBuilderDeckCardVisualizer(float geometryWidth, float geometryHeight, int amountPixelWidth, boolean lightingMaterial) {
        super(geometryWidth, geometryHeight, true, false, lightingMaterial);
        this.amountPixelWidth = amountPixelWidth;
    }
    protected int amountPixelWidth;
    private BufferedImage deleteIcon;

    @Override
    protected PaintableImage paintCard(DeckBuilderDeckCardModel<CardModelType> deckCardModel) {
        PaintableImage image = paintActualCard(deckCardModel.getCardModel());

        for (int x = 0; x < amountPixelWidth; x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                image.setPixel_Red(x, y, 0);
                image.setPixel_Green(x, y, 0);
                image.setPixel_Blue(x, y, 0);
                image.setPixel_Alpha(x, y, 255);
            }
        }
        paintCenteredAmount(image, (amountPixelWidth / 2), (image.getHeight() / 2), deckCardModel.getAmount());

        // Already load it here, to reduce any lag when hovering to a minimum
        if (deleteIcon == null) {
            deleteIcon = SampleImages.loadSampleTexture("delete", -1, image.getHeight());
        }
        if (deckCardModel.isRemovable() && deckCardModel.isHovered()) {
            image.paintImage(deleteIcon, image.getWidth() - deleteIcon.getWidth(), 0);
        }

        return image;
    }

    protected abstract PaintableImage paintActualCard(CardModelType cardModel);

    protected abstract void paintCenteredAmount(PaintableImage image, int x, int y, int amount);
}
