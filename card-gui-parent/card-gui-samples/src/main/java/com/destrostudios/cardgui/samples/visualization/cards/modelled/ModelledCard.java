package com.destrostudios.cardgui.samples.visualization.cards.modelled;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.*;
import com.jme3.util.BufferUtils;

import java.nio.FloatBuffer;

public abstract class ModelledCard {

    private Node node;

    protected void initialize(AssetManager assetManager, String modelPath) {
        node = (Node) assetManager.loadModel(modelPath);
        node.rotate(new Quaternion().fromAngleAxis(-1 * FastMath.HALF_PI, Vector3f.UNIT_X));
        node.scale(0.2f);
        node.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        Geometry back = getGeometry_Back();
        createTextureCoordinates(back, true);
        back.setMaterial(createMaterial_Back(assetManager));

        Geometry side = getGeometry_Side();
        side.setMaterial(createMaterial_Side(assetManager));

        Geometry front = getGeometry_Front();
        createTextureCoordinates(front, false);
        front.setMaterial(createMaterial_Front(assetManager));
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
            float texCoord_X = (((x / 2) + 1) / 2);
            float texCoord_Y = (((y / 2.8f) + 1) / 2);
            if (invertX) {
                texCoord_X = (1 - texCoord_X);
            }
            texCoords[i * 2] = texCoord_X;
            texCoords[(i * 2) + 1] = texCoord_Y;
        }
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));
    }

    protected abstract Material createMaterial_Back(AssetManager assetManager);

    protected abstract Material createMaterial_Side(AssetManager assetManager);

    protected abstract Material createMaterial_Front(AssetManager assetManager);

    public Material getMaterial_Back() {
        return  getGeometry_Back().getMaterial();
    }

    public Material getMaterial_Side() {
        return  getGeometry_Side().getMaterial();
    }

    public Material getMaterial_Front() {
        return  getGeometry_Front().getMaterial();
    }

    protected Geometry getGeometry_Back() {
        return getGeometry("back");
    }

    protected Geometry getGeometry_Side() {
        return getGeometry("side");
    }

    protected Geometry getGeometry_Front() {
        return getGeometry("front");
    }

    private Geometry getGeometry(String name) {
        return (Geometry) node.getChild(name);
    }

    public Node getNode() {
        return node;
    }
}
