package com.destrostudios.cardgui.samples.boardobjects.staticspatial;

import com.destrostudios.cardgui.samples.visualization.SimpleAttachmentVisualizer;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public class StaticSpatialVisualizer extends SimpleAttachmentVisualizer<StaticSpatial, Node> {

    @Override
    protected Node createVisualizationObject(AssetManager assetManager) {
        return new Node();
    }

    @Override
    protected void updateVisualizationObject(Node node, StaticSpatial staticSpatial, AssetManager assetManager) {
        Spatial spatial = staticSpatial.getModel().getSpatial();
        node.attachChild(spatial);
    }
}
