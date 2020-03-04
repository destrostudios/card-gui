package com.destrostudios.cardgui;

import com.destrostudios.cardgui.transformations.TransformationSpeed;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedPositionTransformationSpeed2f;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class BoardSettings {

    @Builder.Default
    private String inputActionPrefix = "cardgui";
    @Builder.Default
    private float draggedCardProjectionZ = 0.8f;
    @Builder.Default
    private boolean draggedCardTiltEnabled = true;
    @Builder.Default
    private float draggedCardTiltUpdateInterval = (1f / 60);
    @Builder.Default
    private TransformationSpeed<Vector2f> draggedCardTiltCursorVelocityTransformationSpeed = new TimeBasedPositionTransformationSpeed2f(0.3f);
    @Builder.Default
    private float draggedCardTiltMaximumCursorSpeed = 600;
    @Builder.Default
    private float draggedCardTiltMaximumAngle = FastMath.QUARTER_PI;

}
