package org.aleks4ay.developer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class ControllerImage implements Initializable {

    @FXML private ImageView image1;
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
    private void saveImage(ActionEvent event){
        String fileName = "";
        Set<Node> labels = ((Node) event.getSource()).getParent().lookupAll("Label");
        for (Node n : labels) {
            if (n.getId().equalsIgnoreCase("description_id")) {
                System.out.println("id=" + ((Label)n).getText());
            }
            if (n.getId().equalsIgnoreCase("file_name")) {
                System.out.println("file=" + ((Label)n).getText().replaceAll("\\\\", "/"));
                fileName = ((Label)n).getText();
            }
        }
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public void cancelImage(ActionEvent event) {
    }
}