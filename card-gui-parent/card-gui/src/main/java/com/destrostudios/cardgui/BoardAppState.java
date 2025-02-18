package com.destrostudios.cardgui;

import com.destrostudios.cardgui.annotations.*;
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
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.util.TempVars;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class BoardAppState extends BaseAppState implements ActionListener {

    public BoardAppState(Board board, Node rootNode) {
        this.board = board;
        this.rootNode = rootNode;
    }
    @Getter
    private Board board;
    private Node rootNode;
    private Node mouseVisibleNode = new Node();
    private Node mouseInvisibleNode = new Node();
    private Application application;
    private RayCasting rayCasting;
    private HashMap<BoardObject, Node> boardObjectNodes = new HashMap<>();
    @Getter
    private BoardObject hoveredBoardObject;
    private float hoverDuration;
    @Getter
    private BoardObject draggedBoardObject;
    private InteractivitySource draggedBoardObjectInteractivitySource;
    @Getter
    private TransformedBoardObject inspectedBoardObject;
    private Node draggedNode;
    private DraggedNodeTilter draggedNodeTilter;
    private TargetArrow aimTargetArrow;

    @Override
    protected void initialize(Application app) {
        application = app;
        rootNode.attachChild(mouseVisibleNode);
        rootNode.attachChild(mouseInvisibleNode);
        rayCasting = new RayCasting(application);
        draggedNodeTilter = new DraggedNodeTilter(board.getSettings());
        aimTargetArrow = new TargetArrow();
        aimTargetArrow.setVisibleToMouse(false);
        initializeInputs();
    }

    private void initializeInputs() {
        application.getInputManager().addMapping(board.getSettings().getInputActionPrefix() + "_mouse_click_left", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        application.getInputManager().addMapping(board.getSettings().getInputActionPrefix() + "_mouse_click_middle", new MouseButtonTrigger(MouseInput.BUTTON_MIDDLE));
        application.getInputManager().addMapping(board.getSettings().getInputActionPrefix() + "_mouse_click_right", new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        application.getInputManager().addListener(
            this,
            board.getSettings().getInputActionPrefix() + "_mouse_click_left",
            board.getSettings().getInputActionPrefix() + "_mouse_click_middle",
            board.getSettings().getInputActionPrefix() + "_mouse_click_right"
        );
    }

    @Override
    public void update(float lastTimePerFrame) {
        super.update(lastTimePerFrame);
        Vector2f cursorPositionScreen = application.getInputManager().getCursorPosition();
        Vector3f cursorPositionWorld = getWorldPosition(cursorPositionScreen);
        updateHoveredBoardObject(cursorPositionWorld, lastTimePerFrame);
        if ((draggedBoardObject != null) && (draggedBoardObject.getInteractivity(draggedBoardObjectInteractivitySource) instanceof AimToTargetInteractivity)) {
            updateAimTargetArrow(cursorPositionWorld);
        }
        updateBoardObjects(lastTimePerFrame);
        if ((draggedBoardObject != null) && (draggedBoardObject.getInteractivity(draggedBoardObjectInteractivitySource) instanceof DragToPlayInteractivity)) {
            updateDragTransformation(cursorPositionScreen, cursorPositionWorld, lastTimePerFrame);
        }
    }

    private void updateAimTargetArrow(Vector3f cursorPositionWorld) {
        AimToTargetInteractivity dragToTargetInteractivity = (AimToTargetInteractivity) draggedBoardObject.getInteractivity(draggedBoardObjectInteractivitySource);
        Vector3f targetLocation = cursorPositionWorld;
        if (dragToTargetInteractivity.getTargetSnapMode() != TargetSnapMode.NEVER) {
            BoardObject hoveredBoardObject = getHoveredInteractivityTarget(dragToTargetInteractivity.getTargetSnapMode() == TargetSnapMode.VALID);
            if (hoveredBoardObject != null) {
                targetLocation = getNode(hoveredBoardObject).getLocalTranslation();
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
            Node parentNode = (boardObject.isVisibleToMouse() ? mouseVisibleNode : mouseInvisibleNode);
            parentNode.attachChild(node);
            BoardObjectVisualizer oldVisualizer = boardObject.getCurrentVisualizer();
            BoardObjectVisualizer newVisualizer = board.getVisualizer(boardObject);
            if (boardObject.needsVisualizationUpdate() || (newVisualizer != oldVisualizer)) {
                if (newVisualizer != oldVisualizer) {
                    if (oldVisualizer != null) {
                        oldVisualizer.removeVisualization(node);
                    }
                    newVisualizer.createVisualization(node, application.getAssetManager());
                }
                newVisualizer.updateVisualization(node, boardObject, application.getAssetManager());
                boardObject.onVisualizationUpdated(newVisualizer);
            }
            if (boardObject instanceof TransformedBoardObject) {
                TransformedBoardObject transformedBoardObject = (TransformedBoardObject) boardObject;
                node.setLocalTranslation(transformedBoardObject.position().getCurrentValue());
                node.setLocalRotation(transformedBoardObject.rotation().getCurrentValue());
                node.setLocalScale(transformedBoardObject.scale().getCurrentValue());
            }
        }
    }

    private void updateDragTransformation(Vector2f cursorPositionScreen, Vector3f cursorPositionWorld, float lastTimePerFrame) {
        draggedNode.setLocalTranslation(cursorPositionWorld);
        draggedNode.setLocalRotation(getCameraFacingRotation());
        if (board.getSettings().isDragTiltEnabled()) {
            draggedNodeTilter.update(draggedNode, cursorPositionScreen, application.getCamera().getUp(), lastTimePerFrame);
        }
        if (draggedBoardObject instanceof TransformedBoardObject) {
            TransformedBoardObject transformedBoardObject = (TransformedBoardObject) draggedBoardObject;
            transformedBoardObject.position().setCurrentValue(draggedNode.getLocalTranslation());
            transformedBoardObject.rotation().setCurrentValue(draggedNode.getLocalRotation());
        }
    }

    private Node getOrCreateNode(BoardObject boardObject) {
        Node node = getNode(boardObject);
        if (node == null) {
            node = new Node();
            node.setUserData("boardObjectId", boardObject.getId());
            boardObjectNodes.put(boardObject, node);
        }
        return node;
    }

    private void removeNode(BoardObject boardObject) {
        Node node = getNode(boardObject);
        // The node can be null if the board object was registered and instantly unregistered before the node was ever created
        if (node != null) {
            BoardObjectVisualizer visualizer = boardObject.getCurrentVisualizer();
            if (visualizer != null) {
                // Important as the visualizer could've done a lot of stuff besides attaching something to the node
                visualizer.removeVisualization(node);
                boardObject.setCurrentVisualizer(null);
            }
            node.getParent().detachChild(node);
            boardObjectNodes.remove(boardObject);
        }
    }

    public Node getNode(BoardObject boardObject) {
        return boardObjectNodes.get(boardObject);
    }

    @Override
    public void onAction(String name, boolean isPressed, float lastTimePerFrame) {
        InteractivitySource interactivitySource = getInteractivitySource(name);
        if (interactivitySource != null) {
            if (isPressed) {
                if (hoveredBoardObject != null) {
                    Interactivity interactivity = hoveredBoardObject.getInteractivity(interactivitySource);
                    if (interactivity instanceof ClickInteractivity) {
                        interactivity.trigger(hoveredBoardObject, null);
                    } else if (interactivity instanceof DragToPlayInteractivity) {
                        setDraggedBoardObject(hoveredBoardObject, interactivitySource);
                    } else if (interactivity instanceof AimToTargetInteractivity) {
                        setDraggedBoardObject(hoveredBoardObject, interactivitySource);
                        board.register(aimTargetArrow);
                    }
                }
            } else {
                if (draggedBoardObject != null) {
                    Interactivity interactivity = draggedBoardObject.getInteractivity(interactivitySource);
                    if (interactivity instanceof DragToPlayInteractivity) {
                        interactivity.trigger(draggedBoardObject, null);
                    } else if (interactivity instanceof AimToTargetInteractivity) {
                        BoardObject hoveredBoardObject = getHoveredInteractivityTarget(true);
                        if (hoveredBoardObject != null) {
                            interactivity.trigger(draggedBoardObject, hoveredBoardObject);
                        }
                        board.unregister(aimTargetArrow);
                    }
                    if (draggedBoardObject instanceof TransformedBoardObject) {
                        TransformedBoardObject transformedBoardObject = (TransformedBoardObject) draggedBoardObject;
                        transformedBoardObject.setTransformationEnabled(true);
                    }
                    clearDraggedBoardObject();
                }
            }
        }
    }

    private InteractivitySource getInteractivitySource(String actionName) {
        if (actionName.equals(board.getSettings().getInputActionPrefix() + "_mouse_click_left")) {
            return InteractivitySource.MOUSE_LEFT;
        } else if (actionName.equals(board.getSettings().getInputActionPrefix() + "_mouse_click_middle")) {
            return InteractivitySource.MOUSE_MIDDLE;
        } else if (actionName.equals(board.getSettings().getInputActionPrefix() + "_mouse_click_right")) {
            return InteractivitySource.MOUSE_RIGHT;
        }
        return null;
    }

    private void setDraggedBoardObject(BoardObject boardObject, InteractivitySource interactivitySource) {
        draggedBoardObject = boardObject;
        draggedBoardObjectInteractivitySource = interactivitySource;
        if (draggedBoardObject instanceof TransformedBoardObject) {
            TransformedBoardObject transformedBoardObject = (TransformedBoardObject) draggedBoardObject;
            transformedBoardObject.setTransformationEnabled(false);
        }
        draggedNode = getNode(draggedBoardObject);
        updateAnnotatedModelProperties_IsBoardObjectDragged();
    }

    private void clearDraggedBoardObject() {
        draggedBoardObject = null;
        draggedBoardObjectInteractivitySource = null;
        draggedNode = null;
        updateAnnotatedModelProperties_IsBoardObjectDragged();
    }

    private void updateHoveredBoardObject(Vector3f cursorPositionWorld, float lastTimePerFrame) {
        BoardObject newHoveredBoardObject = null;
        if (draggedBoardObject == null) {
            CollisionResults hoveredCollisionResults = getHoveredCollisionResults();
            newHoveredBoardObject = getHoveredBoardObjectByFilter(hoveredCollisionResults, boardObject -> !(boardObject instanceof CardZone));
            if (newHoveredBoardObject == null) {
                newHoveredBoardObject = getHoveredBoardObjectByFilter(hoveredCollisionResults, null);
            }
        }
        if ((newHoveredBoardObject != null) && (newHoveredBoardObject == hoveredBoardObject)) {
            hoverDuration += lastTimePerFrame;
            if ((inspectedBoardObject == null) && shouldInspectHoveredBoardObject()) {
                inspect((TransformedBoardObject) hoveredBoardObject, cursorPositionWorld);
            }
        } else {
            hoverDuration = 0;
        }
        hoveredBoardObject = newHoveredBoardObject;
        updateAnnotatedModelProperties_IsBoardObjectHovered();
        if ((inspectedBoardObject != null) && (inspectedBoardObject != hoveredBoardObject) && board.getSettings().getInspector().isReadyToUninspect()) {
            uninspect();
        }
    }

    private boolean shouldInspectHoveredBoardObject() {
        Float hoverInspectionDelay = board.getSettings().getHoverInspectionDelay();
        if ((hoverInspectionDelay != null) && (hoverDuration >= hoverInspectionDelay)) {
            if (hoveredBoardObject instanceof TransformedBoardObject) {
                TransformedBoardObject transformedHoveredBoardObject = (TransformedBoardObject) hoveredBoardObject;
                return board.getSettings().getIsInspectable().test(transformedHoveredBoardObject);
            }
        }
        return false;
    }

    private void inspect(TransformedBoardObject transformedBoardObject, Vector3f cursorPositionWorld) {
        board.getSettings().getInspector().inspect(this, transformedBoardObject, cursorPositionWorld);
        inspectedBoardObject = transformedBoardObject;
        updateAnnotatedModelProperties_IsBoardObjectInspected();
    }

    private void uninspect() {
        board.getSettings().getInspector().uninspect();
        inspectedBoardObject = null;
        updateAnnotatedModelProperties_IsBoardObjectInspected();
    }

    private void updateAnnotatedModelProperties_IsBoardObjectHovered() {
        setAnnotatedModelProperties(IsBoardObjectHovered.class, boardObject -> boardObject == hoveredBoardObject);
    }

    private void updateAnnotatedModelProperties_IsBoardObjectInspected() {
        setAnnotatedModelProperties(IsBoardObjectInspected.class, boardObject -> boardObject == inspectedBoardObject);
    }

    private void updateAnnotatedModelProperties_IsBoardObjectDragged() {
        setAnnotatedModelProperties(IsBoardObjectDragged.class, boardObject -> boardObject == draggedBoardObject);
    }

    private void setAnnotatedModelProperties(Class<? extends Annotation> annotationClass, Function<BoardObject, Object> newValueFunction) {
        for (BoardObject boardObject : board.getBoardObjects()) {
            BoardObjectModel model = boardObject.getModel();
            for (Field declaredField : model.getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);
                if (declaredField.getAnnotation(annotationClass) != null) {
                    try {
                        Object oldValue = declaredField.get(model);
                        Object newValue = newValueFunction.apply(boardObject);
                        model.updateIfNotEquals(oldValue, newValue, () -> {
                            try {
                                declaredField.set(model, newValue);
                            } catch (IllegalAccessException ex) {
                                ex.printStackTrace();
                            }
                        });
                    } catch (IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private BoardObject getHoveredInteractivityTarget(boolean filterValidTargets) {
        BoardObjectFilter targetFilter = null;
        Interactivity interactivity = draggedBoardObject.getInteractivity(draggedBoardObjectInteractivitySource);
        if (filterValidTargets && (interactivity instanceof BoardObjectFilter boardObjectFilter)) {
            targetFilter = boardObjectFilter;
        }
        return getHoveredBoardObjectByFilter(getHoveredCollisionResults(), targetFilter);
    }

    private CollisionResults getHoveredCollisionResults() {
        return rayCasting.getResults_Cursor(mouseVisibleNode);
    }

    private BoardObject getHoveredBoardObjectByFilter(CollisionResults collisionResults, BoardObjectFilter filter) {
        for (int i = 0; i < collisionResults.size(); i++) {
            CollisionResult collisionResult = collisionResults.getCollision(i);
            Spatial spatial = collisionResult.getGeometry();
            while (spatial.getParent() != null) {
                spatial = spatial.getParent();
                Integer boardObjectId = spatial.getUserData("boardObjectId");
                if (boardObjectId != null) {
                    BoardObject boardObject = board.getBoardObject(boardObjectId);
                    if ((filter == null) || filter.isValid(boardObject)) {
                        return boardObject;
                    }
                }
            }
        }
        return null;
    }

    private Vector3f getWorldPosition(Vector2f screenPosition) {
        return application.getCamera().getWorldCoordinates(screenPosition, board.getSettings().getDragProjectionZ());
    }

    public Quaternion getCameraFacingRotation() {
        Quaternion quaternion = new Quaternion(application.getCamera().getRotation());
        TempVars vars = TempVars.get();
        Quaternion frontRotation = vars.quat1;
        frontRotation.fromAngles(-FastMath.HALF_PI, 0, FastMath.PI);
        quaternion.multLocal(frontRotation);
        vars.release();
        return quaternion;
    }

    @Override
    protected void cleanup(Application app) {
        application.getInputManager().deleteMapping(board.getSettings().getInputActionPrefix() + "_mouse_click_left");
        application.getInputManager().deleteMapping(board.getSettings().getInputActionPrefix() + "_mouse_click_middle");
        application.getInputManager().deleteMapping(board.getSettings().getInputActionPrefix() + "_mouse_click_right");
        application.getInputManager().removeListener(this);
        rootNode.detachChild(mouseVisibleNode);
        rootNode.detachChild(mouseInvisibleNode);
    }

    // TODO: Maybe one day

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }
}
