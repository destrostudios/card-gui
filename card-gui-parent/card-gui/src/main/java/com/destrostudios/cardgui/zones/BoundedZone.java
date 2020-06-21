package com.destrostudios.cardgui.zones;

import com.destrostudios.cardgui.Card;
import com.destrostudios.cardgui.CardZone;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

import java.util.Iterator;

/**
 *
 * @author Carl
 */
public abstract class BoundedZone extends CardZone {

    public BoundedZone(Vector3f position, Quaternion rotation) {
        this(position, rotation, new Vector3f(1, 1, 1));
    }

    public BoundedZone(Vector3f position, Quaternion rotation, Vector3f scale) {
        super(position, rotation, scale);
    }
    protected Vector3f minimumPosition;
    protected Vector3f maximumPosition;
    protected Vector3f bounds;

    @Override
    public void addCard(Card card, Vector3f position) {
        super.addCard(card, position);
        updateBounds();
    }

    @Override
    public void removeCard(Card card) {
        super.removeCard(card);
        updateBounds();
    }

    protected void updateBounds() {
        minimumPosition = null;
        maximumPosition = null;
        bounds = null;
        Iterator<Card> cardsIterator = cards.iterator();
        if (cardsIterator.hasNext()) {
            Card firstCard = cardsIterator.next();
            minimumPosition = firstCard.getZonePosition().getPosition().clone();
            maximumPosition = firstCard.getZonePosition().getPosition().clone();
            while (cardsIterator.hasNext()) {
                Card card = cardsIterator.next();
                Vector3f cardZonePosition = card.getZonePosition().getPosition();
                for (int r=0;r<2;r++) {
                    float value = cardZonePosition.get(r);
                    if (value < minimumPosition.get(r)) {
                        minimumPosition.set(r, value);
                    }
                    if (value > maximumPosition.get(r)) {
                        maximumPosition.set(r, value);
                    }
                }
            }
            bounds = maximumPosition.subtract(minimumPosition);
        }
    }
}
