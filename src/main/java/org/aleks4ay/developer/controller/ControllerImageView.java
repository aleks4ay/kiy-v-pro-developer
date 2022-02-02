package org.aleks4ay.developer.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.aleks4ay.developer.model.DescriptionImage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerImageView implements Initializable {

    private List<DescriptionImage> images = new ArrayList<>();
    private int page = 0;
    private double scale = 1.0;

    @FXML private ImageView image1;
    @FXML private Label description;
    @FXML private Label pageLabel;
    @FXML private Label allPage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void updateValues() {
        image1.setImage(images.get(page).getImage());
        pageLabel.setText(String.valueOf(page + 1));
        allPage.setText("из: " + images.size());
    }


    public void setImages(List<DescriptionImage> images) {
        this.images = images;
    }

    @FXML
    private void goPrevious() {
        if (page > 0) {
            page--;
            scale = 1.0;
            updateValues();
        }
    }

    @FXML
    private void goNext() {
        if (page < images.size() - 1) {
            page++;
            scale = 1.0;
            updateValues();
        }
    }

    public void exit(ActionEvent event) {
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    public Label getDescription() {
        return description;
    }

    public void setDescription(Label description) {
        this.description = description;
    }

    public void sizeLess() {
        scale -= 0.1;
        double x = image1.getImage().getWidth();
        double y = image1.getImage().getHeight();
        image1.setFitWidth(x * scale);
        image1.setFitHeight(y * scale);
        updateValues();
    }

    public void sizeMore() {
        scale += 0.1;
        double x = image1.getImage().getWidth();
        double y = image1.getImage().getHeight();
        image1.setFitWidth(x * scale);
        image1.setFitHeight(y * scale);
        image1.setImage(images.get(page).getImage());
        updateValues();
    }
}