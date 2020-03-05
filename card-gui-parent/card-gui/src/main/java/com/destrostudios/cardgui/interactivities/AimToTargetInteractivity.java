package com.destrostudios.cardgui.interactivities;


import com.destrostudios.cardgui.BoardObjectFilter;
import com.destrostudios.cardgui.Interactivity;
import com.destrostudios.cardgui.TargetSnapMode;

/**
 *
 * @author Carl
 */
public abstract class AimToTargetInteractivity extends Interactivity implements BoardObjectFilter {
    
    public AimToTargetInteractivity(TargetSnapMode targetSnapMode) {
        this.targetSnapMode = targetSnapMode;
    }
    private TargetSnapMode targetSnapMode;

    public TargetSnapMode getTargetSnapMode() {
        return targetSnapMode;
    }
}
