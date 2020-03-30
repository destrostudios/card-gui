package com.destrostudios.cardgui.samples.animations;

import com.destrostudios.cardgui.Animation;
import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

public class SnowAnimation extends Animation {

    public SnowAnimation(AssetManager assetManager, Camera camera, Node rootNode) {
        this.assetManager = assetManager;
        this.camera = camera;
        this.rootNode = rootNode;
    }
    private AssetManager assetManager;
    private Node rootNode;
    private Camera camera;
    private Node cameraNode = new Node();

    @Override
    public void start() {
        super.start();
        ParticleEmitter particleEmitter = new ParticleEmitter("buff", ParticleMesh.Type.Triangle, 80);
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        material.setTexture("Texture", assetManager.loadTexture("images/effects/snow_additive.png"));
        particleEmitter.setMaterial(material);
        particleEmitter.setImagesX(2);
        particleEmitter.setImagesY(2);
        particleEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 4, 0));
        particleEmitter.getParticleInfluencer().setVelocityVariation(0.3f);
        particleEmitter.setGravity(Vector3f.ZERO);
        particleEmitter.setInWorldSpace(false);
        particleEmitter.setNumParticles(180);
        particleEmitter.setParticlesPerSec(30);
        particleEmitter.setLowLife(6);
        particleEmitter.setHighLife(6);
        particleEmitter.setEndSize(0.2f);
        particleEmitter.setStartSize(0.2f);
        particleEmitter.setEndSize(0.2f);
        particleEmitter.setStartColor(ColorRGBA.White);
        particleEmitter.setEndColor(new ColorRGBA(1, 1, 1, 0));
        particleEmitter.setGravity(0, 1, 0);
        particleEmitter.getParticleInfluencer().setInitialVelocity(Vector3f.UNIT_XYZ.mult(5));
        particleEmitter.getParticleInfluencer().setVelocityVariation(1);
        particleEmitter.setLocalTranslation(0, 30, 40);
        cameraNode.attachChild(particleEmitter);
        rootNode.attachChild(cameraNode);
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        cameraNode.setLocalTranslation(camera.getLocation());
        cameraNode.setLocalRotation(camera.getRotation());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
