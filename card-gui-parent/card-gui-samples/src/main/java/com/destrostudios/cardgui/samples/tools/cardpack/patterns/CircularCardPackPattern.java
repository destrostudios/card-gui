package com.destrostudios.cardgui.samples.tools.cardpack.patterns;

import com.destrostudios.cardgui.samples.tools.cardpack.CardPackPattern;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

public class CircularCardPackPattern implements CardPackPattern {

    @Override
    public Vector3f getCardPosition(int cards, int index) {
        float radians = (((float) index) / cards) * FastMath.TWO_PI;
        float x = FastMath.sin(radians);
        float z = FastMath.cos(radians + FastMath.PI);
        return new Vector3f(x, 0, z);
    }
}
