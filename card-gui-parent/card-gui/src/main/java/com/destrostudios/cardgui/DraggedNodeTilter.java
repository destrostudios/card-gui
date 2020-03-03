package com.destrostudios.cardgui;

import com.destrostudios.cardgui.transformations.ConstantButTargetedTransformation;
import com.destrostudios.cardgui.transformations.DynamicTransformation;
import com.destrostudios.cardgui.transformations.SimpleTargetPositionTransformation2f;
import com.destrostudios.cardgui.transformations.speeds.TimeBasedPositionTransformationSpeed2f;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class DraggedNodeTilter {

    public DraggedNodeTilter(BoardSettings settings) {
        this.settings = settings;
    }
    private BoardSettings settings;
    private float timeSinceLastUpdate;
    private DynamicTransformation<Vector2f> cursorVelocityTransformation = new ConstantButTargetedTransformation<>(new Vector2f());;
    private Vector2f lastDraggedCursorPosition;

    public void update(Node draggedNode, Vector2f cursorPositionScreen, Vector3f cameraUp, float lastTimePerFrame) {
        timeSinceLastUpdate += lastTimePerFrame;
        if (timeSinceLastUpdate >= settings.getDraggedCardTiltUpdateInterval()) {
            if (lastDraggedCursorPosition != null) {
                Vector2f targetCursorVelocity = cursorPositionScreen.subtract(lastDraggedCursorPosition).divideLocal(settings.getDraggedCardTiltUpdateInterval());
                float cursorSpeed = targetCursorVelocity.length();
                if (cursorSpeed > FastMath.FLT_EPSILON) {
                    Vector2f currentCursorVelocity = cursorVelocityTransformation.getCurrentValue();
                    cursorVelocityTransformation = new SimpleTargetPositionTransformation2f(targetCursorVelocity, new TimeBasedPositionTransformationSpeed2f(0.3f));
                    cursorVelocityTransformation.setCurrentValue(currentCursorVelocity);
                }
            }
            timeSinceLastUpdate = 0;
            lastDraggedCursorPosition = cursorPositionScreen.clone();
        }

        cursorVelocityTransformation.update(lastTimePerFrame);
        Vector2f cursorVelocity = cursorVelocityTransformation.getCurrentValue();
        float cursorSpeed = cursorVelocity.length();
        if (cursorSpeed > 0) {
            float direction = ((cursorVelocity.getX() < 0) ? 1 : -1);
            float tiltFactor = (Math.min(cursorSpeed, settings.getDraggedCardTiltMaximumCursorSpeed()) / settings.getDraggedCardTiltMaximumCursorSpeed());
            float angle = (direction * tiltFactor * settings.getDraggedCardTiltMaximumAngle());
            draggedNode.rotate(new Quaternion().fromAngleAxis(angle, cameraUp));
        }
    }
}
