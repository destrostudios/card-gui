package com.destrostudios.cardgui.samples.visualization.cards;

import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;

public class SimpleCardQuad extends Mesh {

    public SimpleCardQuad(float width, float height) {
        setBuffer(VertexBuffer.Type.Position, 3, new float[] {
                (width / -2),   0,      (height / 2),
                (width / 2),    0,      (height / 2),
                (width / 2),    0,      (height / -2),
                (width / -2),   0,      (height / -2)
        });
        setBuffer(VertexBuffer.Type.TexCoord, 2, new float[] {
                0, 1,
                1, 1,
                1, 0,
                0, 0
        });
        setBuffer(VertexBuffer.Type.Normal, 3, new float[] {
                0, 0, 1,
                0, 0, 1,
                0, 0, 1,
                0, 0, 1
        });
        setBuffer(VertexBuffer.Type.Index, 3, new short[] {
                0, 1, 2,
                0, 2, 3
        });
        updateBound();
        setStatic();
    }
}
