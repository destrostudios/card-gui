package com.destrostudios.cardgui.samples.visualization.cards;

import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer;

public class SimpleCardQuad extends Mesh {

    public SimpleCardQuad(float width, float height, boolean centerX, boolean centerZ) {
        float[] positions = new float[] {
            0, 0, height,
            width, 0, height,
            width, 0, 0,
            0, 0, 0
        };
        if (centerX) {
            positions[0] -= (width / 2);
            positions[3] -= (width / 2);
            positions[6] -= (width / 2);
            positions[9] -= (width / 2);
        }
        if (centerZ) {
            positions[2] -= (height / 2);
            positions[5] -= (height / 2);
            positions[8] -= (height / 2);
            positions[11] -= (height / 2);
        }
        setBuffer(VertexBuffer.Type.Position, 3, positions);
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
