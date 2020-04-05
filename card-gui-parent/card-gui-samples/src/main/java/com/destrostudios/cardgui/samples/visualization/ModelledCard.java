package com.destrostudios.cardgui.samples.visualization;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.*;
import com.jme3.texture.Texture2D;
import com.jme3.util.BufferUtils;

import java.nio.FloatBuffer;

public class ModelledCard {

    public ModelledCard(AssetManager assetManager, String modelPath, String backTexturePath, ColorRGBA sideColor) {
        node = (Node) assetManager.loadModel(modelPath);
        node.rotate(new Quaternion().fromAngleAxis(-1 * FastMath.HALF_PI, Vector3f.UNIT_X));
        node.scale(0.2f);
        node.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

        Geometry back = (Geometry) node.getChild("back");
        createTextureCoordinates(back, false);
        back.setMaterial(createMaterial_Texture(assetManager, backTexturePath));

        Geometry front = (Geometry) node.getChild("front");
        createTextureCoordinates(front, true);
        front.setMaterial(createMaterial_Texture(assetManager, null));

        Geometry side = (Geometry) node.getChild("side");
        side.setMaterial(createMaterial_Color(assetManager, sideColor));
    }
    private Node node;

    private void createTextureCoordinates(Geometry geometry, boolean invert) {
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
            if (invert) {
                texCoord_X = (1 - texCoord_X);
                texCoord_Y = (1 - texCoord_Y);
            }
            texCoords[i * 2] = texCoord_X;
            texCoords[(i * 2) + 1] = texCoord_Y;
        }
        mesh.setBuffer(VertexBuffer.Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));
    }

    private Material createMaterial_Texture(AssetManager assetManager, String texturePath) {
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", ColorRGBA.White);
        material.setColor("Diffuse", new ColorRGBA(0.75f, 0.75f, 0.75f, 1));
        material.setColor("Specular", ColorRGBA.Black);
        if (texturePath != null) {
            material.setTexture("DiffuseMap", assetManager.loadTexture(texturePath));
        }
        return material;
    }

    private Material createMaterial_Color(AssetManager assetManager, ColorRGBA color) {
        Material material = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", color);
        return material;
    }

    public void setFront(PaintableImage paintableImage) {
        Geometry geometry = (Geometry) node.getChild("front");
        geometry.getMaterial().setTexture("DiffuseMap", flipAndCreateTexture(paintableImage));
    }

    private Texture2D flipAndCreateTexture(PaintableImage paintableImage) {
        Texture2D texture = new Texture2D();
        texture.setImage(paintableImage.getImage());
        return texture;
    }

    public Node getNode() {
        return node;
    }
}