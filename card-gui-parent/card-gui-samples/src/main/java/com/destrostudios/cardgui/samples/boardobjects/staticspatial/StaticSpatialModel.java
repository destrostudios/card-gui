package com.destrostudios.cardgui.samples.boardobjects.staticspatial;

import com.destrostudios.cardgui.BoardObjectModel;
import com.jme3.scene.Spatial;

public class StaticSpatialModel extends BoardObjectModel {

    private Spatial spatial;

    public Spatial getSpatial() {
        return spatial;
    }

    public void setSpatial(Spatial spatial) {
        updateIfNotEquals(this.spatial, spatial, () -> this.spatial = spatial);
    }
}
