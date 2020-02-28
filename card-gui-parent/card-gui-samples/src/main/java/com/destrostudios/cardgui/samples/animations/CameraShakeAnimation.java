package com.destrostudios.cardgui.samples.animations;

import com.destrostudios.cardgui.Animation;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;

public class CameraShakeAnimation extends Animation {

    public CameraShakeAnimation(Camera camera, float amplitude, float damp) {
        this(camera,amplitude, damp, 0.01f);
    }

    public CameraShakeAnimation(Camera camera, float amplitude, float damp, float minimumAmplitude) {
        this.camera = camera;
        this.amplitude = amplitude;
        this.damp = damp;
        this.minimumAmplitude = minimumAmplitude;
    }
    private Camera camera;
    private float amplitude;
    private float damp;
    private float minimumAmplitude;
    private Vector3f shakeOffset = new Vector3f(0, 0, 0);
    private Vector3f tmpShakeOffset = new Vector3f();

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        tmpShakeOffset.set(FastMath.rand.nextFloat(), FastMath.rand.nextFloat(), FastMath.rand.nextFloat()).multLocal(amplitude);
        camera.setLocation(camera.getLocation().subtract(shakeOffset).addLocal(tmpShakeOffset));
        shakeOffset.set(tmpShakeOffset);
        amplitude *= FastMath.pow(damp, lastTimePerFrame);
    }

    @Override
    public boolean isFinished() {
        return (amplitude < minimumAmplitude);
    }
}
