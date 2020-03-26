package com.destrostudios.cardgui.animations;

import com.destrostudios.cardgui.Animation;

public class StagedAnimation extends Animation {

    protected Animation[] stages;
    private int currentStageIndex;

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        Animation currentStage = stages[currentStageIndex];
        currentStage.update(lastTimePerFrame);
        if (currentStage.isFinished()) {
            currentStageIndex++;
        }
    }

    @Override
    public boolean isFinished() {
        return (currentStageIndex >= stages.length);
    }
}
