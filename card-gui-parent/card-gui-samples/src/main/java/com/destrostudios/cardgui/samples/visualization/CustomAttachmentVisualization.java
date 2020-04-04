package com.destrostudios.cardgui.samples.visualization;

import com.jme3.scene.Spatial;

public abstract class CustomAttachmentVisualization<SpatialType extends Spatial> {

    public abstract SpatialType getSpatial();
}
