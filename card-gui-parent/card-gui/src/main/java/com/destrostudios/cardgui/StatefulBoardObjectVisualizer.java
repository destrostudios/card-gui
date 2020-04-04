package com.destrostudios.cardgui;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

import java.util.HashMap;

/**
 *
 * @author Carl
 */
public abstract class StatefulBoardObjectVisualizer<BoardObjectType extends BoardObject, VisualizationType> implements BoardObjectVisualizer<BoardObjectType> {

    private HashMap<Node, VisualizationType> visualizations = new HashMap<>();

    public void createVisualization(Node node, AssetManager assetManager) {
        VisualizationType visualization = createVisualizationObject(assetManager);
        visualizations.put(node, visualization);
        addVisualization(node, visualization);
    }

    protected abstract VisualizationType createVisualizationObject(AssetManager assetManager);

    protected abstract void addVisualization(Node node, VisualizationType visualization);

    public void updateVisualization(Node node, BoardObjectType boardObject, AssetManager assetManager) {
        VisualizationType visualization = visualizations.get(node);
        updateVisualizationObject(visualization, boardObject, assetManager);
    }

    protected abstract void updateVisualizationObject(VisualizationType visualization, BoardObjectType boardObject, AssetManager assetManager);

    public void removeVisualization(Node node) {
        VisualizationType visualization = visualizations.get(node);
        removeVisualizationObject(node, visualization);
    }

    protected abstract void removeVisualizationObject(Node node, VisualizationType visualization);
}
