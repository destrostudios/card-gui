package com.destrostudios.cardgui;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

/**
 *
 * @author Carl
 */
public interface BoardObjectVisualizer<BoardObjectType extends BoardObject> {
    
    void createVisualisation(Node node, AssetManager assetManager);

    void updateVisualisation(Node node, BoardObjectType boardObject, AssetManager assetManager);

    void removeVisualisation(Node node);
}
