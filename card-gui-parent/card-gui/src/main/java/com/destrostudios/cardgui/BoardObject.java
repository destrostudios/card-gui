package com.destrostudios.cardgui;

import java.util.HashMap;

public class BoardObject<ModelType extends BoardObjectModel> implements GameLoopListener {

    protected BoardObject(ModelType model) {
        this.model = model;
    }
    private int id = -1;
    private ModelType model;
    private BoardObjectVisualizer currentVisualizer;
    private HashMap<InteractivitySource, Interactivity> interactivities = new HashMap<>();
    private boolean visibleToMouse = true;

    @Override
    public void update(float lastTimePerFrame) {

    }

    public ModelType getModel() {
        return model;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public boolean needsVisualizationUpdate() {
        return model.wasChanged();
    }

    public void onVisualizationUpdated(BoardObjectVisualizer boardObjectVisualizer) {
        setCurrentVisualizer(boardObjectVisualizer);
        model.onUpdate();
    }

    public void setCurrentVisualizer(BoardObjectVisualizer currentVisualizer) {
        this.currentVisualizer = currentVisualizer;
    }

    public BoardObjectVisualizer getCurrentVisualizer() {
        return currentVisualizer;
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

    public boolean isVisibleToMouse() {
        return visibleToMouse;
    }

    public void setVisibleToMouse(boolean visibleToMouse) {
        this.visibleToMouse = visibleToMouse;
    }
}
