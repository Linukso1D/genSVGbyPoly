package org.max;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Controller {

    @FXML
    private void loadImage(ActionEvent event) {
       System.out.println("LoadImage event: ");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                ((ImageView) Main.mainView.lookup("#originImage")).setImage(image);
            } catch (IOException ignored) { }
        }
    }
    @FXML
    private void exportSvg(ActionEvent event) {
        System.out.println("ExportSvg event: ");
    }
    @FXML
    private void saveData(ActionEvent event) {
        System.out.println("SaveImage event: ");

    }
    @FXML
    private void loadData(ActionEvent event) {
        System.out.println("LoadData event: ");
    }
    @FXML
    private void start(ActionEvent event) {
        System.out.println("Start event: ");
    }
}
