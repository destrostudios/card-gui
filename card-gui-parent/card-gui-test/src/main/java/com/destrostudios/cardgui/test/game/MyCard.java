package com.destrostudios.cardgui.test.game;

/**
 *
 * @author Carl
 */
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
    private boolean damaged;

    public Color getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public boolean isDamaged() {
        return damaged;
    }
}
