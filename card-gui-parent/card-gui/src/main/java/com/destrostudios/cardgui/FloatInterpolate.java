package com.destrostudios.cardgui;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.util.TempVars;

/**
 *
 * @author Carl
 */
public class FloatInterpolate {

    public static float get(float currentValue, float targetValue, float speed, float lastTimePerFrame) {
        if (currentValue != targetValue) {
            float totalDistance = (targetValue - currentValue);
            float movedDistance = (speed * lastTimePerFrame);
            if (movedDistance < Math.abs(totalDistance)) {
                return (currentValue + (Math.signum(totalDistance) * movedDistance));
            }
        }
        return targetValue;
    }

    public static Vector3f get(Vector3f currentValue, Vector3f targetValue, float speed, float lastTimePerFrame) {
        Vector3f difference = targetValue.subtract(currentValue);
        float totalDistance = difference.length();
        if (totalDistance > 0) {
            float movedDistance = (speed * lastTimePerFrame);
            if (movedDistance < totalDistance) {
                return currentValue.add(difference.normalizeLocal().multLocal(movedDistance));
            }
        }
        return targetValue;
    }
    
    public static Quaternion get(Quaternion currentValue, Quaternion targetValue, float speed, float lastTimePerFrame) {
        TempVars vars = TempVars.get();
        vars.vect4f1.set(currentValue.getX(), currentValue.getY(), currentValue.getZ(), currentValue.getW());
        vars.vect4f2.set(targetValue.getX(), targetValue.getY(), targetValue.getZ(), targetValue.getW());
        Vector4f difference = vars.vect4f2.subtract(vars.vect4f1);
        float totalDistance = difference.length();
        Quaternion newValue = targetValue;
        if (totalDistance > 0) {
            float movedDistance = (speed * lastTimePerFrame);
            if (movedDistance < totalDistance) {
                Vector4f newValueVector = vars.vect4f1.add(difference.normalizeLocal().multLocal(movedDistance));
                newValue = new Quaternion(newValueVector.getX(), newValueVector.getY(), newValueVector.getZ(), newValueVector.getW());
            }
        }
        vars.release();
        return newValue;
    }

    public static float getDistanceLikeNumber(Quaternion quaternion1, Quaternion quaternion2) {
        TempVars vars = TempVars.get();
        vars.vect4f1.set(quaternion1.getX(), quaternion1.getY(), quaternion1.getZ(), quaternion1.getW());
        vars.vect4f2.set(quaternion2.getX(), quaternion2.getY(), quaternion2.getZ(), quaternion2.getW());
        float distance = vars.vect4f1.distance(vars.vect4f2);
        vars.release();
        return distance;
    }
}
