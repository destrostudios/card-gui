package com.destrostudios.cardgui.samples.visualization;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;
import java.util.Arrays;

import com.jme3.texture.Image;
import com.jme3.texture.Image.Format;
import com.jme3.util.BufferUtils;

public class PaintableImage {

    public PaintableImage(PaintableImage paintableImage) {
        setSize(paintableImage.width, paintableImage.height);
        data = Arrays.copyOf(paintableImage.data, paintableImage.data.length);
    }

    public PaintableImage(BufferedImage image) {
        setSize(image.getWidth(), image.getHeight());
        loadImage(image);
    }

    public PaintableImage(int width, int height) {
        setSize(width, height);
        setBackground(Color.BLACK);
    }
    private int width;
    private int height;
    private byte[] data;
    private Image image;

    private void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        data = new byte[width * height * 4];
        ByteBuffer buffer = BufferUtils.createByteBuffer(data);
        image = new Image(Format.RGBA8, width, height, buffer);
    }

    public Image getImage() {
        ByteBuffer buffer = BufferUtils.createByteBuffer(data);
        image.setData(buffer);
        return image;
    }

    public void setBackground(Color color) {
        for (int i = 0; i < (width * height * 4); i+=4) {
            data[i] = (byte) color.getRed();
            data[i + 1] = (byte) color.getGreen();
            data[i + 2] = (byte) color.getBlue();
            data[i + 3] = (byte) color.getAlpha();
        }
    }
 
    public void setBackground_Alpha(int colorValue) {
        for (int i = 3; i < (width * height * 4); i += 4) {
            data[i] = (byte) colorValue;
        }
    }

    public void loadImage(BufferedImage image) {
        switch (image.getType()) {
            case BufferedImage.TYPE_4BYTE_ABGR:
                byte[] imageBuffer = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
                for (int i = 0; i < data.length; i += 4) {
                    data[i] = imageBuffer[i + 3];
                    data[i + 1] = imageBuffer[i + 2];
                    data[i + 2] = imageBuffer[i + 1];
                    data[i + 3] = imageBuffer[i];
                }
                return;
            default:
                int rgb;
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        rgb = image.getRGB(x, y);
                        setPixel(x, y, ((rgb >> 16) & 0xFF), ((rgb >> 8) & 0xFF), (rgb & 0xFF), ((rgb >> 24) & 0xFF));
                    }
                }
        }
    }

    public void paintImage(BufferedImage image, int x, int y) {
        int rgb;
        for (int imageX = 0; imageX < image.getWidth(); imageX++) {
            for (int imageY = 0; imageY < image.getHeight(); imageY++) {
                rgb = image.getRGB(imageX, imageY);
                int alpha = ((rgb >> 24) & 0xFF);
                if (alpha != 0) {
                    setPixel((x + imageX), (y + imageY), ((rgb >> 16) & 0xFF), ((rgb >> 8) & 0xFF), (rgb & 0xFF), alpha);
                }
            }
        }
    }

    public void paintImage(PaintableImage image, int x, int y, int width, int height) {
        float scaleX = (((float) image.getWidth()) / width);
        float scaleY = (((float) image.getHeight()) / height);
        for (int localX = 0; localX < width; localX++) {
            for (int localY = 0; localY < height; localY++) {
                int sourceX = (int) (localX * scaleX);
                int sourceY = (int) (localY * scaleY);
                int alpha = image.getPixel_Alpha(sourceX, sourceY);
                int destinationX = (x + localX);
                int destinationY = (y + localY);
                int red = (((getPixel_Red(destinationX, destinationY) * (255 - alpha)) + (image.getPixel_Red(sourceX, sourceY) * alpha)) / 255);
                int green = (((getPixel_Green(destinationX, destinationY) * (255 - alpha)) + (image.getPixel_Green(sourceX, sourceY) * alpha)) / 255);
                int blue = (((getPixel_Blue(destinationX, destinationY) * (255 - alpha)) + (image.getPixel_Blue(sourceX, sourceY) * alpha)) / 255);
                int destinationAlpha = getPixel_Alpha(destinationX, destinationY);
                int resultAlpha = alpha + destinationAlpha - alpha * destinationAlpha / 255;
                setPixel_Red(destinationX, destinationY, red);
                setPixel_Green(destinationX, destinationY, green);
                setPixel_Blue(destinationX, destinationY, blue);
                setPixel_Alpha(destinationX, destinationY, resultAlpha);
            }
        }
    }

    public void paintSameSizeImage(PaintableImage image) {
        for (int i = 0; i < data.length; i += 4) {
            int alpha = image.getPixel(i + 3);
            int red = (((getPixel(i) * (255 - alpha)) + (image.getPixel(i) * alpha)) / 255);
            int green = (((getPixel(i + 1) * (255 - alpha)) + (image.getPixel(i + 1) * alpha)) / 255);
            int blue = (((getPixel(i + 2) * (255 - alpha)) + (image.getPixel(i + 2) * alpha)) / 255);
            int resultAlpha = alpha + getPixel(i + 3) - alpha * getPixel(i + 3) / 255;
            setPixel(i, red);
            setPixel(i + 1, green);
            setPixel(i + 2, blue);
            setPixel(i + 3, resultAlpha);
        }
    }

    public void removeByAlphaMask(PaintableImage image) {
        for (int i = 0; i < data.length; i += 4) {
            if (image.data[i + 3] != 0) {
                data[i] = 0;
                data[i + 1] = 0;
                data[i + 2] = 0;
                data[i + 3] = 0;
            }
        }
    }

    public void flipY() {
        byte[] tmpData = new byte[4];
        int x = 0;
        int y = 0;
        for (int index1 = 0; index1 < (width * height * 2); index1 += 4) {
            int index2 = ((x + (((height - 1) - y) * width)) * 4);
            for (int r = 0; r < 4; r++) {
                tmpData[r] = data[index1 + r];
                data[index1 + r] = data[index2 + r];
                data[index2 + r] = tmpData[r];
            }
            x++;
            if (x >= width) {
                x = 0;
                y++;
            }
        }
    }

    public void setPixel(int x, int y, Color color) {
        setPixel(x, y, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public void setPixel(int x, int y, int red, int green, int blue, int alpha) {
        setPixel_Red(x, y, red);
        setPixel_Green(x, y, green);
        setPixel_Blue(x, y, blue);
        setPixel_Alpha(x, y, alpha);
    }

    public void setPixel_Red(int x, int y, int colorValue) {
        setPixel(getIndex(x, y, 0), colorValue);
    }

    public void setPixel_Green(int x, int y, int colorValue) {
        setPixel(getIndex(x, y, 1), colorValue);
    }

    public void setPixel_Blue(int x, int y, int colorValue) {
        setPixel(getIndex(x, y, 2), colorValue);
    }

    public void setPixel_Alpha(int x, int y, int colorValue) {
        setPixel(getIndex(x, y, 3), colorValue);
    }

    public void setPixel(int index, int colorValue) {
        data[index] = (byte) colorValue;
    }

    public int getPixel_Red(int x, int y) {
        return getPixel(getIndex(x, y, 0));
    }

    public int getPixel_Green(int x, int y) {
        return getPixel(getIndex(x, y, 1));
    }

    public int getPixel_Blue(int x, int y) {
        return getPixel(getIndex(x, y, 2));
    }

    public int getPixel_Alpha(int x, int y) {
        return getPixel(getIndex(x, y, 3));
    }

    public int getPixel(int index) {
        return (data[index] & 0xFF);
    }

    public int getIndex(int x, int y, int channelIndex) {
        return (((x + (y * width)) * 4) + channelIndex);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte[] getData() {
        return data;
    }
}
