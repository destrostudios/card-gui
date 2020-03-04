package com.destrostudios.cardgui.transformations;

public abstract class SimpleTargetedTransformation<ValueType> extends TargetedTransformation<ValueType> {

    public SimpleTargetedTransformation(ValueType value, ValueType targetValue, TransformationSpeed<ValueType> transformationSpeed) {
        super(value, targetValue);
        this.transformationSpeed = transformationSpeed;
        setTargetValue(targetValue, true);
    }
    protected TransformationSpeed<ValueType> transformationSpeed;

    public void setTargetValue(ValueType targetValue, boolean resetSpeed) {
        if (!isValueEquals(targetValue, this.targetValue)) {
            setTargetValue(targetValue);
            if (resetSpeed) {
                resetSpeed();
            }
        }
    }

    protected boolean isValueEquals(ValueType value1, ValueType value2) {
        return value1.equals(value2);
    }

    public void resetSpeed() {
        transformationSpeed.reset();
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        transformationSpeed.update(lastTimePerFrame);
    }

    @Override
    public ValueType getNewValue(ValueType currentValue, float lastTimePerFrame) {
        float speed = transformationSpeed.getSpeed(currentValue, targetValue);
        return getNewValue(currentValue, targetValue, speed, lastTimePerFrame);
    }

    protected abstract ValueType getNewValue(ValueType currentValue, ValueType targetValue, float speed, float lastTimePerFrame);

    @Override
    public abstract SimpleTargetedTransformation clone();
}
