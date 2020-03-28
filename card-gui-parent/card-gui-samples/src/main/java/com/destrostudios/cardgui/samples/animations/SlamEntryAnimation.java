package com.destrostudios.cardgui.samples.animations;

import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.samples.animations.keyframes.SimpleKeyFrameEntryAnimation;
import com.destrostudios.cardgui.samples.animations.keyframes.TransformationKeyFrame;
import com.destrostudios.cardgui.samples.animations.keyframes.TransformationKeyFrameFactory;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.util.TempVars;

public class SlamEntryAnimation extends SimpleKeyFrameEntryAnimation {

    public SlamEntryAnimation(TransformedBoardObject transformedBoardObject) {
        this(transformedBoardObject, 0.75f);
    }

    public SlamEntryAnimation(TransformedBoardObject transformedBoardObject, float duration) {
        super(transformedBoardObject, (duration / 3));
        this.duration = duration;
    }
    private float duration;

    @Override
    protected TransformationKeyFrame[] getKeyFrames() {
        TransformationKeyFrame[] keyFrames = new TransformationKeyFrame[3];

        Vector3f defaultTargetPosition = transformedBoardObject.position().getDefaultTargetValue();
        Quaternion defaultTargetRotation = transformedBoardObject.rotation().getDefaultTargetValue();
        keyFrames[0] = TransformationKeyFrameFactory.createConstantKeyFrame(defaultTargetPosition, defaultTargetRotation);

        Vector3f position = defaultTargetPosition.add(-0.25f, 0.5f, 0);
        TempVars vars = TempVars.get();
        Quaternion rotation = defaultTargetRotation.mult(vars.quat1.fromAngles(0, 0, (FastMath.QUARTER_PI / 2)));
        vars.release();
        keyFrames[1] = TransformationKeyFrameFactory.createSimpleKeyFrame(position, rotation, (duration / 3));

        position = defaultTargetPosition.add(0, 1, 0);
        rotation = defaultTargetRotation;
        keyFrames[2] = TransformationKeyFrameFactory.createSimpleKeyFrame(position, rotation, (duration / 3));

        return keyFrames;
    }
}
