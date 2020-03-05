package com.destrostudios.cardgui;

import com.destrostudios.cardgui.boardobjects.*;
import com.destrostudios.cardgui.interactivities.*;
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
public class BoardAppState extends BaseAppState implements ActionListener {

    public BoardAppState(Board board, Node rootNode, BoardSettings settings) {
        this.board = board;
        this.rootNode = rootNode;
        this.settings = settings;
    }
    private Board board;
    private Node rootNode;
    private Application application;
    private RayCasting rayCasting;
    private HashMap<BoardObject, Node> boardObjectNodes = new HashMap<>();
    private BoardObject draggedBoardObject;
    private Node draggedNode;
    private DraggedNodeTilter draggedNodeTilter;
    private TargetArrow aimTargetArrow;
    private BoardSettings settings;

    @Override
    protected void initialize(Application app) {
        application = app;
        rayCasting = new RayCasting(application);
        draggedNodeTilter = new DraggedNodeTilter(settings);
        aimTargetArrow = new TargetArrow();
        initializeInputs();
    }

    private void initializeInputs() {
        application.getInputManager().addMapping(settings.getInputActionPrefix() + "_mouse_click_left", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        application.getInputManager().addMapping(settings.getInputActionPrefix() + "_mouse_click_middle", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
        application.getInputManager().addMapping(settings.getInputActionPrefix() + "_mouse_click_right", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        application.getInputManager().addListener(
            this,
            settings.getInputActionPrefix() + "_mouse_click_left",
            settings.getInputActionPrefix() + "_mouse_click_middle",
            settings.getInputActionPrefix() + "_mouse_click_right"
        );
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        Vector2f cursorPositionScreen = application.getInputManager().getCursorPosition();
        Vector3f cursorPositionWorld = application.getCamera().getWorldCoordinates(cursorPositionScreen, settings.getDraggedCardProjectionZ());
        if ((draggedBoardObject != null) && (draggedBoardObject.getInteractivity() instanceof AimToTargetInteractivity)) {
            updateAimTargetArrow(cursorPositionWorld);
        }
        updateBoardObjects(lastTimePerFrame);
        if ((draggedBoardObject != null) && (draggedBoardObject.getInteractivity() instanceof DragToPlayInteractivity)) {
            updateDragTransformation(cursorPositionScreen, cursorPositionWorld, lastTimePerFrame);
        }
    }

    private void updateAimTargetArrow(Vector3f cursorPositionWorld) {
        AimToTargetInteractivity dragToTargetInteractivity = (AimToTargetInteractivity) draggedBoardObject.getInteractivity();
        Vector3f targetLocation = cursorPositionWorld;
        if (dragToTargetInteractivity.getTargetSnapMode() != TargetSnapMode.NEVER) {
            BoardObject hoveredBoardObject = getHoveredInteractivityTarget(dragToTargetInteractivity.getTargetSnapMode() == TargetSnapMode.VALID);
            if (hoveredBoardObject != null) {
                targetLocation = boardObjectNodes.get(hoveredBoardObject).getLocalTranslation();
            }
        }
        TargetArrowModel targetArrowModel = aimTargetArrow.getModel();
        targetArrowModel.setSourceLocation(draggedNode.getLocalTranslation());
        targetArrowModel.setTargetLocation(targetLocation);
    }

    private void updateBoardObjects(float lastTimePerFrame) {
        for (BoardObject boardObject : board.getLastFrameRemovedBoardObjects()) {
            removeNode(boardObject);
        }
        board.update(lastTimePerFrame);
        for (BoardObject boardObject : board.getBoardObjects()) {
            Node node = getOrCreateNode(boardObject);
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
    }

    private void updateDragTransformation(Vector2f cursorPositionScreen, Vector3f cursorPositionWorld, float lastTimePerFrame) {
        draggedNode.setLocalTranslation(cursorPositionWorld);
        draggedNode.setLocalRotation(application.getCamera().getRotation());
        draggedNode.rotate(-FastMath.HALF_PI, 0, FastMath.PI);
        if (settings.isDraggedCardTiltEnabled()) {
            draggedNodeTilter.update(draggedNode, cursorPositionScreen, application.getCamera().getUp(), lastTimePerFrame);
        }
        if (draggedBoardObject instanceof TransformedBoardObject) {
            TransformedBoardObject transformedBoardObject = (TransformedBoardObject) draggedBoardObject;
            transformedBoardObject.position().setCurrentValue(draggedNode.getLocalTranslation());
            transformedBoardObject.rotation().setCurrentValue(draggedNode.getLocalRotation());
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

    private void removeNode(BoardObject boardObject) {
        Node node = boardObjectNodes.get(boardObject);
        rootNode.detachChild(node);
        boardObjectNodes.remove(boardObject);
    }

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame) {
        if (name.equals(settings.getInputActionPrefix() + "_mouse_click_left")) {
            if (isPressed) {
                BoardObject boardObject = getHoveredBoardObject(BoardObjectFilter.CARD);
                if (boardObject == null) {
                    boardObject = getHoveredBoardObject(BoardObjectFilter.ZONE);
                }
                if (boardObject != null) {
                    Interactivity interactivity = boardObject.getInteractivity();
                    if (interactivity instanceof ClickInteractivity) {
                        boardObject.triggerInteraction(null);
                    } else if (interactivity instanceof DragToPlayInteractivity) {
                        setDraggedBoardObject(boardObject);
                    } else if (interactivity instanceof AimToTargetInteractivity) {
                        setDraggedBoardObject(boardObject);
                        board.register(aimTargetArrow);
                    }
                }
            } else {
                if (draggedBoardObject != null) {
                    if (draggedBoardObject.getInteractivity() instanceof DragToPlayInteractivity) {
                        draggedBoardObject.triggerInteraction(null);
                    } else if (draggedBoardObject.getInteractivity() instanceof AimToTargetInteractivity) {
                        BoardObject hoveredBoardObject = getHoveredInteractivityTarget(true);
                        if (hoveredBoardObject != null) {
                            draggedBoardObject.triggerInteraction(hoveredBoardObject);
                        }
                        board.unregister(aimTargetArrow);
                    }
                    if (draggedBoardObject instanceof TransformedBoardObject) {
                        TransformedBoardObject transformedBoardObject = (TransformedBoardObject) draggedBoardObject;
                        transformedBoardObject.setTransformationEnabled(true);
                    }
                    draggedNode = null;
                    draggedBoardObject = null;
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
