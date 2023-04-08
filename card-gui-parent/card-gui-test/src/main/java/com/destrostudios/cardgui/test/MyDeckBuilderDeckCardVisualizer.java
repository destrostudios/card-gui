package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.samples.tools.deckbuilder.SimpleDeckBuilderDeckCardVisualizer;
import com.destrostudios.cardgui.samples.visualization.PaintableImage;
import com.destrostudios.cardgui.test.files.FileAssets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MyDeckBuilderDeckCardVisualizer extends SimpleDeckBuilderDeckCardVisualizer<MyCardModel> {

    public MyDeckBuilderDeckCardVisualizer() {
        super(4, 0.57f, 57, false);
    }
    private static final Font FONT = new Font("Tahoma", Font.BOLD, 22);

    @Override
    protected PaintableImage paintActualCard(MyCardModel cardModel) {
        PaintableImage image = new PaintableImage(400, 57);
        image.setBackground(Color.BLACK);
        String imagePath = "images/cards/" + cardModel.getName() + ".png";
        int tiles = ("Copy Cat".equals(cardModel.getName()) ? 20 : 1);
        PaintableImage artwork = new PaintableImage(FileAssets.getImage(imagePath));
        image.paintImage(artwork, 0, 0, (artwork.getWidth() / tiles), artwork.getHeight(), amountPixelWidth, 0, (image.getWidth() - amountPixelWidth), image.getHeight());
        paintText(image, amountPixelWidth + 20, (image.getHeight() / 2), false, true, cardModel.getName());
        return image;
    }

    @Override
    protected void paintCenteredAmount(PaintableImage image, int x, int y, int amount) {
        paintText(image, x, y, true, true, "" + amount);
    }

    private void paintText(PaintableImage image, int x, int y, boolean centeredX, boolean centeredY, String text) {
        // Overkill implementation via an own AWT image since it's just a demo
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setFont(FONT);
        graphics.setColor(Color.WHITE);
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int resultingX = x;
        if (centeredX) {
            resultingX -= (fontMetrics.stringWidth(text) / 2);
        }
        int resultingY = y;
        if (centeredY) {
            resultingY -= (fontMetrics.getHeight() / 2);
            resultingY += fontMetrics.getAscent();
        }
        graphics.drawString(text, resultingX, resultingY);
        image.paintImage(bufferedImage, 0, 0);
    }
}
