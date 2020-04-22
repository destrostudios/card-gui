package com.destrostudios.cardgui.samples.visualization.cards;

import com.destrostudios.cardgui.BoardObjectModel;
import com.jme3.scene.Mesh;

public abstract class SimpleCardQuadVisualizer<CardModelType extends BoardObjectModel> extends SimpleCardVisualizer<CardModelType> {

    public SimpleCardQuadVisualizer(float width, float height) {
        this.width = width;
        this.height = height;
    }
    private float width;
    private float height;

    @Override
    protected Mesh createMesh() {
        return new SimpleCardQuad(width, height);
    }
}
