package com.destrostudios.cardgui.test.game;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Carl
 */
@Getter
public class MyCard {

    public MyCard(Color color, String name) {
        this.color = color;
        this.name = name;
    }
    public enum Color {
        NEUTRAL,
        WHITE,
        RED,
        GREEN,
        BLUE,
        BLACK
    }
    private Color color;
    private String name;
    @Setter
    private boolean damaged;
}
