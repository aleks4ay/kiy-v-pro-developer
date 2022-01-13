package org.aleks4ay.developer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.aleks4ay.developer.dao.ConnectionPool;
import org.aleks4ay.developer.dao.DescriptionDao;
import org.aleks4ay.developer.service.DescriptionService;
import org.aleks4ay.developer.tools.FileWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerImage implements Initializable {

    @FXML private ImageView image1;
    @FXML private Label description;
    @FXML private Label description_id;
    @FXML private Label file_name;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void getImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Картинки", "*.png", "*.jpg", "*.gif", "*.bmp", "*.bmp"),
                new FileChooser.ExtensionFilter("Файлы txt", "*.txt"),
                new FileChooser.ExtensionFilter("Все файлы", "*.*"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            file_name.setText(selectedFile.toString());
            viewImage(selectedFile);
        }
    }

    public void viewImage(File file) {
        String fileName = file.toString();
        try {
            InputStream input = new FileInputStream(fileName);
            Image image = new Image(input, 600, 400, true, true);
            image1.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveImage(){
        String id = description_id.getText();
        String fileName = file_name.getText();
        new DescriptionService(new DescriptionDao(ConnectionPool.getInstance())).createImage(fileName, id);
        FileWriter.writeTimeChange("image");
        cancelImage();
    }

    public void cancelImage() {
        UtilController utilController = UtilController.getInstance();
        Parent rootNode = file_name.getScene().getRoot();
        image1.setImage(null);
        utilController.setOrder(null);
        utilController.setDescriptionKb(null);
        utilController.setImagePaneInvisible(rootNode);
    }
}