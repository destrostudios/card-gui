package com.destrostudios.cardgui;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author Carl
 */
public class JMonkeyUtil {

    public static final float FLT_EPSILON_SQUARED = (FastMath.FLT_EPSILON * FastMath.FLT_EPSILON);

    public static void setLocalRotation(Spatial spatial, Vector3f rotation) {
        Vector3f lookAtLocation = spatial.getWorldTranslation().add(rotation);
        spatial.lookAt(lookAtLocation, Vector3f.UNIT_Y);
    }
}
