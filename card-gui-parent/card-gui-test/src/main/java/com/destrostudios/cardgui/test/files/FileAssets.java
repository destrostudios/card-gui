package com.destrostudios.cardgui.test.files;

import com.destrostudios.cardgui.samples.visualization.ImageUtil;

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
        return IMAGES.computeIfAbsent(key, _ -> ImageUtil.resizeImage(getImage(filePath), width, height));
    }

    public static BufferedImage getImage(String filePath) {
        try {
            return ImageIO.read(new File(ROOT + filePath));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
