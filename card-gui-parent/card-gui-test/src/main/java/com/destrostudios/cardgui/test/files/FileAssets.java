package com.destrostudios.cardgui.test.files;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 *
 * @author Carl
 */
public class FileAssets {

    public static String ROOT;
    private static HashMap<String, BufferedImage> IMAGES = new HashMap<>();

    public static void readRootFile() {
        ROOT = FileManager.getFileContent("./assets.ini");
    }

    public static BufferedImage getImage(String filePath, int width, int height) {
        String key = filePath + "_" + width + "_" + height;
        return IMAGES.computeIfAbsent(key, k -> {
            BufferedImage image = getImage(filePath);
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
        });
    }

    public static BufferedImage getImage(String filePath) {
        try {
            return ImageIO.read(new File(ROOT + filePath));
        } catch (Exception ex) {
            System.err.println("Error while reading image file '" + filePath + "'.");
            ex.printStackTrace();
        }
        return null;
    }
}
