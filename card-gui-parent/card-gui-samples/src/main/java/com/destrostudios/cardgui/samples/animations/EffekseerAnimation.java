package com.destrostudios.cardgui.samples.animations;

import com.destroflyer.jme3.effekseer.model.ParticleEffect;
import com.destroflyer.jme3.effekseer.model.ParticleEffectSettings;
import com.destroflyer.jme3.effekseer.reader.EffekseerReader;
import com.destroflyer.jme3.effekseer.renderer.EffekseerControl;
import com.destrostudios.cardgui.Animation;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;

public class EffekseerAnimation extends Animation {

    public EffekseerAnimation(Node node, String assetRoot, String filePath, ParticleEffectSettings particleEffectSettings, AssetManager assetManager) {
        this(node, new EffekseerReader().read(assetRoot, filePath), particleEffectSettings, assetManager);
    }

    public EffekseerAnimation(Node node, ParticleEffect particleEffect, ParticleEffectSettings particleEffectSettings, AssetManager assetManager) {
        this.node = node;
        this.particleEffect = particleEffect;
        this.particleEffectSettings = particleEffectSettings;
        this.assetManager = assetManager;
    }
    private Node node;
    private ParticleEffect particleEffect;
    private ParticleEffectSettings particleEffectSettings;
    private AssetManager assetManager;
    private EffekseerControl effekseerControl;

    @Override
    public void start() {
        super.start();
        effekseerControl = new EffekseerControl(particleEffect, particleEffectSettings, assetManager);
        node.addControl(effekseerControl);
    }

    @Override
    public boolean isFinished() {
        for (int i = 0; i < node.getNumControls(); i++) {
            if (node.getControl(i) == effekseerControl) {
                return false;
            }
        }
        return true;
    }
}