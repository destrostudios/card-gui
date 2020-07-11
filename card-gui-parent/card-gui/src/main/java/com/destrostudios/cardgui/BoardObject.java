package com.destrostudios.cardgui;

/**
 *
 * @author Carl
 */
public class BoardObject<ModelType extends BoardObjectModel> implements GameLoopListener {

    protected BoardObject(ModelType model) {
        this.model = model;
    }
    private int id = -1;
    private ModelType model;
    private BoardObjectVisualizer currentVisualizer;
    private Interactivity interactivity;

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

    public void clearInteractivity() {
        setInteractivity(null);
    }

    public void setInteractivity(Interactivity interactivity) {
        this.interactivity = interactivity;
    }

    public Interactivity getInteractivity() {
        return interactivity;
    }

    public void triggerInteraction(BoardObject target) {
        interactivity.trigger(this, target);
    }

    public boolean isVisibleToMouse() {
        return true;
    }
}
