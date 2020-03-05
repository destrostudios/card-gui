package com.destrostudios.cardgui.samples.boardobjects.targetarrow;

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
public class SimpleTargetArrowUtil {

    public static Geometry create(AssetManager assetManager, SimpleTargetArrowSettings settings) {
        Geometry geometry = new Geometry();
        geometry.setMesh(new SimpleTargetArrowMesh(settings.getResolution(), settings.getWidth(), settings.getArcHeight()));
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture(new TextureKey(settings.getTexturePath(), false));
        texture.setWrap(Texture.WrapMode.Repeat);
        material.setTexture("ColorMap", texture);
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        material.getAdditionalRenderState().setFaceCullMode(RenderState.FaceCullMode.Off);
        geometry.setMaterial(material);
        geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        return geometry;
    }

    public static void update(Geometry geometry, Vector3f sourceLocation, Vector3f targetLocation) {
        geometry.setLocalTranslation(sourceLocation);
        JMonkeyUtil.setLocalRotation(geometry, targetLocation.subtract(sourceLocation));
        SimpleTargetArrowMesh simpleTargetArrowMesh = (SimpleTargetArrowMesh) geometry.getMesh();
        simpleTargetArrowMesh.updatePositions(sourceLocation, targetLocation);
    }
}
