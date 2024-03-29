package com.destrostudios.cardgui.samples.tools.deckbuilder;

import com.destrostudios.cardgui.BoardObjectModel;
import lombok.Getter;

@Getter
public class DeckBuilderDeckCardModel<CardModelType extends BoardObjectModel> extends BoardObjectModel {

    private CardModelType cardModel;
    private int amount;

    public void setCardModel(CardModelType cardModel) {
        updateIfNotEquals(this.cardModel, cardModel, () -> this.cardModel = cardModel);
    }

    public void setAmount(int amount) {
        updateIfNotEquals(this.amount, amount, () -> this.amount = amount);
    }
}
