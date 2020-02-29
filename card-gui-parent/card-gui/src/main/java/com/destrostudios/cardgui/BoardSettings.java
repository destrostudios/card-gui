package com.destrostudios.cardgui;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class BoardSettings {

    private String inputActionPrefix = "cardgui_";
    private float draggedCardProjectionZ = 0.8f;

}
