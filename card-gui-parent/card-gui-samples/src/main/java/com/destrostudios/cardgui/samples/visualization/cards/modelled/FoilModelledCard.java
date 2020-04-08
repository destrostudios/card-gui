package com.destrostudios.cardgui.samples.visualization.cards.modelled;

import com.destrostudios.cardgui.samples.visualization.PaintableImage;
import com.destrostudios.cardgui.samples.visualization.materials.TimeMaterialParamControl;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.texture.Texture2D;

public class FoilModelledCard extends SimpleModelledCard {

    public FoilModelledCard(AssetManager assetManager, String modelPath, String backTexturePath, ColorRGBA sideColor) {
        super(assetManager, modelPath, backTexturePath, sideColor);

        Geometry front = getGeometry_Front();
        front.addControl(new TimeMaterialParamControl("Time"));
    }

    @Override
    protected Material createMaterial_Front(AssetManager assetManager) {
        return new Material(assetManager, "materials/foil_card/foil_card.j3md");
    }

    public void setFront(PaintableImage imageBack, PaintableImage imageFoil, PaintableImage imageFront) {
        PaintableImage diffuseMap = new PaintableImage(imageBack);
        diffuseMap.paintImage(imageFoil, 0, 0, diffuseMap.getWidth(), diffuseMap.getHeight());
        diffuseMap.paintImage(imageFront, 0, 0, diffuseMap.getWidth(), diffuseMap.getHeight());
        PaintableImage foilMap = new PaintableImage(imageFoil);
        foilMap.removeByAlphaMask(imageFront);
        setFront(flipAndCreateTexture(diffuseMap), flipAndCreateTexture(foilMap));
    }

    public void setFront(PaintableImage image) {
        setFront(flipAndCreateTexture(image), null);
    }

    public void setFront(Texture2D diffuseMap, Texture2D foilMap) {
        Material material = getGeometry_Front().getMaterial();
        material.setTexture("DiffuseMap", diffuseMap);
        material.setTexture("FoilMap", foilMap);
    }
}
