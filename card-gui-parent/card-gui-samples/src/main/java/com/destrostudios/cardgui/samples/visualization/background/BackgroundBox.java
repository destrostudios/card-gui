package com.destrostudios.cardgui.samples.visualization.background;

import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import lombok.Getter;

public class BackgroundBox {

    public BackgroundBox(float width, float height, float depth) {
        geometry = new Geometry("backgroundBox", new Box(width, height, depth));
    }
    @Getter
    protected Geometry geometry;
}
