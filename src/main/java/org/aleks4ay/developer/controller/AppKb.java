package org.aleks4ay.developer.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.aleks4ay.developer.tools.ScreenSize;

public class AppKb extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/fxml/kb.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("KIY-V Production Management 5.0 ");
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(700.0);
        int width = ScreenSize.getScreenWidth() < 1300 ? 1280 : 1380;
        primaryStage.setWidth(width);
        primaryStage.getScene().getStylesheets().add("/css/StyleForPaneOne.css");
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon.png")));
        primaryStage.show();
    }
}
