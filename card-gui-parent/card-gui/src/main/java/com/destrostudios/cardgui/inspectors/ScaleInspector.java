package com.destrostudios.cardgui.inspectors;

import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.transformations.LinearTargetVectorTransformation3f;
import com.destrostudios.cardgui.transformations.relative.ConditionalRelativeTransformation;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedVectorTransformationSpeed3f;
import com.jme3.math.Vector3f;
import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
public class ScaleInspector extends TransformInspector {

    public ScaleInspector(Vector3f scale, float duration) {
        this.scale = scale;
        this.duration = duration;
        relativeTransforms = new HashMap<>();
    }
    private Vector3f scale;
    private float duration;
    private HashMap<TransformedBoardObject<?>, ConditionalRelativeTransformation<Vector3f>> relativeTransforms;

    @Override
    public void onBoardObjectRegister(TransformedBoardObject<?> transformedBoardObject) {
        super.onBoardObjectRegister(transformedBoardObject);
        ConditionalRelativeTransformation<Vector3f> relativeTransformation = new ConditionalRelativeTransformation<>(
            new LinearTargetVectorTransformation3f(scale, new TimeBasedVectorTransformationSpeed3f(duration)),
            new LinearTargetVectorTransformation3f(new Vector3f(1, 1, 1), new TimeBasedVectorTransformationSpeed3f(duration)),
            () -> transformedBoardObject == inspectedBoardObject
        );
        relativeTransforms.put(transformedBoardObject, relativeTransformation);
        transformedBoardObject.scale().addRelativeTransformation(relativeTransformation);
    }

    @Override
    public void onBoardObjectUnregister(TransformedBoardObject<?> transformedBoardObject) {
        super.onBoardObjectUnregister(transformedBoardObject);
        ConditionalRelativeTransformation<Vector3f> relativeTransformation = relativeTransforms.remove(transformedBoardObject);
        transformedBoardObject.scale().removeRelativeTransformation(relativeTransformation);
    }
}
