package com.destrostudios.cardgui;

import com.destrostudios.cardgui.annotations.*;
import com.destrostudios.cardgui.boardobjects.*;
import com.destrostudios.cardgui.interactivities.*;
import com.destrostudios.cardgui.transformations.*;
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class BoardAppState extends BaseAppState implements ActionListener {

    public BoardAppState(Board board, Node rootNode, BoardSettings settings) {
        this.board = board;
        this.rootNode = rootNode;
        this.settings = settings;
        board.setSettings(settings);
    }
    private Board board;
    private Node rootNode;
    private Node mouseVisibleNode = new Node();
    private Node mouseInvisibleNode = new Node();
    private Application application;
    private RayCasting rayCasting;
    private HashMap<BoardObject, Node> boardObjectNodes = new HashMap<>();
    private BoardObject hoveredBoardObject;
    private float hoverDuration;
    private BoardObject draggedBoardObject;
    private InteractivitySource draggedBoardObjectInteractivitySource;
    private TransformedBoardObject inspectedBoardObject;
    private Node draggedNode;
    private DraggedNodeTilter draggedNodeTilter;
    private TargetArrow aimTargetArrow;
    private BoardSettings settings;

    @Override
    protected void initialize(Application app) {
        application = app;
        rootNode.attachChild(mouseVisibleNode);
        rootNode.attachChild(mouseInvisibleNode);
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
        if (settings.isDragTiltEnabled()) {
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
        if (actionName.equals(settings.getInputActionPrefix() + "_mouse_click_left")) {
            return InteractivitySource.MOUSE_LEFT;
        } else if (actionName.equals(settings.getInputActionPrefix() + "_mouse_click_middle")) {
            return InteractivitySource.MOUSE_MIDDLE;
        } else if (actionName.equals(settings.getInputActionPrefix() + "_mouse_click_right")) {
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
            newHoveredBoardObject = getHoveredBoardObjectByFilter(BoardObjectFilter.CARD);
            if (newHoveredBoardObject == null) {
                newHoveredBoardObject = getHoveredBoardObjectByFilter(BoardObjectFilter.ZONE);
            }
        }
        if ((newHoveredBoardObject != null) && (newHoveredBoardObject == hoveredBoardObject)) {
            hoverDuration += lastTimePerFrame;
            if ((inspectedBoardObject == null) && shouldInspectHoveredBoardObject()) {
                inspect((TransformedBoardObject) hoveredBoardObject, cursorPositionWorld);
            }
        } else {
            hoverDuration = 0;
            if ((inspectedBoardObject != null) && inspectedBoardObject.hasReachedTargetTransform()) {
                uninspect();
            }
        }
        hoveredBoardObject = newHoveredBoardObject;
        updateAnnotatedModelProperties_IsBoardObjectHovered();
    }

    private boolean shouldInspectHoveredBoardObject() {
        if (hoveredBoardObject instanceof TransformedBoardObject) {
            TransformedBoardObject transformedHoveredBoardObject = (TransformedBoardObject) hoveredBoardObject;
            if (settings.getIsInspectable().test(transformedHoveredBoardObject)) {
                Float hoverInspectionDelay = settings.getHoverInspectionDelay();
                if (hoverInspectionDelay != null) {
                    return (hoverDuration >= hoverInspectionDelay);
                }
            }
        }
        return false;
    }

    private void inspect(TransformedBoardObject transformedBoardObject, Vector3f cursorPositionWorld) {
        Quaternion cameraFacingRotation = getCameraFacingRotation();
        transformedBoardObject.position().setTransformation(new LinearTargetVectorTransformation3f(cursorPositionWorld, settings.getInspectionPositionTransformationSpeed().get()));
        transformedBoardObject.rotation().setTransformation(new LinearTargetRotationTransformation(cameraFacingRotation, settings.getInspectionRotationTransformationSpeed().get()));
        transformedBoardObject.scale().setTransformation(new LinearTargetVectorTransformation3f(settings.getInspectionScale(), settings.getInspectionScaleTransformationSpeed().get()));
        inspectedBoardObject = transformedBoardObject;
        updateAnnotatedModelProperties_IsBoardObjectInspected();
    }

    private void uninspect() {
        inspectedBoardObject.resetTransformations();
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
        if (filterValidTargets && (interactivity instanceof BoardObjectFilter)) {
            targetFilter = (BoardObjectFilter) interactivity;
        }
        return getHoveredBoardObjectByFilter(targetFilter);
    }

    private BoardObject getHoveredBoardObjectByFilter(BoardObjectFilter filter) {
        return getHoveredBoardObject(filter::isValid);
    }

    private BoardObject getHoveredBoardObject(Predicate<BoardObject> filter) {
        CollisionResults collisionResults = rayCasting.getResults_Cursor(mouseVisibleNode);
        for (int i = 0; i < collisionResults.size(); i++) {
            CollisionResult collisionResult = collisionResults.getCollision(i);
            Spatial spatial = collisionResult.getGeometry();
            while (spatial.getParent() != null) {
                spatial = spatial.getParent();
                Integer cardId = spatial.getUserData("boardObjectId");
                if (cardId != null) {
                    BoardObject boardObject = board.getBoardObject(cardId);
                    if ((filter == null) || filter.test(boardObject)) {
                        return boardObject;
                    }
                }
            }
        }
        return null;
    }

    private Vector3f getWorldPosition(Vector2f screenPosition) {
        return application.getCamera().getWorldCoordinates(screenPosition, settings.getDragProjectionZ());
    }

    private Quaternion getCameraFacingRotation() {
        Quaternion quaternion = new Quaternion(application.getCamera().getRotation());
        TempVars vars = TempVars.get();
        Quaternion frontRotation = vars.quat1;
        frontRotation.fromAngles(-FastMath.HALF_PI, 0, FastMath.PI);
        quaternion.multLocal(frontRotation);
        vars.release();
        return quaternion;
    }

    public BoardObject getHoveredBoardObject() {
        return hoveredBoardObject;
    }

    public BoardObject getDraggedBoardObject() {
        return draggedBoardObject;
    }

    public TransformedBoardObject getInspectedBoardObject() {
        return inspectedBoardObject;
    }

    @Override
    protected void cleanup(Application app) {
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
