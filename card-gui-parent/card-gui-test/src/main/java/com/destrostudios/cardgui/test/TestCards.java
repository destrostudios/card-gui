package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.test.game.MyCard;

public class TestCards {

    private static final String[] NAMES = new String[] {
        "Aether Adept",
        "Ancient Temple",
        "Angel of Mercy",
        "Argent Squire",
        "Auchenai Soulpriest",
        "Bicycle",
        "Copy Cat",
        "Dire Wolf Alpha",
        "Fireblast",
        "Fish",
        "Garen",
        "Goldshire Footman",
        "Hornliu",
        "Injured Blademaster",
        "Leper Gnome",
        "Lightning Strike",
        "Magikarp",
        "Man-Eater Bug",
        "Mordekaiser",
        "Ookazi",
        "Pot of Greed",
        "Professor Oak",
        "Puppy",
        "Roy Mustang",
        "Shyvana",
        "Sunfury Protector",
        "Swamp",
        "Taric",
        "Thunder Crash",
        "Tuska",
        "Zombie"
    };

    public static MyCard getRandomCard() {
        return new MyCard(getRandomColor(), getRandomName());
    }

    public static MyCardModel getRandomCardModel() {
        MyCardModel cardModel = new MyCardModel();
        cardModel.setColor(getRandomColor());
        cardModel.setName(getRandomName());
        return cardModel;
    }

    private static MyCard.Color getRandomColor() {
        return getRandomElement(MyCard.Color.values());
    }

    private static String getRandomName() {
        return getRandomElement(NAMES);
    }

    private static <T> T getRandomElement(T[] array) {
        return array[(int) (Math.random() * array.length)];
    }
}
