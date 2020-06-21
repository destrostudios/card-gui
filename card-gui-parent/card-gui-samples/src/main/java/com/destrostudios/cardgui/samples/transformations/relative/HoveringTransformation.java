package com.destrostudios.cardgui.samples.transformations.relative;

import com.destrostudios.cardgui.transformations.ConstantVectorTransformation3f;
import com.jme3.math.FastMath;

public class HoveringTransformation extends ConstantVectorTransformation3f {

    public HoveringTransformation(float height, float timePerCycle) {
        this.height = height;
        this.timePerCycle = timePerCycle;
    }
    private float height;
    private float timePerCycle;
    private float passedTime;

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        passedTime += lastTimePerFrame;
        currentValue.setY(((FastMath.sin((passedTime / timePerCycle) * FastMath.TWO_PI) + 1) / 2) * height);
    }
}
