package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.samples.visualization.CustomAttachmentVisualizer;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

public class MyCircleVisualizer extends CustomAttachmentVisualizer<MyCircle, Node, MyCircleVisualization> {

    @Override
    protected MyCircleVisualization createVisualizationObject(AssetManager assetManager) {
        return new MyCircleVisualization(assetManager);
    }

    @Override
    protected void updateVisualizationObject(MyCircleVisualization visualization, MyCircle circle, AssetManager assetManager) {
        visualization.updateCardFront(circle.getModel(), assetManager);
    }
}
