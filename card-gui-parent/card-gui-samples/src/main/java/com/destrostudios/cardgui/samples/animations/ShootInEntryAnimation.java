package com.destrostudios.cardgui.samples.animations;

import com.destrostudios.cardgui.TransformedBoardObject;
import com.jme3.app.Application;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.util.TempVars;

public class ShootInEntryAnimation extends SimpleEntryAnimation {

    public ShootInEntryAnimation(TransformedBoardObject transformedBoardObject, float duration, Application application) {
        super(transformedBoardObject, duration);
        this.application = application;
    }
    private Application application;

    @Override
    protected Vector3f getStartPosition() {
        return application.getCamera().getLocation();
    }

    @Override
    protected Quaternion getStartRotation() {
        Quaternion currentRotation = application.getCamera().getRotation().clone();
        TempVars vars = TempVars.get();
        currentRotation.multLocal(vars.quat1.fromAngles(-FastMath.HALF_PI, 0, FastMath.PI));
        vars.release();
        return currentRotation;
    }
}
