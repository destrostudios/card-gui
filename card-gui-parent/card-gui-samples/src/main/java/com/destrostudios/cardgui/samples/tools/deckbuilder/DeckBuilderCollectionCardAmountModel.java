package com.destrostudios.cardgui.samples.tools.deckbuilder;

import com.destrostudios.cardgui.BoardObjectModel;
import lombok.Getter;

@Getter
public class DeckBuilderCollectionCardAmountModel extends BoardObjectModel {

    private int x;
    private int y;
    private Integer maximumAmountDeck;
    private int amountCollection;
    private int amountDeck;

    public void setX(int x) {
        updateIfNotEquals(this.x, x, () -> this.x = x);
    }

    public void setY(int y) {
        updateIfNotEquals(this.y, y, () -> this.y = y);
    }

    public void setMaximumAmountDeck(Integer maximumAmountDeck) {
        updateIfNotEquals(this.maximumAmountDeck, maximumAmountDeck, () -> this.maximumAmountDeck = maximumAmountDeck);
    }

    public void setAmountCollection(int amountCollection) {
        updateIfNotEquals(this.amountCollection, amountCollection, () -> this.amountCollection = amountCollection);
    }

    public void setAmountDeck(int amountDeck) {
        updateIfNotEquals(this.amountDeck, amountDeck, () -> this.amountDeck = amountDeck);
    }
}
