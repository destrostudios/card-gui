package com.destrostudios.cardgui.samples.boardobjects.connectionmarker;

import com.destrostudios.cardgui.samples.boardobjects.targetarrow.SimpleTargetArrowSettings;
import com.destrostudios.cardgui.samples.boardobjects.targetarrow.SimpleTargetArrowUtil;
import com.destrostudios.cardgui.samples.visualisation.SimpleAttachmentVisualizer;
import com.jme3.asset.AssetManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

/**
 *
 * @author Carl
 */
public class ConnectionMarkerVisualizer extends SimpleAttachmentVisualizer<ConnectionMarker, Geometry> {

    public ConnectionMarkerVisualizer(SimpleTargetArrowSettings settings) {
        this.settings = settings;
    }
    private SimpleTargetArrowSettings settings;

    @Override
    protected Geometry createAttachment(AssetManager assetManager) {
        return SimpleTargetArrowUtil.create(assetManager, settings);
    }

    @Override
    protected void updateAttachment(Geometry geometry, ConnectionMarker connectionMarker, AssetManager assetManager) {
        ConnectionMarkerModel connectionMarkerModel = connectionMarker.getModel();
        Vector3f sourceLocation = connectionMarkerModel.getSourceBoardObject().position().getCurrentValue();
        Vector3f targetLocation = connectionMarkerModel.getTargetBoardObject().position().getCurrentValue();
        SimpleTargetArrowUtil.update(geometry, sourceLocation, targetLocation);
    }
}
