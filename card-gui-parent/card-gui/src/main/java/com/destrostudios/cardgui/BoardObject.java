package com.destrostudios.cardgui;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class BoardObject<ModelType extends BoardObjectModel> implements GameLoopListener {

    protected BoardObject(ModelType model) {
        this.model = model;
    }
    private Board board;
    @Getter
    private int id = -1;
    @Getter
    private ModelType model;
    @Setter
    @Getter
    private BoardObjectVisualizer currentVisualizer;
    private HashMap<InteractivitySource, Interactivity> interactivities = new HashMap<>();
    @Setter
    @Getter
    private boolean visibleToMouse = true;

    @Override
    public void update(float lastTimePerFrame) {

    }

    public void onRegister(Board board, int id) {
        this.board = board;
        this.id = id;
    }

    public void onUnregister() {
        this.board = null;
        this.id = -1;
    }

    public boolean needsVisualizationUpdate() {
        return model.wasChanged();
    }

    public void onVisualizationUpdated(BoardObjectVisualizer boardObjectVisualizer) {
        setCurrentVisualizer(boardObjectVisualizer);
        model.onUpdate();
    }

    public void clearInteractivities() {
        interactivities.clear();
    }

    public void clearInteractivity(InteractivitySource source) {
        interactivities.remove(source);
    }

    public void setInteractivity(InteractivitySource source, Interactivity interactivity) {
        interactivities.put(source, interactivity);
    }

    public Interactivity getInteractivity(InteractivitySource source) {
        return interactivities.get(source);
    }
}
