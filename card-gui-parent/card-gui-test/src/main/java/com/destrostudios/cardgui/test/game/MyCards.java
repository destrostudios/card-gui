package com.destrostudios.cardgui.test.game;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author Carl
 */
public class MyCards {

    public MyCards() {
        
    }
    private LinkedList<MyCard> cards = new LinkedList<>();
    
    public void addCard(MyCard card) {
        cards.add(card);
    }
    
    public void removeCard(MyCard card) {
        cards.remove(card);
    }

    public Iterator<MyCard> iterator() {
        return cards.iterator();
    }
    
    public MyCard removeLast() {
        return cards.removeLast();
    }
    
    public void shuffle() {
        // Whatever, it's a demo
    }
    
    public boolean contains(MyCard card) {
        return cards.contains(card);
    }
}
