package com.agh.microscope;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ImageEditor extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        final Screen primaryScreen = Screen.getPrimary();
        configureAndShowStage(stage, primaryScreen);
    }

    private void configureAndShowStage(final Stage stage, final Screen screen) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("start.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Image editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

// icon link: icon8.com