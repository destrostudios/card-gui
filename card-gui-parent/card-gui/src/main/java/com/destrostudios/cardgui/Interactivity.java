package com.destrostudios.cardgui;

/**
 *
 * @author Carl
 */
public abstract class Interactivity<ModelType extends BoardObjectModel> {

    protected Interactivity(Type type) {
        this.type = type;
    }
    public enum Type {
        CLICK,
        DRAG,
        AIM
    }
    private Type type;

    public Type getType() {
        return type;
    }

    public abstract void trigger(BoardObject<ModelType> boardObject, BoardObject target);
}
