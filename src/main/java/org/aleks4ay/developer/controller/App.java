package org.aleks4ay.developer.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.aleks4ay.developer.tools.ScreenSize;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/fxml/main_pain.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("KIY-V Production Management 5.0 ");
        primaryStage.setScene(scene);
        int height = ScreenSize.getScreenHeight() - 50;
        primaryStage.setHeight(height);
        int width = ScreenSize.getScreenWidth() < 1300 ? 1280 : ScreenSize.getScreenWidth() - 200;
        primaryStage.setWidth(width);
        primaryStage.getScene().getStylesheets().add("/css/StyleForPaneOne.css");
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon.png")));
        primaryStage.show();
    }
}
