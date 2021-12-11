package org.aleks4ay.developer.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("/fxml/factory3.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("KIY-V Production Management 5.0 "/* + DataControl.getCurrentProfile()*/);
        primaryStage.setScene(scene);
        primaryStage.setMinHeight(700.0);
//        primaryStage.setMinWidth(1100);

//        primaryStage.getScene().getStylesheets().add("/css/StyleForPaneOne.css");
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon.png")));
//        primaryStage.getScene().getStylesheets().add("/css/StyleOne.css");
        primaryStage.show();
    }
}
