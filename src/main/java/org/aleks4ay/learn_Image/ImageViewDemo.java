package org.aleks4ay.learn_Image;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.InputStream;

public class ImageViewDemo extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        String fileName = "J:\\__Temp___\\Ава.jpg";

        InputStream input = new FileInputStream(fileName);
        Image image = new Image(input, 300, 200, true, true);
        ImageView imageView = new ImageView(image);

        FlowPane root = new FlowPane();
        root.setPadding(new Insets(20));
        root.getChildren().add(imageView);

        Scene scene = new Scene(root, 400, 200);

        primaryStage.setTitle("JavaFX ImageView (o7planning.org)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

