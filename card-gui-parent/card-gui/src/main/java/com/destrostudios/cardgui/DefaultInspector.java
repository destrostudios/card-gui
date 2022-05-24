package com.destrostudios.cardgui;

import com.destrostudios.cardgui.transformations.LinearTargetRotationTransformation;
import com.destrostudios.cardgui.transformations.LinearTargetVectorTransformation3f;
import com.destrostudios.cardgui.transformations.TransformationSpeed;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedRotationTransformationSpeed;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedVectorTransformationSpeed3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import lombok.AllArgsConstructor;

import java.util.function.Supplier;

@AllArgsConstructor
public class DefaultInspector implements Inspector {

    public DefaultInspector() {
        scale = new Vector3f(1, 1, 1);
        positionTransformationSpeed = () -> new TimeBasedVectorTransformationSpeed3f(0.3f);
        rotationTransformationSpeed = () -> new TimeBasedRotationTransformationSpeed(0.3f);
        scaleTransformationSpeed = () -> new TimeBasedVectorTransformationSpeed3f(0.3f);
    }
    private Vector3f scale;
    private Supplier<TransformationSpeed<Vector3f>> positionTransformationSpeed;
    private Supplier<TransformationSpeed<Quaternion>> rotationTransformationSpeed;
    private Supplier<TransformationSpeed<Vector3f>> scaleTransformationSpeed;

    @Override
    public void inspect(BoardAppState boardAppState, TransformedBoardObject<?> transformedBoardObject, Vector3f cursorPositionWorld) {
        transformedBoardObject.position().setTransformation(new LinearTargetVectorTransformation3f(cursorPositionWorld, positionTransformationSpeed.get()));
        transformedBoardObject.rotation().setTransformation(new LinearTargetRotationTransformation(boardAppState.getCameraFacingRotation(), rotationTransformationSpeed.get()));
        transformedBoardObject.scale().setTransformation(new LinearTargetVectorTransformation3f(scale, scaleTransformationSpeed.get()));
    }

    @Override
    public void uninspect(TransformedBoardObject<?> transformedBoardObject) {
        transformedBoardObject.resetTransformations();
    }
}
