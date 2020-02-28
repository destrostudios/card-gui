package com.destrostudios.cardgui.test.game;

/**
 *
 * @author Carl
 */
public class MyPlayer {

    public MyPlayer() {
        
    }
    private MyCards deck = new MyCards();
    private MyCards hand = new MyCards();
    private MyCards board = new MyCards();
    
    public void draw() {
        MyCard card = deck.removeLast();
        hand.addCard(card);
    }

    public MyCards getDeck() {
        return deck;
    }

    public MyCards getHand() {
        return hand;
    }

    public MyCards getBoard() {
        return board;
    }
}
