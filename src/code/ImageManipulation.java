package code;

import image.APImage;
import image.Pixel;

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        APImage image = edgeDetection("cyberpunk2077.jpg", 20);
        image.draw();
    }

    /**
     * CHALLENGE ONE: Grayscale
     * <p>
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     * <p>
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value.
     *
     * @return
     */
    public static APImage grayScale(String pathOfFile) {
        APImage originalImage = new APImage(pathOfFile);
        APImage grayscaleImage = originalImage.clone();
        int width = grayscaleImage.getWidth();
        int height = grayscaleImage.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = grayscaleImage.getPixel(x, y);
                int average = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
                pixel.setRed(average);
                pixel.setGreen(average);
                pixel.setBlue(average);
                grayscaleImage.setPixel(x, y, pixel);
            }
        }
        return grayscaleImage;
    }

    /**
     * CHALLENGE TWO: Black and White
     * <p>
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     * <p>
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white
     *
     * @return
     */
    public static APImage blackAndWhite(String pathOfFile) {
        APImage originalImage = new APImage(pathOfFile);
        APImage blackAndWhiteImage = originalImage.clone();
        int width = blackAndWhiteImage.getWidth();
        int height = blackAndWhiteImage.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = blackAndWhiteImage.getPixel(x, y);
                int average = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
                if(average < 128){
                    pixel.setBlue(0);
                    pixel.setRed(0);
                    pixel.setGreen(0);
                }
                else{
                    pixel.setBlue(255);
                    pixel.setRed(255);
                    pixel.setGreen(255);
                }
                blackAndWhiteImage.setPixel(x, y, pixel);
            }
        }
        return blackAndWhiteImage;
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
public static APImage edgeDetection(String pathOfFile, int threshold) {
    APImage originalImage = new APImage(pathOfFile);
    APImage edgeImage = originalImage.clone();
    int width = edgeImage.getWidth();
    int height = edgeImage.getHeight();
    for (int y = 0; y < height; y++) {
        for (int x = 0; x < width; x++) {
            Pixel currentPixel = edgeImage.getPixel(x, y);
            int diffLeft = 0;
            int diffBelow = 0;
            int currentAverage = (currentPixel.getRed() + currentPixel.getGreen() + currentPixel.getBlue()) / 3;
            if (x > 0) {
                Pixel leftPixel = edgeImage.getPixel(x - 1, y);
                int leftAverage = (leftPixel.getRed() + leftPixel.getGreen() + leftPixel.getBlue()) / 3;
                diffLeft = Math.abs(currentAverage - leftAverage);
            }
            if (y > 0) {
                Pixel belowPixel = edgeImage.getPixel(x, y - 1);
                int belowAverage = (belowPixel.getRed() + belowPixel.getGreen() + belowPixel.getBlue()) / 3;
                diffBelow = Math.abs(currentAverage - belowAverage);
            }
            if (x >0 && y > 0){
                if (diffLeft > threshold || diffBelow > threshold) {
                    currentPixel.setRed(0);
                    currentPixel.setGreen(0);
                    currentPixel.setBlue(0);
                } else {
                    currentPixel.setRed(255);
                    currentPixel.setGreen(255);
                    currentPixel.setBlue(255);
                }
            }
            edgeImage.setPixel(x, y, currentPixel);
        }
    }
    return edgeImage;
}

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile) {
        APImage img = new APImage(pathToFile);
        APImage reflected = img.clone();
        Pixel curr;

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                curr = img.getPixel(x, y);
                reflected.setPixel(img.getWidth() - x - 1, y, curr);
            }
        }

        reflected.draw();
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile) {
        APImage img = new APImage(pathToFile);
        APImage rotated = new APImage(img.getHeight(), img.getWidth());
        Pixel curr;

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                curr = img.getPixel(x, y);
                rotated.setPixel(rotated.getWidth() - y - 1, x, curr);
            }
        }

        rotated.draw();
    }

}