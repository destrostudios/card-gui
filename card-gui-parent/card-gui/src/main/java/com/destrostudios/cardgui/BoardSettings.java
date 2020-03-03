package com.destrostudios.cardgui;

import com.jme3.math.FastMath;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class BoardSettings {

    private String inputActionPrefix = "cardgui";
    private float draggedCardProjectionZ = 0.8f;
    private boolean draggedCardTiltEnabled = true;
    private float draggedCardTiltUpdateInterval = (1f / 60);
    private float draggedCardTiltMaximumCursorSpeed = 600;
    private float draggedCardTiltMaximumAngle = FastMath.QUARTER_PI;

}
