package com.destrostudios.cardgui.test.game;

import lombok.Getter;

/**
 *
 * @author Carl
 */
@Getter
public class MyPlayer {

    private MyCards deck = new MyCards();
    private MyCards hand = new MyCards();
    private MyCards board = new MyCards();
    
    public void draw() {
        MyCard card = deck.removeLast();
        hand.addCard(card);
    }
}
