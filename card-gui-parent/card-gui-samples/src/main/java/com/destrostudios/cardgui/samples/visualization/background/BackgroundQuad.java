package com.destrostudios.cardgui.samples.visualization.background;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.RectangleMesh;
import lombok.Getter;

public class BackgroundQuad {

    public BackgroundQuad(float width, float height) {
        geometry = new Geometry("backgroundQuad", new RectangleMesh(
            new Vector3f((width / -2), 0, (height / 2)),
            new Vector3f((width / 2), 0, (height / 2)),
            new Vector3f((width / -2), 0, (height / -2))
        ));
    }
    @Getter
    protected Geometry geometry;
}
