package com.destrostudios.cardgui;

/**
 *
 * @author Carl
 */
public abstract class Interactivity<ModelType extends BoardObjectModel> {

    public abstract void trigger(BoardObject<ModelType> boardObject, BoardObject target);
}
