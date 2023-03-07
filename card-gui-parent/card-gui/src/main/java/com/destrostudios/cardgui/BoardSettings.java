package com.destrostudios.cardgui;

import com.destrostudios.cardgui.inspectors.DefaultInspector;
import com.destrostudios.cardgui.transformations.TransformationSpeed;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedVectorTransformationSpeed2f;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedVectorTransformationSpeed3f;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedRotationTransformationSpeed;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import lombok.*;

import java.util.function.Predicate;
import java.util.function.Supplier;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class BoardSettings {

    @Builder.Default
    private String inputActionPrefix = "cardgui";
    @Builder.Default
    private Supplier<TransformationSpeed<Vector3f>> cardInZonePositionTransformationSpeed = () -> new TimeBasedVectorTransformationSpeed3f(1.5f);
    @Builder.Default
    private Supplier<TransformationSpeed<Quaternion>> cardInZoneRotationTransformationSpeed = () -> new TimeBasedRotationTransformationSpeed(1);
    @Builder.Default
    private Supplier<TransformationSpeed<Vector3f>> cardInZoneScaleTransformationSpeed = () -> new TimeBasedVectorTransformationSpeed3f(1.5f);
    @Builder.Default
    private float dragProjectionZ = 0.8f;
    @Builder.Default
    private boolean dragTiltEnabled = true;
    @Builder.Default
    private float dragTiltUpdateInterval = (1f / 60);
    @Builder.Default
    private TransformationSpeed<Vector2f> dragTiltCursorVelocityTransformationSpeed = new TimeBasedVectorTransformationSpeed2f(0.3f);
    @Builder.Default
    private float dragTiltMaximumCursorSpeed = 600;
    @Builder.Default
    private float dragTiltMaximumAngle = FastMath.QUARTER_PI;
    @Builder.Default
    private Float hoverInspectionDelay = null;
    @Builder.Default
    private Predicate<TransformedBoardObject<?>> isInspectable = transformedBoardObject -> transformedBoardObject instanceof Card;
    @Builder.Default
    private Inspector inspector = new DefaultInspector(0.3f);

}
