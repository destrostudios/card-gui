package com.destrostudios.cardgui;

import com.jme3.math.Vector3f;

public interface Inspector {

    void inspect(BoardAppState boardAppState, TransformedBoardObject<?> transformedBoardObject, Vector3f cursorPositionWorld);

    void uninspect(TransformedBoardObject<?> transformedBoardObject);
}