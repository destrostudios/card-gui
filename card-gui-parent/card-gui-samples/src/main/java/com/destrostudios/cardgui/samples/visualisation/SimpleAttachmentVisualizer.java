package com.destrostudios.cardgui.samples.visualisation;

import com.destrostudios.cardgui.BoardObject;
import com.destrostudios.cardgui.BoardObjectVisualizer;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public abstract class SimpleAttachmentVisualizer<BoardObjectType extends BoardObject, SpatialType extends Spatial> implements BoardObjectVisualizer<BoardObjectType> {
    
    private static final String NAME_SPATIAL = "simpleAttachment";

    @Override
    public void createVisualisation(Node node, AssetManager assetManager) {
        SpatialType spatial = createAttachment(assetManager);
        spatial.setName(NAME_SPATIAL);
        node.attachChild(spatial);
    }

    protected abstract SpatialType createAttachment(AssetManager assetManager);

    @Override
    public void updateVisualisation(Node node, BoardObjectType boardObject, AssetManager assetManager) {
        SpatialType spatial = (SpatialType) node.getChild(NAME_SPATIAL);
        updateAttachment(spatial, boardObject, assetManager);
    }

    protected abstract void updateAttachment(SpatialType spatial, BoardObjectType boardObject, AssetManager assetManager);

    @Override
    public void removeVisualisation(Node node) {
        node.detachChildNamed(NAME_SPATIAL);
    }
}
