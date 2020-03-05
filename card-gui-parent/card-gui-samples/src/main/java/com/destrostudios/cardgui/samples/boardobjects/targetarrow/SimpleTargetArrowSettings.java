package com.destrostudios.cardgui.samples.boardobjects.targetarrow;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class SimpleTargetArrowSettings {

    @Builder.Default
    private int resolution = 10;
    @Builder.Default
    private float width = 1;
    @Builder.Default
    private float arcHeight = 0.25f;
    @Builder.Default
    private String texturePath = "images/target_arrow.png";

}
