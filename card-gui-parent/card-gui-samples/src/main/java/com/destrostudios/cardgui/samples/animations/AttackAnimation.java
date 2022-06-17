package com.destrostudios.cardgui.samples.animations;

import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.samples.animations.keyframes.SimpleKeyFrameEntryAnimation;
import com.destrostudios.cardgui.samples.animations.keyframes.TransformationKeyFrame;
import com.destrostudios.cardgui.samples.animations.keyframes.TransformationKeyFrameFactory;

public class AttackAnimation extends SimpleKeyFrameEntryAnimation {

    public AttackAnimation(TransformedBoardObject attacker, TransformedBoardObject defender) {
        this(attacker, defender, 0.5f);
    }

    public AttackAnimation(TransformedBoardObject attacker, TransformedBoardObject defender, float duration) {
        super(attacker, (duration / 2));
        this.defender = defender;
        this.duration = duration;
    }
    private TransformedBoardObject defender;
    private float duration;

    @Override
    protected TransformationKeyFrame[] getKeyFrames() {
        TransformationKeyFrame[] keyFrames = new TransformationKeyFrame[2];

        keyFrames[0] = TransformationKeyFrameFactory.createConstantKeyFrame(transformedBoardObject.position().getCurrentValue(), transformedBoardObject.rotation().getCurrentValue());
        keyFrames[1] = TransformationKeyFrameFactory.createLinearKeyFrame(defender.position().getCurrentValue(), defender.rotation().getCurrentValue(), (duration / 2));

        return keyFrames;
    }
}
