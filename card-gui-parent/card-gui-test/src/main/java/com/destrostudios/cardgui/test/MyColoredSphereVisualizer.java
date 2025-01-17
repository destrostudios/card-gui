package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.samples.visualization.SimpleAttachmentVisualizer;
import com.destrostudios.cardgui.samples.visualization.materials.MaterialFactory;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;

public class MyColoredSphereVisualizer extends SimpleAttachmentVisualizer<MyColoredSphere, Geometry> {

    @Override
    protected Geometry createVisualizationObject(AssetManager assetManager) {
        Sphere sphereMesh = new Sphere(16, 16, 0.5f);
        Geometry geometry = new Geometry(null, sphereMesh);
        geometry.setMaterial(MaterialFactory.lightingColor(assetManager, null));
        return geometry;
    }

    @Override
    protected void updateVisualizationObject(Geometry geometry, MyColoredSphere myColoredSphere, AssetManager assetManager) {
        geometry.getMaterial().setColor("Diffuse", myColoredSphere.getModel().getColorRGBA());
    }
}
