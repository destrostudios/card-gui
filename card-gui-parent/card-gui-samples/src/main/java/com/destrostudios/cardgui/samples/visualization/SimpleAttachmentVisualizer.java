package com.destrostudios.cardgui.samples.visualization;

import com.destrostudios.cardgui.BoardObject;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public abstract class SimpleAttachmentVisualizer<BoardObjectType extends BoardObject, SpatialType extends Spatial> extends AttachmentVisualizer<BoardObjectType, SpatialType, SpatialType> {

    public SimpleAttachmentVisualizer() {
        super(spatial -> spatial);
    }
}
