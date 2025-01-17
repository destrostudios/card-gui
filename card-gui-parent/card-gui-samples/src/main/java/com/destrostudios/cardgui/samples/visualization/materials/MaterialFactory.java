package com.destrostudios.cardgui.samples.visualization.materials;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;

public abstract class MaterialFactory {

    public static Material unshaded(AssetManager assetManager) {
        return new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    }

    public static Material unshadedTexture(AssetManager assetManager) {
        return unshaded(assetManager);
    }

    public static Material unshadedColor(AssetManager assetManager, ColorRGBA color) {
        Material material = unshaded(assetManager);
        material.setColor("Color", color);
        return material;
    }

    public static Material unshadedTexture(AssetManager assetManager, String texturePath) {
        Material material = unshaded(assetManager);
        material.setTexture("ColorMap", assetManager.loadTexture(texturePath));
        return material;
    }

    public static Material lighting(AssetManager assetManager) {
        return new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
    }

    public static Material lightingColor(AssetManager assetManager, ColorRGBA color) {
        Material material = lighting(assetManager);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", color);
        return material;
    }

    public static Material lightingTexture(AssetManager assetManager) {
        Material material = lighting(assetManager);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", ColorRGBA.White);
        material.setColor("Diffuse", new ColorRGBA(0.75f, 0.75f, 0.75f, 1));
        material.setColor("Specular", ColorRGBA.Black);
        return material;
    }

    public static Material lightingTexture(AssetManager assetManager, String texturePath) {
        Material material = lightingTexture(assetManager);
        material.setTexture("DiffuseMap", assetManager.loadTexture(texturePath));
        return material;
    }
}
