package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.samples.tools.deckbuilder.collection.CollectionDeckBuilderCardAmount;
import com.destrostudios.cardgui.samples.tools.deckbuilder.collection.CollectionDeckBuilderCardAmountModel;
import com.destrostudios.cardgui.samples.visualization.SimpleAttachmentVisualizer;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MyDeckBuilderCollectionCardAmountVisualizer extends SimpleAttachmentVisualizer<CollectionDeckBuilderCardAmount, BitmapText> {

    @Override
    protected BitmapText createVisualizationObject(AssetManager assetManager) {
        BitmapText bitmapText = new BitmapText(assetManager.loadFont("fonts/Verdana_14.fnt"));
        bitmapText.rotate(new Quaternion().fromAngleAxis(-1 * FastMath.HALF_PI, Vector3f.UNIT_X));
        bitmapText.scale(0.02f);
        return bitmapText;
    }

    @Override
    protected void updateVisualizationObject(BitmapText bitmapText, CollectionDeckBuilderCardAmount amount, AssetManager assetManager) {
        CollectionDeckBuilderCardAmountModel model = amount.getModel();

        float xOffset = -9.56f;
        float yOffset = -3.7f;
        float xInterval = 1;
        float yInterval = 1.4f;
        float x = (xOffset + (model.getX() * xInterval));
        float y = (yOffset + (model.getY() * yInterval));
        bitmapText.setLocalTranslation(x, 0, y);

        bitmapText.setText("" + model.getAmountCollection());
        bitmapText.setColor(model.getAmountCollection() > 0 ? ColorRGBA.Black : ColorRGBA.Red);
    }
}
