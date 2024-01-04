package com.example.imageeditor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class FilterController {
    @FXML
    private ImageView filterImageView;
    private Image originalImage;  // Store the original image

    public void setImageToFilter(javafx.scene.image.Image image) {
        // Store the original image
        originalImage = image;

        // Resize the image to the desired dimensions
        javafx.scene.image.Image resizedImage = resizeImage(image, 743, 391);

        // Set the resized image to the ImageView
        filterImageView.setImage(resizedImage);
        filterImageView.setPreserveRatio(true);
        filterImageView.setFitWidth(743);
        filterImageView.setFitHeight(391);
    }


    // Method to resize the image to the specified dimensions
    private Image resizeImage(Image image, int width, int height) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(javafx.scene.paint.Color.TRANSPARENT);
        return imageView.snapshot(params, null);
    }
    @FXML
    public void applyVividEffect() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(0.5);
        filterImageView.setEffect(colorAdjust);
    }
    @FXML
    public void applyBlackAndWhiteEffect() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1.0); // Set saturation to -1 for grayscale (black and white)
        filterImageView.setEffect(colorAdjust);
    }

    @FXML
    public void applyWarmToneEffect() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(0.1); // Shift the hue towards red-yellow range
        filterImageView.setEffect(colorAdjust);
    }

    @FXML
    public void applyCoolToneEffect() {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setHue(-0.5); // Shift the hue towards blue range
        filterImageView.setEffect(colorAdjust);
    }
    @FXML
    private void back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainfilter.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            MainFilterController mainFilterController = loader.getController();
            mainFilterController.setImage(filterImageView.getImage());

            Stage stage = (Stage) filterImageView.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("MainFilter");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void saveImage() {
        Image filteredImage = filterImageView.snapshot(null, null);
        // Set the filtered image back in filterImageView
        filterImageView.setImage(filteredImage);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainfilter.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            MainFilterController mainFilterController = loader.getController();
            mainFilterController.setImage(filterImageView.getImage());

            Stage stage = (Stage) filterImageView.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("MainFilter");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
