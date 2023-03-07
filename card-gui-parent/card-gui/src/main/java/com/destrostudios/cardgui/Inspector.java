package com.destrostudios.cardgui;

import com.jme3.math.Vector3f;

public abstract class Inspector {

    protected TransformedBoardObject<?> inspectedBoardObject;

    public void onBoardObjectRegister(TransformedBoardObject<?> transformedBoardObject) {

    }

    public void onBoardObjectUnregister(TransformedBoardObject<?> transformedBoardObject) {

    }

    public void inspect(BoardAppState boardAppState, TransformedBoardObject<?> transformedBoardObject, Vector3f cursorPositionWorld) {
        inspectedBoardObject = transformedBoardObject;
    }

    public void uninspect() {
        inspectedBoardObject = null;
    }

    public abstract boolean isReadyToUninspect();
}
