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
        createTextureCoordinates_Background(back, true);
        back.setMaterial(createMaterial_Texture(assetManager, backTexturePath));

        Geometry front = (Geometry) node.getChild("front");
        createTextureCoordinates_Background(front, false);
        front.setMaterial(createMaterial_Texture(assetManager, null));

        Geometry side = (Geometry) node.getChild("side");
        side.setMaterial(createMaterial_Color(assetManager, sideColor));

        Geometry image = (Geometry) node.getChild("image");
        createTextureCoordinates_Image(image);
        image.setMaterial(createMaterial_Texture(assetManager, null));
    }
    private Node node;

    private void createTextureCoordinates_Background(Geometry geometry, boolean invertX) {
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

    private void createTextureCoordinates_Image(Geometry geometry) {
        Mesh mesh = geometry.getMesh();
        float[] texCoords = new float[] {
                1, 1, // right top
                0, 1, // left top
                0, 0, // left bottom
                1, 0, // right bottom
        };
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
        setTexture("front", paintableImage);
    }

    public void setImage(PaintableImage paintableImage) {
        setTexture("image", paintableImage);
    }

    private void setTexture(String geometryName, PaintableImage paintableImage) {
        Geometry geometry = (Geometry) node.getChild(geometryName);
        geometry.getMaterial().setTexture("DiffuseMap", flipAndCreateTexture(paintableImage));
    }

    private Texture2D flipAndCreateTexture(PaintableImage paintableImage) {
        paintableImage.flipY();
        Texture2D texture = new Texture2D();
        texture.setImage(paintableImage.getImage());
        return texture;
    }

    public Node getNode() {
        return node;
    }
}
