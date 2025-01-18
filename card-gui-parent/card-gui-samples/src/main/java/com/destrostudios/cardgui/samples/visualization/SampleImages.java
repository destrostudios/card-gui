package com.destrostudios.cardgui.samples.visualization;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SampleImages {

    public static BufferedImage loadSampleTexture(String name, int width, int height) {
        return ImageUtil.resizeImage(loadSampleTexture(name), width, height);
    }

    public static BufferedImage loadSampleTexture(String name) {
        try {
            return ImageIO.read(SampleImages.class.getResourceAsStream("/card-gui/samples/textures/" + name + ".png"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
