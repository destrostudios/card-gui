package com.destrostudios.cardgui.samples.tools.deckbuilder;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.samples.visualization.PaintableImage;
import com.destrostudios.cardgui.samples.visualization.cards.SimpleCardQuadVisualizer;

public abstract class SimpleDeckBuilderDeckCardVisualizer<CardModelType extends BoardObjectModel> extends SimpleCardQuadVisualizer<DeckBuilderDeckCardModel<CardModelType>> {

    public SimpleDeckBuilderDeckCardVisualizer(float geometryWidth, float geometryHeight, int amountPixelWidth, boolean lightingMaterial) {
        super(geometryWidth, geometryHeight, true, false, lightingMaterial);
        this.amountPixelWidth = amountPixelWidth;
    }
    protected int amountPixelWidth;

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
        return image;
    }

    protected abstract PaintableImage paintActualCard(CardModelType cardModel);

    protected abstract void paintCenteredAmount(PaintableImage image, int x, int y, int amount);
}
