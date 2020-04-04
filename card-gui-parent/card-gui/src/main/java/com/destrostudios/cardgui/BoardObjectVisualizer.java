package com.destrostudios.cardgui;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 *
 * @author Carl
 */
public interface BoardObjectVisualizer<BoardObjectType extends BoardObject> {
    
    void createVisualization(Node node, AssetManager assetManager);

    void updateVisualization(Node node, BoardObjectType boardObject, AssetManager assetManager);

    void removeVisualization(Node node);
}
