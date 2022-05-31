package com.destrostudios.cardgui.samples.visualization.background;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import lombok.Getter;

public class BackgroundQuad {

    public BackgroundQuad(float width, float height) {
        geometry = new Geometry("backgroundQuad", new Quad(width, height));
        geometry.setLocalTranslation((width / -2), 0, (height / 2));
        geometry.rotate(new Quaternion().fromAngleAxis(-1 * FastMath.HALF_PI, Vector3f.UNIT_X));
    }
    @Getter
    protected Geometry geometry;
}
