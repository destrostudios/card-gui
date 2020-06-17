package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.BoardObjectModel;
import com.jme3.math.ColorRGBA;

public class MyColoredSphereModel extends BoardObjectModel {

    private ColorRGBA colorRGBA;

    public void setColorRGBA(ColorRGBA colorRGBA) {
        updateIfNotEquals(this.colorRGBA, colorRGBA, () -> this.colorRGBA = colorRGBA);
    }

    public ColorRGBA getColorRGBA() {
        return colorRGBA;
    }
}
