package com.fulwin.util;

import com.fulwin.pojo.Commodity;
import com.fulwin.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

public class Image {

    static byte[] delimiter = new byte[] { (byte) 0x1A, (byte) 0x2B, (byte) 0x3C, (byte) 0x4D, (byte) 0x5E, (byte) 0x6F };

    public static byte[] concatenateImagesWithDelimiter(List<byte[]> images) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            for (int i = 0; i < images.size(); i++) {
                byte[] image = images.get(i);
                if (i > 0) {
                    // Insert the delimiter between images (skip for the first image)
                    outputStream.write(delimiter);
                }
                // Append the image data
                outputStream.write(image);
            }
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static List<String> splitImagesAndToBase64(byte[] concatenatedData) {
        List<byte[]> individualImages = new ArrayList<>();
        int currentIndex = 0;

        while (currentIndex < concatenatedData.length) {
            int delimiterIndex = indexOf(concatenatedData, delimiter, currentIndex);
            if (delimiterIndex != -1) {
                // Found a delimiter; extract the image data between the current index and the delimiter
                byte[] imageData = Arrays.copyOfRange(concatenatedData, currentIndex, delimiterIndex);
                individualImages.add(imageData);
                currentIndex = delimiterIndex + delimiter.length; // Move the current index past the delimiter
            } else {
                // No more delimiters found; extract the remaining data as the last image
                byte[] imageData = Arrays.copyOfRange(concatenatedData, currentIndex, concatenatedData.length);
                individualImages.add(imageData);
                break; // Exit the loop
            }
        }

        return encodeImagesToBase64(individualImages);
    }

    // Helper method to find the index of a byte array within another byte array
    public static int indexOf(byte[] source, byte[] target, int fromIndex) {
        for (int i = fromIndex; i < source.length - target.length + 1; i++) {
            boolean found = true;
            for (int j = 0; j < target.length; j++) {
                if (source[i + j] != target[j]) {
                    found = false;
                    break;
                }
            }
            if (found) {
                return i;
            }
        }
        return -1;
    }

    public static List<String> encodeImagesToBase64(List<byte[]> images) {
        List<String> base64Images = new ArrayList<>();
        for (byte[] image : images) {
            String base64 = Base64.getEncoder().encodeToString(image);
            base64Images.add(base64);
        }
        return base64Images;
    }

    public static byte[] resizeImage(byte[] imageData, int targetWidth, int targetHeight) throws IOException {
        // Convert byte array to BufferedImage
        InputStream inputStream = new ByteArrayInputStream(imageData);
        BufferedImage originalImage = ImageIO.read(inputStream);

        // Create a new BufferedImage with the desired dimensions
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

        // Resize the original image to the new dimensions
        resizedImage.createGraphics().drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);

        // Convert the resized BufferedImage back to a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", outputStream);
        return outputStream.toByteArray();
    }

}
