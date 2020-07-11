package com.destrostudios.cardgui.samples.boardobjects.staticspatial;

import com.destrostudios.cardgui.TransformedBoardObject;

/**
 *
 * @author Carl
 */
public class StaticSpatial extends TransformedBoardObject<StaticSpatialModel> {

    public StaticSpatial() {
        super(new StaticSpatialModel());
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        TransformedBoardObject followTarget = getModel().getFollowTarget();
        if (followTarget != null) {
            position().setCurrentValue(followTarget.position().getCurrentValue());
            rotation().setCurrentValue(followTarget.rotation().getCurrentValue());
            scale().setCurrentValue(followTarget.scale().getCurrentValue());
        }
    }
}
