package com.destrostudios.cardgui.interactivities;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.Interactivity;

/**
 *
 * @author Carl
 */
public abstract class ClickInteractivity<ModelType extends BoardObjectModel> extends Interactivity<ModelType> {

    public ClickInteractivity() {
        super(Type.CLICK);
    }
}
