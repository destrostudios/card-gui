package com.destrostudios.cardgui.samples.visualization.cards;

import com.destrostudios.cardgui.BoardObjectModel;
import com.jme3.scene.Mesh;

public abstract class SimpleCardQuadVisualizer<CardModelType extends BoardObjectModel> extends SimpleCardVisualizer<CardModelType> {

    public SimpleCardQuadVisualizer(float width, float height, boolean centerX, boolean centerZ, boolean lightingMaterial) {
        super(lightingMaterial);
        this.width = width;
        this.height = height;
        this.centerX = centerX;
        this.centerZ = centerZ;
    }
    private float width;
    private float height;
    private boolean centerX;
    private boolean centerZ;

    @Override
    protected Mesh createMesh() {
        return new SimpleCardQuad(width, height, centerX, centerZ);
    }
}
