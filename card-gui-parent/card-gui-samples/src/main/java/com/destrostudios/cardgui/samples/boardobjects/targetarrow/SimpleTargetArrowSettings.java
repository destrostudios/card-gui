package com.destrostudios.cardgui.samples.boardobjects.targetarrow;

import com.jme3.math.ColorRGBA;
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
    private String texturePath = "card-gui/samples/textures/target_arrow.png";
    @Builder.Default
    private ColorRGBA color = ColorRGBA.Red;

}
