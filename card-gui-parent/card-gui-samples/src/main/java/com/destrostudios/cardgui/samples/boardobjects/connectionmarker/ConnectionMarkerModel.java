package com.destrostudios.cardgui.samples.boardobjects.connectionmarker;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.TransformedBoardObject;
import lombok.Getter;

@Getter
public class ConnectionMarkerModel extends BoardObjectModel {

    private TransformedBoardObject sourceBoardObject;
    private TransformedBoardObject targetBoardObject;

    public void setSourceBoardObject(TransformedBoardObject sourceBoardObject) {
        updateIfNotEquals(this.sourceBoardObject, sourceBoardObject, () -> this.sourceBoardObject = sourceBoardObject);
    }

    public void setTargetBoardObject(TransformedBoardObject targetBoardObject) {
        updateIfNotEquals(this.targetBoardObject, targetBoardObject, () -> this.targetBoardObject = targetBoardObject);
    }
}
