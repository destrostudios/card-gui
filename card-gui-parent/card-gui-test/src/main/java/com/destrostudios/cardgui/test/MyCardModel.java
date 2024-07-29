package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.annotations.IsBoardObjectDragged;
import com.destrostudios.cardgui.annotations.IsBoardObjectInspected;
import com.destrostudios.cardgui.test.game.MyCard;
import com.jme3.math.ColorRGBA;
import lombok.Getter;

@Getter
public class MyCardModel extends BoardObjectModel {

    private MyCard.Color color;
    private String name;
    private boolean damaged;
    private ColorRGBA glowColor;
    @IsBoardObjectInspected
    private boolean inspected;
    @IsBoardObjectDragged
    private boolean dragged;

    public void setColor(MyCard.Color color) {
        updateIfNotEquals(this.color, color, () -> this.color = color);
    }

    public void setName(String name) {
        updateIfNotEquals(this.name, name, () -> this.name = name);
    }

    public void setDamaged(boolean damaged) {
        updateIfNotEquals(this.damaged, damaged, () -> this.damaged = damaged);
    }

    public void setGlowColor(ColorRGBA glowColor) {
        updateIfNotEquals(this.glowColor, glowColor, () -> this.glowColor = glowColor);
    }
}
