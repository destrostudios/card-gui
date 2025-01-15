package com.destrostudios.cardgui.samples.visualization;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.util.BufferUtils;
import lombok.Getter;

import java.nio.FloatBuffer;

public abstract class ModelledFlatObject {

    public ModelledFlatObject(String modelPath, String geometryNameFront, String geometryNameBack, String geometryNameSide, float meshWidth, float meshHeight) {
        this.modelPath = modelPath;
        this.geometryNameFront = geometryNameFront;
        this.geometryNameBack = geometryNameBack;
        this.geometryNameSide = geometryNameSide;
        this.meshWidth = meshWidth;
        this.meshHeight = meshHeight;
    }
    private String modelPath;
    private String geometryNameFront;
    private String geometryNameBack;
    private String geometryNameSide;
    private float meshWidth;
    private float meshHeight;
    @Getter
    protected Node node;

    protected void initialize(AssetManager assetManager) {
        node = (Node) assetManager.loadModel(modelPath);
        node.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        Geometry front = getGeometry_Front();
        createTextureCoordinates(front, false);
        front.setMaterial(createMaterial_Front(assetManager));

        Geometry back = getGeometry_Back();
        createTextureCoordinates(back, true);
        back.setMaterial(createMaterial_Back(assetManager));

        Geometry side = getGeometry_Side();
        side.setMaterial(createMaterial_Side(assetManager));
    }

    private void createTextureCoordinates(Geometry geometry, boolean invertX) {
        Mesh mesh = geometry.getMesh();
        float[] texCoords = new float[mesh.getVertexCount() * 2];
        FloatBuffer positions = mesh.getFloatBuffer(VertexBuffer.Type.Position);
        positions.rewind();
        for (int i = 0; i < mesh.getVertexCount(); i++) {
            float x = positions.get();
            float y = positions.get();
            float z = positions.get();
            float texCoord_X = (((getAxisPositionForTextureCoordinate_X(x, y, z) / meshWidth) + 1) / 2);
            float texCoord_Y = (((getAxisPositionForTextureCoordinate_Y(x, y, z) / meshHeight) + 1) / 2);
            if (invertX) {
                texCoord_X = (1 - texCoord_X);
            }
            texCoords[i * 2] = texCoord_X;
            texCoords[(i * 2) + 1] = texCoord_Y;
        }
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));
    }

    protected abstract float getAxisPositionForTextureCoordinate_X(float x, float y, float z);

    protected abstract float getAxisPositionForTextureCoordinate_Y(float x, float y, float z);

    protected abstract Material createMaterial_Front(AssetManager assetManager);

    protected abstract Material createMaterial_Back(AssetManager assetManager);

    protected abstract Material createMaterial_Side(AssetManager assetManager);

    public Material getMaterial_Front() {
        return  getGeometry_Front().getMaterial();
    }

    public Material getMaterial_Back() {
        return  getGeometry_Back().getMaterial();
    }

    public Material getMaterial_Side() {
        return  getGeometry_Side().getMaterial();
    }

    protected Geometry getGeometry_Front() {
        return getGeometry(geometryNameFront);
    }

    protected Geometry getGeometry_Back() {
        return getGeometry(geometryNameBack);
    }

    protected Geometry getGeometry_Side() {
        return getGeometry(geometryNameSide);
    }

    private Geometry getGeometry(String name) {
        return (Geometry) node.getChild(name);
    }
}
