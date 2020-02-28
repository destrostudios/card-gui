package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.CardZone;

/**
 *
 * @author Carl
 */
public class PlayerZones {

    public PlayerZones(CardZone deckZone, CardZone handZone, CardZone boardZone) {
        this.deckZone = deckZone;
        this.handZone = handZone;
        this.boardZone = boardZone;
    }
    private CardZone deckZone;
    private CardZone handZone;
    private CardZone boardZone;

    public CardZone getDeckZone() {
        return deckZone;
    }

    public CardZone getHandZone() {
        return handZone;
    }

    public CardZone getBoardZone() {
        return boardZone;
    }
}
