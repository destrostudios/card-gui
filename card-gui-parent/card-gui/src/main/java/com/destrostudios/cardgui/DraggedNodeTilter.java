package com.destrostudios.cardgui;

import com.destrostudios.cardgui.transformations.SimpleTargetPositionTransformation2f;
import com.destrostudios.cardgui.transformations.SimpleTargetedTransformation;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class DraggedNodeTilter {

    public DraggedNodeTilter(BoardSettings settings) {
        this.settings = settings;
        cursorVelocityTransformation = new SimpleTargetPositionTransformation2f(new Vector2f(), settings.getDraggedCardTiltCursorVelocityTransformationSpeed());
    }
    private BoardSettings settings;
    private float timeSinceLastUpdate;
    private SimpleTargetedTransformation<Vector2f> cursorVelocityTransformation;
    private Vector2f lastDraggedCursorPosition;

    public void update(Node draggedNode, Vector2f cursorPositionScreen, Vector3f cameraUp, float lastTimePerFrame) {
        timeSinceLastUpdate += lastTimePerFrame;
        if (timeSinceLastUpdate >= settings.getDraggedCardTiltUpdateInterval()) {
            if (lastDraggedCursorPosition != null) {
                Vector2f targetCursorVelocity = cursorPositionScreen.subtract(lastDraggedCursorPosition).divideLocal(settings.getDraggedCardTiltUpdateInterval());
                cursorVelocityTransformation.setTargetValue(targetCursorVelocity, true);
            }
            timeSinceLastUpdate = 0;
            lastDraggedCursorPosition = cursorPositionScreen.clone();
        }

        cursorVelocityTransformation.update(lastTimePerFrame);
        Vector2f cursorVelocity = cursorVelocityTransformation.getCurrentValue();
        float cursorSpeed = cursorVelocity.length();
        float direction = ((cursorVelocity.getX() > 0) ? 1 : -1);
        float tiltFactor = (Math.min(cursorSpeed, settings.getDraggedCardTiltMaximumCursorSpeed()) / settings.getDraggedCardTiltMaximumCursorSpeed());
        float angle = (direction * tiltFactor * settings.getDraggedCardTiltMaximumAngle());
        draggedNode.rotate(0, 0, angle);
    }
}
