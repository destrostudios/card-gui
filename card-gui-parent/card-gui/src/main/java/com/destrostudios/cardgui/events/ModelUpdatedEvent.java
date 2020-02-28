package com.destrostudios.cardgui.events;

import com.destrostudios.cardgui.Board;
import com.destrostudios.cardgui.BoardObject;
import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.GameEvent;

/**
 *
 * @author Carl
 */
public class ModelUpdatedEvent<ModelType extends BoardObjectModel> extends GameEvent {

    public ModelUpdatedEvent(BoardObject boardObject) {
        this.boardObject = boardObject;
    }
    private BoardObject<ModelType> boardObject;

    @Override
    public void trigger(Board board) {
        boardObject.checkForVisualisationUpdate();
    }
}
