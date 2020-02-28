package com.destrostudios.cardgui;

import com.destrostudios.cardgui.transformations.ConstantButTargetedTransformation;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import java.util.LinkedList;

/**
 *
 * @author Carl
 */
public abstract class CardZone extends TransformedBoardObject {

    public CardZone(Vector3f position, Quaternion rotation) {
        position().setTransformation(new ConstantButTargetedTransformation<>(position));
        rotation().setTransformation(new ConstantButTargetedTransformation<>(rotation));
    }
    private Board board;
    protected LinkedList<Card> cards = new LinkedList<>();
    
    public void addCard(Card card, Vector3f position) {
        board.register(card);
        ZonePosition zonePosition = card.getZonePosition();
        if (zonePosition.getZone() != null) {
            zonePosition.getZone().removeCard(card);
        }
        card.getZonePosition().setZone(this);
        card.getZonePosition().setPosition(position);
        cards.add(card);
    }

    public void removeCard(Card card) {
        card.getZonePosition().setZone(null);
        card.getZonePosition().setPosition(null);
        cards.remove(card);
    }
    
    public abstract Vector3f getLocalPosition(Vector3f zonePosition);

    public void setBoard(Board board) {
        this.board = board;
    }

    public LinkedList<Card> getCards() {
        return cards;
    }
}
