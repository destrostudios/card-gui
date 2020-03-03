package com.destrostudios.cardgui.samples.animations;

import com.destrostudios.cardgui.Animation;
import com.destrostudios.cardgui.TransformedBoardObject;
import com.destrostudios.cardgui.animations.*;
import com.destrostudios.cardgui.transformations.*;
import com.destrostudios.cardgui.transformations.speeds.*;
import com.jme3.app.Application;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import com.jme3.util.TempVars;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ShuffleAnimation extends StagedAnimation {

    public ShuffleAnimation(Collection<? extends TransformedBoardObject> transformedBoardObjects, Application application) {
        this.stages = new Animation[] {
            new FixedTransformAnimation<SimpleTargetPositionTransformation3f, SimpleTargetRotationTransformation>(transformedBoardObjects, true) {

                @Override
                protected SimpleTargetPositionTransformation3f createPositionTransform() {
                    return new SimpleTargetPositionTransformation3f(new Vector3f(), new TimeBasedPositionTransformationSpeed3f(1));
                }

                @Override
                protected void updatePositionTransform(int index, TransformedBoardObject transformedBoardObject, SimpleTargetPositionTransformation3f positionTransformation) {
                    positionTransformation.setTargetValue(getTargetPosition(), false);
                }

                @Override
                protected SimpleTargetRotationTransformation createRotationTransform() {
                    return new SimpleTargetRotationTransformation(new Quaternion(), new TimeBasedRotationTransformationSpeed(1));
                }

                @Override
                protected void updateRotationTransform(int index, TransformedBoardObject transformedBoardObject, SimpleTargetRotationTransformation rotationTransformation) {
                    // Set rotation relative to camera
                    Quaternion targetRotation = application.getCamera().getRotation().clone();
                    TempVars vars = TempVars.get();
                    Quaternion faceToCamera = vars.quat1;
                    faceToCamera.fromAngles(FastMath.HALF_PI, -FastMath.HALF_PI, 0);
                    targetRotation.multLocal(faceToCamera);
                    vars.release();

                    //rotationTransformation.setTargetValue(targetRotation, false);
                }
            },
            new FixedTransformAnimation<SimpleTargetPositionTransformation3f, SimpleTargetRotationTransformation>(transformedBoardObjects, true) {

                @Override
                protected SimpleTargetPositionTransformation3f createPositionTransform() {
                    return new SimpleTargetPositionTransformation3f(new Vector3f(), new TimeBasedPositionTransformationSpeed3f(0.5f));
                }

                @Override
                protected void updatePositionTransform(int index, TransformedBoardObject transformedBoardObject, SimpleTargetPositionTransformation3f positionTransformation) {
                    Vector3f targetPosition = getTargetPosition();
                    float maximumX = 0.5f;
                    float cardsPerHalf = (transformedBoardObjects.size() / 2);
                    float x = ((randomOrder.get(index) - cardsPerHalf) * (maximumX / cardsPerHalf));
                    targetPosition.addLocal(x, 0, 0);
                    positionTransformation.setTargetValue(targetPosition, false);
                }

                @Override
                protected SimpleTargetRotationTransformation createRotationTransform() {
                    return null;
                }

                @Override
                protected void updateRotationTransform(int index, TransformedBoardObject transformedBoardObject, SimpleTargetRotationTransformation rotationTransformation) {

                }
            },
            new ResetFixedTransformAnimation(transformedBoardObjects)
        };
        this.application = application;
        randomOrder = getRandomOrder(transformedBoardObjects.size());
    }
    private Application application;
    private ArrayList<Integer> randomOrder;

    private Vector3f getTargetPosition() {
        AppSettings settings = application.getContext().getSettings();
        Vector3f origin = application.getCamera().getWorldCoordinates(new Vector2f((settings.getWidth() / 2), (settings.getHeight() / 2)), 0.0f);
        Vector3f direction = application.getCamera().getWorldCoordinates(new Vector2f((settings.getWidth() / 2), (settings.getHeight() / 2)), 0.3f).subtract(origin).normalizeLocal();
        return origin.addLocal(direction.multLocal(2));
    }

    private static ArrayList<Integer> getRandomOrder(int size) {
        ArrayList<Integer> order = new ArrayList<>(size);
        for (int i=0;i<size;i++) {
            order.add(i);
        }
        Collections.shuffle(order);
        return order;
    }
}