package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.samples.visualization.CustomAttachmentVisualization;
import com.destrostudios.cardgui.samples.visualization.circles.LightingSimpleModelledCircle;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;

public class MyCircleVisualization extends CustomAttachmentVisualization<Node> {

    public MyCircleVisualization(AssetManager assetManager) {
        node = new Node();
        modelledCircle = new LightingSimpleModelledCircle(assetManager, "images/cardbacks/magic.png", ColorRGBA.Black);
        node.attachChild(modelledCircle.getNode());
    }
    private Node node;
    private LightingSimpleModelledCircle modelledCircle;

    public void updateCardFront(MyCircleModel circleModel, AssetManager assetManager) {
        Texture texture = assetManager.loadTexture("images/cards/" + circleModel.getName() + ".png");
        modelledCircle.setFront(texture);
    }

    @Override
    public Node getSpatial() {
        return node;
    }
}
