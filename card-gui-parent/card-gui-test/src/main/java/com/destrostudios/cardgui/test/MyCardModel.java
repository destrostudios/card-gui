package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.BoardObjectModel;
import com.destrostudios.cardgui.annotations.IsBoardObjectDragged;
import com.destrostudios.cardgui.annotations.IsBoardObjectInspected;
import com.destrostudios.cardgui.test.game.MyCard;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(exclude = {"isInspected", "isDragged"})
public class MyCardModel extends BoardObjectModel {

    private MyCard.Color color;
    private String name;
    private boolean isDamaged;
    @IsBoardObjectInspected
    private boolean isInspected;
    @IsBoardObjectDragged
    private boolean isDragged;

    public void setColor(MyCard.Color color) {
        updateIfNotEquals(this.color, color, () -> this.color = color);
    }

    public MyCard.Color getColor() {
        return color;
    }

    public void setName(String name) {
        updateIfNotEquals(this.name, name, () -> this.name = name);
    }

    public String getName() {
        return name;
    }

    public void setDamaged(boolean isDamaged) {
        updateIfNotEquals(this.isDamaged, isDamaged, () -> this.isDamaged = isDamaged);
    }

    public boolean isDamaged() {
        return isDamaged;
    }

    public boolean isInspected() {
        return isInspected;
    }

    public boolean isDragged() {
        return isDragged;
    }
}
