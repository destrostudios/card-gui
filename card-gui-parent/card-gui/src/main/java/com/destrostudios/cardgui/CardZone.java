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
        this(position, rotation, new Vector3f(1, 1, 1));
    }

    public CardZone(Vector3f position, Quaternion rotation, Vector3f scale) {
        super(new BoardObjectModel());
        position().setTransformation(new ConstantButTargetedTransformation<>(position));
        rotation().setTransformation(new ConstantButTargetedTransformation<>(rotation));
        scale().setTransformation(new ConstantButTargetedTransformation<>(scale));
    }
    protected Board board;
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

    public Vector3f getCardPosition(Vector3f zonePosition) {
        return position().getCurrentValue().add(getLocalCardPosition(zonePosition));
    }

    protected Vector3f getLocalCardPosition(Vector3f zonePosition) {
        return Vector3f.ZERO;
    }

    public Quaternion getCardRotation(Vector3f zonePosition) {
        return rotation().getCurrentValue().mult(getLocalCardRotation(zonePosition));
    }

    protected Quaternion getLocalCardRotation(Vector3f zonePosition) {
        return Quaternion.IDENTITY;
    }

    public Vector3f getCardScale(Vector3f zonePosition) {
        return scale().getCurrentValue().mult(getLocalCardScale(zonePosition));
    }

    protected Vector3f getLocalCardScale(Vector3f zonePosition) {
        return Vector3f.UNIT_XYZ;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public LinkedList<Card> getCards() {
        return cards;
    }
}
