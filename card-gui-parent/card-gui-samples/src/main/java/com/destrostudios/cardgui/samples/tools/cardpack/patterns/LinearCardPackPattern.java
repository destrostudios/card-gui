package com.destrostudios.cardgui.samples.tools.cardpack.patterns;

import com.destrostudios.cardgui.samples.tools.cardpack.CardPackPattern;
import com.jme3.math.Vector3f;

public class LinearCardPackPattern implements CardPackPattern {

    @Override
    public Vector3f getCardPosition(int cards, int index) {
        return new Vector3f(index, 0, 0);
    }
}
