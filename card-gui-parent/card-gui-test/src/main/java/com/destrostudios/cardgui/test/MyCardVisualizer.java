package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.Card;
import com.destrostudios.cardgui.samples.visualization.CustomAttachmentVisualizer;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

public class MyCardVisualizer extends CustomAttachmentVisualizer<Card<MyCardModel>, Node, MyCardVisualization> {

    public MyCardVisualizer(boolean minified) {
        this.minified = minified;
    }
    private boolean minified;

    @Override
    protected MyCardVisualization createVisualizationObject(AssetManager assetManager) {
        return new MyCardVisualization(assetManager, minified);
    }

    @Override
    protected void updateVisualizationObject(MyCardVisualization visualization, Card<MyCardModel> card, AssetManager assetManager) {
        visualization.updateCardFront(card.getModel());
        if (card.getModel().isInspected()) {
            visualization.setGlow(ColorRGBA.Blue);
        } else if (card.getModel().isDamaged()) {
            visualization.setGlow(ColorRGBA.randomColor());
        } else {
            visualization.removeGlow();
        }
    }
}
