package com.destrostudios.cardgui.samples.boardobjects.targetarrow;

import com.destrostudios.cardgui.Util;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.VertexBuffer.Type;
import java.util.LinkedList;

/**
 *
 * @author Carl
 */
public class SimpleTargetArrowMesh extends Mesh {

    public SimpleTargetArrowMesh(int resolution, float width, float arcHeight) {
        this.resolution = resolution;
        this.width = width;
        this.arcHeight = arcHeight;
        setupConstantBuffers();
    }
    private float arcHeight;
    private int resolution;
    private float width;
    private LinkedList<Float> positions = new LinkedList<>();

    private void setupConstantBuffers() {
        short currentIndex = 0;
        LinkedList<Short> indices = new LinkedList<>();
        LinkedList<Float> normals = new LinkedList<>();
        LinkedList<Float> textureCoordinates = new LinkedList<>();
        for (int i=0;i<resolution;i++) {
            //Triangle 1
            indices.add((short) (currentIndex + 1));
            indices.add(currentIndex);
            indices.add((short) (currentIndex + 2));
            currentIndex += 3;
            textureCoordinates.add(0f);
            textureCoordinates.add(0f);
            textureCoordinates.add(1f);
            textureCoordinates.add(0f);
            textureCoordinates.add(1f);
            textureCoordinates.add(1f);
            for (int r=0;r<3;r++) {
                normals.add(0f);
                normals.add(0f);
                normals.add(1f);
            }
            //Triangle 2
            indices.add((short) (currentIndex + 1));
            indices.add(currentIndex);
            indices.add((short) (currentIndex + 2));
            currentIndex += 3;
            textureCoordinates.add(0f);
            textureCoordinates.add(0f);
            textureCoordinates.add(1f);
            textureCoordinates.add(1f);
            textureCoordinates.add(0f);
            textureCoordinates.add(1f);
            for (int r=0;r<3;r++) {
                normals.add(0f);
                normals.add(0f);
                normals.add(1f);
            }
        }
        setBuffer(Type.Index, 3, Util.convertToArray_Short(indices));
        setBuffer(Type.Normal, 3, Util.convertToArray_Float(normals));
        setBuffer(Type.TexCoord, 2, Util.convertToArray_Float(textureCoordinates));
        updateBound();
    }

    public void updatePositions(Vector3f sourceLocation, Vector3f targetLocation) {
        positions.clear();
        float distance = sourceLocation.distance(targetLocation);
        float interval = (distance / resolution);
        float halfWidth = (width / 2);

        float paraboleOffsetX = (resolution / -2f);
        float paraboleOffsetY = arcHeight;
        float paraboleFactor = ((-4f * arcHeight) / (resolution * resolution));

        float x = 0;
        float lastY = 0;
        float z = 0;
        for (int i=0;i<resolution;i++) {
            float paraboleX = ((i + 1) + paraboleOffsetX);
            float nextY = ((paraboleFactor * (paraboleX * paraboleX)) + paraboleOffsetY);
            //Triangle 1
            positions.add(x - halfWidth);
            positions.add(lastY);
            positions.add(z);
            positions.add(x + halfWidth);
            positions.add(lastY);
            positions.add(z);
            positions.add(x + halfWidth);
            positions.add(nextY);
            positions.add(z + interval);
            //Triangle 2
            positions.add(x - halfWidth);
            positions.add(lastY);
            positions.add(z);
            positions.add(x + halfWidth);
            positions.add(nextY);
            positions.add(z + interval);
            positions.add(x - halfWidth);
            positions.add(nextY);
            positions.add(z + interval);
            z += interval;
            lastY = nextY;
        }
        setBuffer(Type.Position, 3, Util.convertToArray_Float(positions));
        updateBound();
    }
}
