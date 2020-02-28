package com.destrostudios.cardgui.interactivities;

import com.destrostudios.cardgui.BoardObjectFilter;
import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.Interactivity;
import com.destrostudios.cardgui.targetarrow.TargetSnapMode;

/**
 *
 * @author Carl
 */
public abstract class AimToTargetInteractivity<ModelType extends BoardObjectModel> extends Interactivity<ModelType> implements BoardObjectFilter {
    
    public AimToTargetInteractivity(TargetSnapMode targetSnapMode) {
        super(Type.AIM);
        this.targetSnapMode = targetSnapMode;
    }
    private TargetSnapMode targetSnapMode;

    public TargetSnapMode getTargetSnapMode() {
        return targetSnapMode;
    }
}
