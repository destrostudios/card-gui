package com.destrostudios.cardgui.samples.animations;

import com.destroflyer.jme3.effekseer.nativ.EffekseerControl;
import com.destrostudios.cardgui.Animation;
import com.jme3.scene.Node;

public class EffekseerAnimation extends Animation {

    public EffekseerAnimation(Node node, EffekseerControl effect) {
        this.node = node;
        this.effect = effect;
    }
    private Node node;
    private EffekseerControl effect;
    private boolean initialized;

    @Override
    public void start() {
        super.start();
        node.addControl(effect);
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        if (!initialized && effect.isPlaying()) {
            initialized = true;
        }
    }

    @Override
    public boolean isFinished() {
        return (initialized && !effect.isPlaying());
    }
}
