package com.destrostudios.cardgui.samples.animations;

import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.samples.animations.keyframes.AlreadyEndingKeyFrameEntryAnimation;
import com.destrostudios.cardgui.samples.animations.keyframes.TransformationKeyFrame;
import com.destrostudios.cardgui.samples.animations.keyframes.TransformationKeyFrameFactory;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.util.TempVars;

public class FlipEntryAnimation extends AlreadyEndingKeyFrameEntryAnimation {

    public FlipEntryAnimation(TransformedBoardObject transformedBoardObject, float height, int rotations, float duration) {
        super(transformedBoardObject);
        this.height = height;
        this.rotations = rotations;
        this.duration = duration;
    }
    private float height;
    private int rotations;
    private float duration;

    @Override
    protected TransformationKeyFrame[] getKeyFrames() {
        Vector3f targetPosition = transformedBoardObject.position().getDefaultTargetValue();
        Quaternion targetRotation = transformedBoardObject.rotation().getDefaultTargetValue();

        TempVars vars = TempVars.get();
        targetRotation.multLocal(vars.quat1.fromAngles(0, 0, (0.75f) * FastMath.PI));

        float eighthRotations = (4 + (rotations * 8));
        float paraboleA = ((-4 * height) / (eighthRotations * eighthRotations));
        float paraboleB = ((4 * height) / eighthRotations);
        TransformationKeyFrame[] keyFrames = new TransformationKeyFrame[(int) (eighthRotations + 1)];
        for (int i = 0; i < keyFrames.length; i++) {
            float paraboleHeight = ((paraboleA * (i * i)) + (paraboleB * i));
            Vector3f position = targetPosition.add(0, paraboleHeight, 0);
            targetRotation.multLocal(vars.quat1.fromAngles(0, 0, FastMath.QUARTER_PI));
            if (i == 0) {
                keyFrames[i] = TransformationKeyFrameFactory.createConstantKeyFrame(position.clone(), targetRotation.clone());
            } else {
                keyFrames[i] = TransformationKeyFrameFactory.createSimpleKeyFrame(position.clone(), targetRotation.clone(), (duration / eighthRotations));
            }
        }

        vars.release();

        return keyFrames;
    }
}
