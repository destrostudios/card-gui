package com.destrostudios.cardgui.samples.animations;

import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.samples.animations.keyframes.AlreadyEndingKeyFrameEntryAnimation;
import com.destrostudios.cardgui.samples.animations.keyframes.TransformationKeyFrame;
import com.destrostudios.cardgui.samples.animations.keyframes.TransformationKeyFrameFactory;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.util.TempVars;

public class WhirlpoolEntryAnimation extends AlreadyEndingKeyFrameEntryAnimation {

    public WhirlpoolEntryAnimation(TransformedBoardObject transformedBoardObject, float height, float rotation, float duration) {
        super(transformedBoardObject);
        this.height = height;
        this.rotation = rotation;
        this.duration = duration;
    }
    private float height;
    private float rotation;
    private float duration;

    @Override
    protected TransformationKeyFrame[] getKeyFrames() {
        Vector3f targetPosition = transformedBoardObject.position().getDefaultTargetValue();
        Quaternion targetRotation = transformedBoardObject.rotation().getDefaultTargetValue();

        targetPosition.addLocal(0, height, 0);

        TempVars vars = TempVars.get();
        int rotationParts = (int) FastMath.ceil(rotation / FastMath.QUARTER_PI);
        TransformationKeyFrame[] keyFrames = new TransformationKeyFrame[rotationParts];
        for (int i = 0; i < rotationParts; i++) {
            targetPosition.subtractLocal(0, (height / rotationParts), 0);
            targetRotation.multLocal(vars.quat1.fromAngles(0, (rotation / rotationParts), 0));
            if (i == 0) {
                keyFrames[i] = TransformationKeyFrameFactory.createConstantKeyFrame(targetPosition.clone(), targetRotation.clone());
            } else {
                keyFrames[i] = TransformationKeyFrameFactory.createLinearKeyFrame(targetPosition.clone(), targetRotation.clone(), (duration / rotationParts));
            }
        }
        vars.release();

        return keyFrames;
    }
}
