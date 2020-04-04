package com.destrostudios.cardgui.samples.visualization;

import com.destrostudios.cardgui.BoardObject;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public abstract class CustomAttachmentVisualizer<BoardObjectType extends BoardObject, SpatialType extends Spatial, VisualizationType extends CustomAttachmentVisualization<SpatialType>> extends AttachmentVisualizer<BoardObjectType, VisualizationType, SpatialType> {

    public CustomAttachmentVisualizer() {
        super(CustomAttachmentVisualization::getSpatial);
    }
}
