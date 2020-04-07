package com.destrostudios.cardgui.samples.visualization.materials;

import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.AbstractControl;
import com.jme3.shader.VarType;

public class PulsatingMaterialParamControl extends AbstractControl {

    public PulsatingMaterialParamControl(String parameterName, float minimum, float maximum, float interval) {
        this.parameterName = parameterName;
        this.minimum = minimum;
        this.maximum = maximum;
        this.interval = interval;
    }
    private String parameterName;
    private float minimum;
    private float maximum;
    private float interval;
    private float passedTime;

    @Override
    protected void controlUpdate(float lastTimePerFrame) {
        passedTime += lastTimePerFrame;
        float progress = ((FastMath.cos(passedTime * (FastMath.TWO_PI / interval)) + 1) / 2);
        float value = (minimum + (progress * (maximum - minimum)));
        Geometry geometry = (Geometry) spatial;
        Material material = geometry.getMaterial();
        material.setParam(parameterName, VarType.Float, value);
    }

    @Override
    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
