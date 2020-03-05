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
    private Interactivity interactivity;

    @Override
    public void update(float lastTimePerFrame) {

    }

    public ModelType getModel() {
        return model;
    }

    public void onVisualisationUpdate() {
        model.onUpdate();
    }

    public boolean needsVisualisationUpdate() {
        return (model != null) && model.wasChanged();
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
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
}
