package com.destrostudios.cardgui.samples.visualization;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {

    public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
        int finalWidth = width;
        int finalHeight = height;
        if (finalWidth == -1) {
            finalWidth = (int) ((((float) image.getWidth()) / image.getHeight()) * finalHeight);
        } else if (finalHeight == -1) {
            finalHeight = (int) ((((float) image.getHeight()) / image.getWidth()) * finalWidth);
        }
        if ((finalWidth != image.getWidth()) || (finalHeight != image.getHeight())) {
            BufferedImage resizedImage = new BufferedImage(finalWidth, finalHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = resizedImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics.drawImage(image, 0, 0, finalWidth, finalHeight, 0, 0, image.getWidth(), image.getHeight(), null);
            graphics.dispose();
            return resizedImage;
        }
        return image;
    }
}
