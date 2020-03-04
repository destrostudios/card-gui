package com.destrostudios.cardgui.samples.visualisation;

import com.destrostudios.cardgui.CardZone;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Carl
 */
public class DebugZoneVisualizer extends SimpleAttachmentVisualizer<CardZone, Geometry> {

    @Override
    protected Geometry createAttachment(AssetManager assetManager) {
        Geometry geometry = new Geometry();
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.getAdditionalRenderState().setWireframe(true);
        geometry.setMaterial(material);
        geometry.setLocalTranslation(0, -0.1f, 0);
        return geometry;
    }

    @Override
    protected void updateAttachment(Geometry geometry, CardZone zone, AssetManager assetManager) {
        Vector2f size = getSize(zone);
        Box box = new Box((size.getX() / 2), 0.1f, (size.getY() / 2));
        geometry.setMesh(box);
        Material material = geometry.getMaterial();
        material.setColor("Color", ColorRGBA.randomColor());
    }

    protected Vector2f getSize(CardZone zone) {
        return new Vector2f(3, 3);
    }
}
