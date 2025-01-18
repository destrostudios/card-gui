package com.destrostudios.cardgui.test;

import com.destrostudios.cardgui.CardZone;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Carl
 */
@AllArgsConstructor
@Getter
public class PlayerZones {
    private CardZone deckZone;
    private CardZone handZone;
    private CardZone boardZone;
}
