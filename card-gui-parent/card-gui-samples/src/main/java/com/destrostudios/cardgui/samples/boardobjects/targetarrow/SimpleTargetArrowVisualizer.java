package com.destrostudios.cardgui.samples.boardobjects.targetarrow;

import com.destrostudios.cardgui.boardobjects.TargetArrow;
import com.destrostudios.cardgui.boardobjects.TargetArrowModel;
import com.destrostudios.cardgui.samples.visualization.SimpleAttachmentVisualizer;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Geometry;

/**
 *
 * @author Carl
 */
public class SimpleTargetArrowVisualizer extends SimpleAttachmentVisualizer<TargetArrow, Geometry> {

    public SimpleTargetArrowVisualizer(SimpleTargetArrowSettings settings) {
        this.settings = settings;
    }
    private SimpleTargetArrowSettings settings;

    @Override
    protected Geometry createVisualizationObject(AssetManager assetManager) {
        return SimpleTargetArrowUtil.create(assetManager, settings);
    }

    @Override
    protected void updateVisualizationObject(Geometry geometry, TargetArrow targetArrow, AssetManager assetManager) {
        TargetArrowModel targetArrowModel = targetArrow.getModel();
        SimpleTargetArrowUtil.update(geometry, targetArrowModel.getSourceLocation(), targetArrowModel.getTargetLocation());
    }
}
