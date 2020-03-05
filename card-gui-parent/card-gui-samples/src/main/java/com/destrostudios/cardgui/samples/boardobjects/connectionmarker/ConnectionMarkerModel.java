package com.destrostudios.cardgui.samples.boardobjects.connectionmarker;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.TransformedBoardObject;

public class ConnectionMarkerModel extends BoardObjectModel {

    private TransformedBoardObject sourceBoardObject;
    private TransformedBoardObject targetBoardObject;

    public TransformedBoardObject getSourceBoardObject() {
        return sourceBoardObject;
    }

    public TransformedBoardObject getTargetBoardObject() {
        return targetBoardObject;
    }

    public void setSourceBoardObject(TransformedBoardObject sourceBoardObject) {
        updateIfNotEquals(this.sourceBoardObject, sourceBoardObject, () -> this.sourceBoardObject = sourceBoardObject);
    }

    public void setTargetBoardObject(TransformedBoardObject targetBoardObject) {
        updateIfNotEquals(this.targetBoardObject, targetBoardObject, () -> this.targetBoardObject = targetBoardObject);
    }
}
