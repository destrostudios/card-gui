package com.destrostudios.cardgui.samples.visualization.cards;

import com.destrostudios.cardgui.BoardObjectModel;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Box;

public abstract class SimpleCardBoxVisualizer<CardModelType extends BoardObjectModel> extends SimpleCardVisualizer<CardModelType> {

    public SimpleCardBoxVisualizer(float width, float height, boolean lightingMaterial) {
        super(lightingMaterial);
        this.width = width;
        this.height = height;
    }
    private float width;
    private float height;

    @Override
    protected Mesh createMesh() {
        Box box = new Box(width, 0.01f, height);
        box.setBuffer(VertexBuffer.Type.TexCoord, 2, new float[] {
            1, 0, 0, 0, 0, 1, 1, 1, // top
            0, 0, 0, 1, 1, 1, 1, 0, // right
            0, 1, 1, 1, 1, 0, 0, 0, // bottom
            1, 1, 1, 0, 0, 0, 0, 1, // left
            0, 0, 0, 1, 1, 1, 1, 0, // front
            0, 0, 0, 1, 1, 1, 1, 0  // back
        });
        return box;
    }
}
