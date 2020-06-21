package com.destrostudios.cardgui;

import com.destrostudios.cardgui.transformations.LinearTargetVectorTransformation2F;
import com.destrostudios.cardgui.transformations.SimpleTargetedTransformation;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class DraggedNodeTilter {

    public DraggedNodeTilter(BoardSettings settings) {
        this.settings = settings;
        cursorVelocityTransformation = new LinearTargetVectorTransformation2F(new Vector2f(), settings.getDragTiltCursorVelocityTransformationSpeed());
    }
    private BoardSettings settings;
    private float timeSinceLastUpdate;
    private SimpleTargetedTransformation<Vector2f> cursorVelocityTransformation;
    private Vector2f lastDraggedCursorPosition;

    public void update(Node draggedNode, Vector2f cursorPositionScreen, Vector3f cameraUp, float lastTimePerFrame) {
        timeSinceLastUpdate += lastTimePerFrame;
        if (timeSinceLastUpdate >= settings.getDragTiltUpdateInterval()) {
            if (lastDraggedCursorPosition != null) {
                Vector2f targetCursorVelocity = cursorPositionScreen.subtract(lastDraggedCursorPosition).divideLocal(settings.getDragTiltUpdateInterval());
                cursorVelocityTransformation.setTargetValue(targetCursorVelocity, true);
            }
            timeSinceLastUpdate = 0;
            lastDraggedCursorPosition = cursorPositionScreen.clone();
        }

        cursorVelocityTransformation.update(lastTimePerFrame);
        Vector2f cursorVelocity = cursorVelocityTransformation.getCurrentValue();
        float cursorSpeed = cursorVelocity.length();
        float direction = ((cursorVelocity.getX() > 0) ? 1 : -1);
        float tiltFactor = (Math.min(cursorSpeed, settings.getDragTiltMaximumCursorSpeed()) / settings.getDragTiltMaximumCursorSpeed());
        float angle = (direction * tiltFactor * settings.getDragTiltMaximumAngle());
        draggedNode.rotate(0, 0, angle);
    }
}
