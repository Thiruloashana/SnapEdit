package com.example.imageeditor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainFilterController {
    @FXML
    private ImageView imageView1;
    private Image croppedImage;
    private javafx.geometry.Rectangle2D croppedViewport;

    public void setImage(Image image) {
        // Resize the image to the desired dimensions
        Image resizedImage = resizeImage(image, 743, 391);

        // Set the resized image to the ImageView
        imageView1.setImage(resizedImage);
        imageView1.setPreserveRatio(true);
        imageView1.setFitWidth(743);
        imageView1.setFitHeight(391);
    }

    // Method to resize the image to the specified dimensions
    private Image resizeImage(Image image, int width, int height) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(javafx.scene.paint.Color.TRANSPARENT);
        WritableImage resizedImage = new WritableImage(width, height);
        return imageView.snapshot(params, resizedImage);
    }
    // Store the cropped image and its viewport


    public void setImageAndViewport(Image image, javafx.geometry.Rectangle2D viewport) {
        croppedImage = image;
        croppedViewport = viewport;

        // Resize the image to the desired dimensions and set the viewport
        Image resizedImage = resizeImage(croppedImage, (int) croppedViewport.getWidth(), (int) croppedViewport.getHeight());
        imageView1.setImage(resizedImage);
        imageView1.setViewport(viewport);
    }

    @FXML
    private void cropImage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Crop.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            CropController cropController = loader.getController();
            cropController.setImageToCrop(imageView1.getImage());

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Crop");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void filter(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("filter.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            FilterController filterController = loader.getController();
            filterController.setImageToFilter(imageView1.getImage());

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Filter");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void adjust(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Adjust.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            AdjustController adjustController = loader.getController();
            adjustController.setImageToAdjust(imageView1.getImage());


            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Adjust");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void saveImage() {
        Image image = imageView1.getImage();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Image", "*.png"));

        File file = fileChooser.showSaveDialog(getWindow());

        if (file != null) {
            try {
                int width = (int) image.getWidth();
                int height = (int) image.getHeight();

                WritableImage writableImage = new WritableImage(width, height);
                imageView1.snapshot(null, writableImage);
                BufferedImage bufferedImage = imageToBufferedImage(writableImage);

                ImageIO.write(bufferedImage, "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Window getWindow() {
        // You can modify this method to get the appropriate window if needed.
        // For example, if your FXML controller is associated with a specific stage, you can return that stage.
        return imageView1.getScene().getWindow();
    }

    private BufferedImage imageToBufferedImage(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = image.getPixelReader().getArgb(x, y);
                bufferedImage.setRGB(x, y, argb);
            }
        }

        return bufferedImage;
    }


    @FXML
    private void back() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Mainpage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            Stage stage = (Stage) imageView1.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Mainpage");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    // You can apply filters to 'initialImage' and update 'filteredImageView'
}