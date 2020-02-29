package com.destrostudios.cardgui;

import com.destrostudios.cardgui.interactivities.AimToTargetInteractivity;
import com.destrostudios.cardgui.targetarrow.TargetArrow;
import com.destrostudios.cardgui.targetarrow.TargetSnapMode;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.HashMap;

/**
 *
 * @author Carl
 */
public class BoardAppState<CardModelType extends BoardObjectModel> extends BaseAppState implements ActionListener {

    public BoardAppState(Board<CardModelType> board, Node rootNode, BoardSettings settings) {
        this.board = board;
        this.rootNode = rootNode;
        this.settings = settings;
    }
    private Board<CardModelType> board;
    private Node rootNode;
    private Application application;
    private RayCasting rayCasting;
    private HashMap<BoardObject, Node> boardObjectNodes = new HashMap<>();
    private BoardObject<CardModelType> draggedBoardObject;
    private Node draggedNode;
    private TargetArrow targetArrow;
    private BoardSettings settings;

    @Override
    protected void initialize(Application app) {
        application = app;
        rayCasting = new RayCasting(application);
        targetArrow = new TargetArrow(application.getAssetManager());
        application.getInputManager().addMapping(settings.getInputActionPrefix() + "mouse_click_left", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        application.getInputManager().addMapping(settings.getInputActionPrefix() + "mouse_click_middle", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
        application.getInputManager().addMapping(settings.getInputActionPrefix() + "mouse_click_right", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        application.getInputManager().addListener(
            this,
            settings.getInputActionPrefix() + "mouse_click_left",
            settings.getInputActionPrefix() + "mouse_click_middle",
            settings.getInputActionPrefix() + "mouse_click_right"
        );
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        board.update(lastTimePerFrame);
        for (BoardObject boardObject : board.getBoardObjects()) {
            Node node = getOrCreateNode(boardObject);
            if (boardObject == draggedBoardObject) {
                continue;
            }

            if (boardObject.needsVisualisationUpdate()) {
                board.getVisualizer(boardObject).updateVisualisation(node, boardObject, application.getAssetManager());
                boardObject.onVisualisationUpdate();
            }
            if (boardObject instanceof TransformedBoardObject) {
                TransformedBoardObject transformedBoardObject = (TransformedBoardObject) boardObject;
                node.setLocalTranslation(transformedBoardObject.position().getCurrentValue());
                node.setLocalRotation(transformedBoardObject.rotation().getCurrentValue());
            }
        }
        if (draggedNode != null) {
            Vector2f cursorPosition = application.getInputManager().getCursorPosition();
            Vector3f cursorWorldLocation = application.getCamera().getWorldCoordinates(cursorPosition, settings.getDraggedCardProjectionZ());

            Interactivity interactivity = draggedBoardObject.getInteractivity();
            switch (interactivity.getType()) {
                case DRAG:
                    draggedNode.setLocalTranslation(cursorWorldLocation);
                    // Set rotation so the node faces the camera (2d-like)
                    draggedNode.setLocalRotation(application.getCamera().getRotation());
                    draggedNode.rotate(-FastMath.HALF_PI, 0, FastMath.PI);
                    if (draggedBoardObject instanceof TransformedBoardObject) {
                        TransformedBoardObject transformedBoardObject = (TransformedBoardObject) draggedBoardObject;
                        transformedBoardObject.position().setCurrentValue(draggedNode.getLocalTranslation());
                        transformedBoardObject.rotation().setCurrentValue(draggedNode.getLocalRotation());
                    }
                    break;
                
                case AIM:
                    Vector3f sourceLocation = draggedNode.getLocalTranslation();
                    Vector3f targetLocation = cursorWorldLocation;
                    AimToTargetInteractivity dragToTargetInteractivity = (AimToTargetInteractivity) interactivity;
                    if (dragToTargetInteractivity.getTargetSnapMode() != TargetSnapMode.NEVER) {
                        BoardObject hoveredBoardObject = getHoveredInteractivityTarget(dragToTargetInteractivity.getTargetSnapMode() == TargetSnapMode.VALID);
                        if (hoveredBoardObject != null) {
                            targetLocation = boardObjectNodes.get(hoveredBoardObject).getLocalTranslation();
                        }
                    }
                    targetArrow.updateGeometry(sourceLocation, targetLocation);
                    break;
            }
        }
    }

    private Node getOrCreateNode(BoardObject boardObject) {
        Node node = boardObjectNodes.get(boardObject);
        if (node == null) {
            node = new Node();
            node.setUserData("boardObjectId", boardObject.getId());
            board.getVisualizer(boardObject).createVisualisation(node, application.getAssetManager());
            board.getVisualizer(boardObject).updateVisualisation(node, boardObject, application.getAssetManager());
            rootNode.attachChild(node);
            boardObjectNodes.put(boardObject, node);
        }
        return node;
    }

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame) {
        if (name.equals(settings.getInputActionPrefix() + "mouse_click_left")) {
            if (isPressed) {
                BoardObject boardObject = getHoveredBoardObject(BoardObjectFilter.CARD);
                if (boardObject == null) {
                    boardObject = getHoveredBoardObject(BoardObjectFilter.ZONE);
                }
                if (boardObject != null) {
                    Interactivity interactivity = boardObject.getInteractivity();
                    if (interactivity != null) {
                        switch (interactivity.getType()) {
                            case CLICK:
                                boardObject.triggerInteraction(null);
                                break;
                            case DRAG:
                                setDraggedBoardObject(boardObject);
                                break;
                            case AIM:
                                setDraggedBoardObject(boardObject);
                                rootNode.attachChild(targetArrow.getGeometry());
                                break;
                        }
                    }
                }
            } else {
                if (draggedBoardObject != null) {
                    switch (draggedBoardObject.getInteractivity().getType()) {
                        case DRAG:
                            draggedBoardObject.triggerInteraction(null);
                            break;

                        case AIM:
                            BoardObject hoveredBoardObject = getHoveredInteractivityTarget(true);
                            if (hoveredBoardObject != null) {
                                draggedBoardObject.triggerInteraction(hoveredBoardObject);
                            }
                            break;
                    }
                    draggedNode = null;
                    if (draggedBoardObject instanceof TransformedBoardObject) {
                        TransformedBoardObject transformedBoardObject = (TransformedBoardObject) draggedBoardObject;
                        transformedBoardObject.setTransformationEnabled(true);
                    }
                    draggedBoardObject = null;
                    rootNode.detachChild(targetArrow.getGeometry());
                }
            }
        }
    }

    private void setDraggedBoardObject(BoardObject boardObject) {
        draggedBoardObject = boardObject;
        if (draggedBoardObject instanceof TransformedBoardObject) {
            TransformedBoardObject transformedBoardObject = (TransformedBoardObject) draggedBoardObject;
            transformedBoardObject.setTransformationEnabled(false);
        }
        draggedNode = boardObjectNodes.get(draggedBoardObject);
    }
    
    private BoardObject getHoveredInteractivityTarget(boolean filterValidTargets) {
        BoardObjectFilter targetFilter = null;
        if (filterValidTargets && (draggedBoardObject.getInteractivity() instanceof BoardObjectFilter)) {
            targetFilter = (BoardObjectFilter) draggedBoardObject.getInteractivity();
        }
        return getHoveredBoardObject(targetFilter);
    }

    private BoardObject getHoveredBoardObject(BoardObjectFilter filter) {
        CollisionResults collisionResults = rayCasting.getResults_Cursor(rootNode);
        for (int i=0;i<collisionResults.size();i++) {
            CollisionResult collisionResult = collisionResults.getCollision(i);
            Spatial spatial = collisionResult.getGeometry();
            while (spatial.getParent() != null) {
                spatial = spatial.getParent();
                Integer cardId = spatial.getUserData("boardObjectId");
                if (cardId != null) {
                    BoardObject boardObject = board.getBoardObject(cardId);
                    if ((filter == null) || filter.isValid(boardObject)) {
                        return boardObject;
                    }
                }
            }
        }
        return null;
    }

    // TODO: Other appstate interface methods

    @Override
    protected void cleanup(Application app) {
        
    }

    @Override
    protected void onEnable() {
        
    }

    @Override
    protected void onDisable() {
        
    }
}
