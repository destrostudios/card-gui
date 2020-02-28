package com.destrostudios.cardgui.animations;

import com.destrostudios.cardgui.Animation;

public class StagedAnimation extends Animation {

    protected Animation[] stages;
    private int currentStageIndex;

    @Override
    public void start() {
        super.start();
        startNextStage();
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        Animation currentStage = stages[currentStageIndex];
        currentStage.update(lastTimePerFrame);
        if (currentStage.isFinished()) {
            currentStageIndex++;
            startNextStage();
        }
    }

    private void startNextStage() {
        if (currentStageIndex < stages.length) {
            stages[currentStageIndex].start();
        }
    }

    @Override
    public boolean isFinished() {
        return (currentStageIndex >= stages.length);
    }
}
