package com.destrostudios.cardgui.transformations.handlers;

import com.destrostudios.cardgui.GameLoopListener;
import com.destrostudios.cardgui.transformations.Transformation;
import com.destrostudios.cardgui.transformations.relative.RelativeTransformation;
import com.destrostudios.cardgui.values.ValueCompositor;
import com.destrostudios.cardgui.values.ValueSetter;
import com.jme3.math.Vector3f;

import java.util.LinkedList;
import java.util.function.Supplier;

public abstract class TransformationHandler<ValueType, TransformationType extends Transformation<ValueType>> implements ValueSetter<ValueType>, ValueCompositor<ValueType>, GameLoopListener {

    public TransformationHandler(ValueType value) {
        this.currentValue = value;
    }
    private ValueType currentValue;
    protected TransformationType transformation;
    private boolean isFixed;
    private Supplier<TransformationType> defaultTransformationProvider;
    private LinkedList<RelativeTransformation<ValueType>> relativeTransformations = new LinkedList<>();

    @Override
    public void update(float lastTimePerFrame) {
        if (transformation == null) {
            setTransformation(defaultTransformationProvider.get());
            isFixed = false;
        }
        updateTransformation(lastTimePerFrame);
        setValue(currentValue, transformation.getCurrentValue());
        if (!isFixed) {
            for (RelativeTransformation<ValueType> relativeTransformation : relativeTransformations) {
                relativeTransformation.update(lastTimePerFrame);
                compositeValue(currentValue, relativeTransformation.getCurrentValue());
            }
        }
    }

    protected void updateTransformation(float lastTimePerFrame) {
        transformation.update(lastTimePerFrame);
    }

    public void reset() {
        setTransformation(null);
    }

    public void setTransformation(TransformationType transformation) {
        this.transformation = transformation;
        isFixed = true;
    }

    public TransformationType getTransformation() {
        return transformation;
    }

    public void setCurrentValue(ValueType currentValue) {
        setValue(this.currentValue, currentValue);
    }

    public ValueType getCurrentValue() {
        return currentValue;
    }

    public void addRelativeTransformation(RelativeTransformation<ValueType> relativeTransformation) {
        relativeTransformations.add(relativeTransformation);
    }

    public void removeRelativeTransformation(RelativeTransformation<Vector3f> relativeTransformation) {
        relativeTransformations.remove(relativeTransformation);
    }

    public void setDefaultTransformationProvider(Supplier<TransformationType> defaultTransformationProvider) {
        this.defaultTransformationProvider = defaultTransformationProvider;
    }
}
