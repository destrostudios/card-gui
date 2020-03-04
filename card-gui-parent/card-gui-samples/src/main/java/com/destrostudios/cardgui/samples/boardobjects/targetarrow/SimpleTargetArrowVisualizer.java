package com.destrostudios.cardgui.samples.boardobjects.targetarrow;

import com.destrostudios.cardgui.JMonkeyUtil;
import com.destrostudios.cardgui.boardobjects.TargetArrow;
import com.destrostudios.cardgui.boardobjects.TargetArrowModel;
import com.destrostudios.cardgui.samples.visualisation.SimpleAttachmentVisualizer;
import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.texture.Texture;

/**
 *
 * @author Carl
 */
public class SimpleTargetArrowVisualizer extends SimpleAttachmentVisualizer<TargetArrow, Geometry> {

    @Override
    protected Geometry createAttachment(AssetManager assetManager) {
        Geometry geometry = new Geometry();
        geometry.setMesh(new SimpleTargetArrowMesh(10, 1, 0.25f));
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture(new TextureKey("images/target_arrow.png", false));
        texture.setWrap(Texture.WrapMode.Repeat);
        material.setTexture("ColorMap", texture);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        material.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        geometry.setMaterial(material);
        geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        updateGeometry(geometry, new Vector3f(), new Vector3f());
        return geometry;
    }

    @Override
    protected void updateAttachment(Geometry geometry, TargetArrow targetArrow, AssetManager assetManager) {
        TargetArrowModel targetArrowModel = targetArrow.getModel();
        updateGeometry(geometry, targetArrowModel.getSourceLocation(), targetArrowModel.getTargetLocation());
    }

    public void updateGeometry(Geometry geometry, Vector3f sourceLocation, Vector3f targetLocation) {
        geometry.setLocalTranslation(sourceLocation);
        JMonkeyUtil.setLocalRotation(geometry, targetLocation.subtract(sourceLocation));
        SimpleTargetArrowMesh simpleTargetArrowMesh = (SimpleTargetArrowMesh) geometry.getMesh();
        simpleTargetArrowMesh.updatePositions(sourceLocation, targetLocation);
    }
}
