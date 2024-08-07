package com.destrostudios.cardgui.samples.boardobjects.staticspatial;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.TransformedBoardObject;
import com.jme3.scene.Spatial;
import lombok.Getter;

@Getter
public class StaticSpatialModel extends BoardObjectModel {

    private Spatial spatial;
    private TransformedBoardObject followTarget;

    public void setSpatial(Spatial spatial) {
        updateIfNotEquals(this.spatial, spatial, () -> this.spatial = spatial);
    }

    public void setFollowTarget(TransformedBoardObject followTarget) {
        updateIfNotEquals(this.followTarget, followTarget, () -> this.followTarget = followTarget);
    }
}
