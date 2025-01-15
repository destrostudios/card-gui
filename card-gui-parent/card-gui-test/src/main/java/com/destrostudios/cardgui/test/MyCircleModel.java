package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.BoardObjectModel;
import lombok.Getter;

@Getter
public class MyCircleModel extends BoardObjectModel {

    private String name;
    private boolean faceDown;

    public void setName(String name) {
        updateIfNotEquals(this.name, name, () -> this.name = name);
    }

    public void setFaceDown(boolean faceDown) {
        updateIfNotEquals(this.faceDown, faceDown, () -> this.faceDown = faceDown);
    }
}
