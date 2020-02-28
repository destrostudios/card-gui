package com.destrostudios.cardgui.targetarrow;

import com.destrostudios.cardgui.JMonkeyUtil;
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
public class TargetArrow {
    
    public TargetArrow(AssetManager assetManager) {
        targetArrowMesh = new TargetArrowMesh(10, 1, 0.25f);
        geometry = new Geometry("targetArrow", targetArrowMesh);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture(new TextureKey("images/target_arrow.png", false));
        texture.setWrap(Texture.WrapMode.Repeat);
        material.setTexture("ColorMap", texture);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        material.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        geometry.setMaterial(material);
        geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        updateGeometry(new Vector3f(), new Vector3f());
    }
    private TargetArrowMesh targetArrowMesh;
    private Geometry geometry;
    
    public void updateGeometry(Vector3f sourceLocation, Vector3f targetLocation) {
        geometry.setLocalTranslation(sourceLocation);
        JMonkeyUtil.setLocalRotation(geometry, targetLocation.subtract(sourceLocation));
        targetArrowMesh.updatePositions(sourceLocation, targetLocation);
    }

    public Geometry getGeometry() {
        return geometry;
    }
}
