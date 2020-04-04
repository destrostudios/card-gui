package com.destrostudios.cardgui.samples.visualization;

import com.destrostudios.cardgui.BoardObject;
import com.destrostudios.cardgui.StatefulBoardObjectVisualizer;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.function.Function;

/**
 *
 * @author Carl
 */
public abstract class AttachmentVisualizer<BoardObjectType extends BoardObject, VisualizationType, SpatialType extends Spatial> extends StatefulBoardObjectVisualizer<BoardObjectType, VisualizationType> {

    public AttachmentVisualizer(Function<VisualizationType, SpatialType> spatialTransformer) {
        this.spatialTransformer = spatialTransformer;
    }
    private Function<VisualizationType, SpatialType> spatialTransformer;

    @Override
    protected void addVisualization(Node node, VisualizationType visualization) {
        node.attachChild(spatialTransformer.apply(visualization));
    }

    @Override
    protected void removeVisualizationObject(Node node, VisualizationType visualization) {
        node.detachChild(spatialTransformer.apply(visualization));
    }
}
