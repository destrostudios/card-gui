package com.destrostudios.cardgui.samples.visualization.materials;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

public abstract class MaterialFactory {

    public static Material unshaded(AssetManager assetManager) {
        return new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    }

    public static Material textureLighting(AssetManager assetManager) {
        return textureLighting(assetManager, null);
    }

    public static Material textureLighting(AssetManager assetManager, String texturePath) {
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", ColorRGBA.White);
        material.setColor("Diffuse", new ColorRGBA(0.75f, 0.75f, 0.75f, 1));
        material.setColor("Specular", ColorRGBA.Black);
        if (texturePath != null) {
            material.setTexture("DiffuseMap", assetManager.loadTexture(texturePath));
        }
        return material;
    }

    public static Material colorLighting(AssetManager assetManager, ColorRGBA color) {
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", color);
        return material;
    }
}
