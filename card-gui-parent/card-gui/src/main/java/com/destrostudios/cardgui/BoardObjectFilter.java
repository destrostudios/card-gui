package com.destrostudios.cardgui;

/**
 *
 * @author Carl
 */
public interface BoardObjectFilter {

    BoardObjectFilter CARD = (boardObject) -> boardObject instanceof Card;
    BoardObjectFilter ZONE = (boardObject) -> boardObject instanceof CardZone;

    static BoardObjectFilter getCompositeFilter(BoardObjectFilter... boardObjectFilters) {
        return (boardObject) -> {
            for (BoardObjectFilter boardObjectFilter : boardObjectFilters) {
                if (!boardObjectFilter.isValid(boardObject)) {
                    return false;
                }
            }
            return true;
        };
    };

    static BoardObjectFilter getClassFilter(Class<? extends BoardObject> boardObjectClass) {
        return (boardObject) -> boardObjectClass.isAssignableFrom(boardObject.getClass());
    };

    static BoardObjectFilter getInstanceFilter(BoardObject boardObject) {
        return (currentBoardObject) -> currentBoardObject == boardObject;
    };

    boolean isValid(BoardObject boardObject);
}
