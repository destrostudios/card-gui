package com.destrostudios.cardgui.inspectors;

import com.destrostudios.cardgui.Inspector;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class TransformInspector extends Inspector {

    @Override
    public boolean isReadyToUninspect() {
        return inspectedBoardObject.hasReachedTargetTransform();
    }
}
