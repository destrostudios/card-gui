package com.destrostudios.cardgui;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Predicate;

/**
 *
 * @author Carl
 */
public class Board implements GameLoopListener {

    private int nextId;
    private HashMap<Integer, BoardObject> boardObjects = new HashMap<>();
    private LinkedList<BoardObject> lastFrameRemovedBoardObjects = new LinkedList<>();
    private HashMap<Predicate<BoardObject>, BoardObjectVisualizer> boardObjectVisualizers = new HashMap<>();
    private AnimationQueue animationQueue = new AnimationQueue();

    public void addZone(CardZone zone) {
        zone.setBoard(this);
        register(zone);
    }

    public void register(BoardObject boardObject) {
        if (boardObject.getId() == -1) {
            boardObject.setId(nextId);
            boardObjects.put(nextId, boardObject);
            nextId++;
        }
    }

    public void unregister(BoardObject boardObject) {
        boardObjects.remove(boardObject.getId());
        boardObject.setId(-1);
        boardObject.setCurrentVisualizer(null);
        lastFrameRemovedBoardObjects.add(boardObject);
    }

    public <T extends BoardObject> void registerVisualizer_ZonePosition(Predicate<ZonePosition> cardZonePredicate, BoardObjectVisualizer boardObjectVisualizer) {
        registerVisualizer(boardObject -> {
            if (boardObject instanceof Card) {
                Card card = (Card) boardObject;
                return cardZonePredicate.test(card.getZonePosition());
            }
            return false;
        }, boardObjectVisualizer);
    }

    public <T extends BoardObject> void registerVisualizer_Class(Class<T> boardObjectClass, BoardObjectVisualizer boardObjectVisualizer) {
        registerVisualizer(boardObject -> boardObjectClass.isAssignableFrom(boardObject.getClass()), boardObjectVisualizer);
    }

    public <T extends BoardObject> void registerVisualizer(Predicate<BoardObject> boardObjectPredicate, BoardObjectVisualizer boardObjectVisualizer) {
        boardObjectVisualizers.put(boardObjectPredicate, boardObjectVisualizer);
    }

    public Collection<BoardObject> getBoardObjects() {
        return boardObjects.values();
    }

    public LinkedList<BoardObject> getLastFrameRemovedBoardObjects() {
        return lastFrameRemovedBoardObjects;
    }

    public BoardObject getBoardObject(Integer id) {
        return boardObjects.get(id);
    }

    public void triggerEvent(GameEvent event) {
        event.trigger(this);
    }

    public void playAnimation(Animation animation) {
        animationQueue.addAnimation(animation);
    }

    @Override
    public void update(float lastTimePerFrame) {
        lastFrameRemovedBoardObjects.clear();
        for (BoardObject boardObject : boardObjects.values()) {
            boardObject.update(lastTimePerFrame);
        }
        animationQueue.update(lastTimePerFrame);
    }

    public void finishAllTransformations() {
        for (BoardObject boardObject : boardObjects.values()) {
            if (boardObject instanceof TransformedBoardObject) {
                TransformedBoardObject transformedBoardObject = (TransformedBoardObject) boardObject;
                transformedBoardObject.position().finish();
                transformedBoardObject.rotation().finish();
            }
        }
    }

    public <T extends BoardObject> BoardObjectVisualizer<T> getVisualizer(T boardObject) {
        return boardObjectVisualizers.entrySet().stream()
                .filter(entry -> entry.getKey().test(boardObject))
                .findAny()
                .map(entry -> (BoardObjectVisualizer<T>) entry.getValue())
                .orElse(null);
    }

    public boolean isAnimationQueueBlocking() {
        return animationQueue.isBlocking();
    }
}
