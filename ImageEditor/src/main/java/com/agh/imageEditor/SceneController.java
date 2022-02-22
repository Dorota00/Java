package com.agh.microscope;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Optional;

public class SceneController {
    @FXML
    MenuBar start_menu;
    @FXML
    MenuBar image_menu;
    @FXML
    VBox start_vbox;
    @FXML
    VBox image_choose;
    @FXML
    VBox image_edit;
    @FXML
    Button choose;
    @FXML
    VBox image_vbox;
    @FXML
    ImageView imageView;

    private Stage primaryStage;
    private Stage secondaryStage;
    private Scene scene;
    public Parent root;

    int resize_x = 0;
    int resize_y = 0;
    int x = 0;
    int y = 0;
    int rotate = 0;

    double hue = 0.0;
    double saturation = 0.0;
    double brightness = 0.0;
    double contrast = 0.0;

    int width;
    int height;

    private File file;

    @FXML
    public void switchToStart(ActionEvent event) throws IOException {
        primaryStage = (Stage) start_menu.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("start.fxml"));
        scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image editor");
        primaryStage.show();
    }

    @FXML
    public void switchToStart2(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("start_2.fxml"));
        primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Image editor");
        primaryStage.show();

        openRunning();
    }

    @FXML
    public void closeRunning(ActionEvent event) {
        secondaryStage = (Stage) image_menu.getScene().getWindow();
        secondaryStage.close();
    }


    public void openRunning() throws IOException {
        secondaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("running.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        secondaryStage.setTitle("Image editor");
        secondaryStage.setScene(scene);

        double X = 600;
        double Y = 0;
        secondaryStage.setX(X);
        secondaryStage.setY(Y);

        secondaryStage.show();

    }

    public void choose_image() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open image");
        file = fileChooser.showOpenDialog(secondaryStage);

        add_image();
    }

    public void add_image() throws IOException {
        image_vbox.getChildren().clear();
        Image image = new Image(file.toURI().toString());
        width =(int)image.getWidth();
        height = (int)image.getHeight();
        imageView.setImage(image);

        Rectangle2D viewportRect = new Rectangle2D(x, y, resize_x, resize_y);
        imageView.setViewport(viewportRect);
        imageView.setRotate(rotate);
        ColorAdjust color = new ColorAdjust(hue, saturation, brightness, contrast);
        imageView.setEffect(color);

        image_vbox.getChildren().add(imageView);
    }

    public void exit()
    {
        System.out.println("Exit");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Do you want to exit the program?",
                ButtonType.YES, ButtonType.NO);

        alert.setResizable(true);
        alert.onShownProperty().addListener(e -> {
            Platform.runLater(() -> alert.setResizable(false));
        });

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES)
        {
            Platform.exit();
        }
    }

    @FXML
    public void javaFX(ActionEvent event){
        start_vbox.getChildren().clear();
        start_vbox.getChildren().add(new Label("Link to documentation:"));
        start_vbox.getChildren().add(new Hyperlink("https://docs.oracle.com/javase/8/javafx/api/toc.htm"));
    }

    @FXML
    public void about(ActionEvent event) {
        start_vbox.getChildren().clear();
        start_vbox.getChildren().add(new Label("Image editor is a Java's project created by Dorota Zub. Link to github:"));
        start_vbox.getChildren().add(new Hyperlink("https://github.com/Dorota00"));
    }

    @FXML
    public void resize(ActionEvent event) {
        image_vbox.getChildren().clear();
        image_edit.getChildren().clear();

        image_edit.getChildren().add(new Label("Resize width"));
        String w = String.valueOf(width);
        Label label_size_x = new Label(w);
        image_edit.getChildren().add(label_size_x);
        Slider slider_size_x = new Slider(1, width, width);
        image_edit.getChildren().add(slider_size_x);

        resize_x = Integer.parseInt(label_size_x.getText());

        slider_size_x.valueProperty().addListener((observable, oldValue, newValue) -> {
            label_size_x.textProperty().setValue(
                    String.valueOf(newValue.intValue()));
            resize_x = Integer.parseInt(label_size_x.getText());
            try {
                add_image();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        image_edit.getChildren().add(new Label("Resize height"));
        String h = String.valueOf(height);
        Label label_size_y = new Label(h);
        image_edit.getChildren().add(label_size_y);
        Slider slider_size_y = new Slider(1,height,height);
        image_edit.getChildren().add(slider_size_y);

        resize_y = Integer.parseInt(label_size_y.getText());

        slider_size_y.valueProperty().addListener((observable, oldValue, newValue) -> {
            label_size_y.textProperty().setValue(
                    String.valueOf(newValue.intValue()));
            resize_y = Integer.parseInt(label_size_y.getText());
            try {
                add_image();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        image_edit.getChildren().add(new Label("Location width"));
        Label label_x = new Label("0");
        image_edit.getChildren().add(label_x);
        Slider slider_x = new Slider(0,width,0);
        image_edit.getChildren().add(slider_x);

        x = Integer.parseInt(label_x.getText());

        slider_x.valueProperty().addListener((observable, oldValue, newValue) -> {
            label_x.textProperty().setValue(
                    String.valueOf(newValue.intValue()));
            x = Integer.parseInt(label_x.getText());
            try {
                add_image();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        image_edit.getChildren().add(new Label("Location height"));
        Label label_y = new Label("0");
        image_edit.getChildren().add(label_y);
        Slider slider_y = new Slider(0,imageView.getFitHeight(),0);
        image_edit.getChildren().add(slider_y);

        y = Integer.parseInt(label_y.getText());

        slider_y.valueProperty().addListener((observable, oldValue, newValue) -> {
            label_y.textProperty().setValue(
                    String.valueOf(newValue.intValue()));
            y = Integer.parseInt(label_y.getText());
            try {
                add_image();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    public void rotate(ActionEvent event) {
        image_vbox.getChildren().clear();
        image_edit.getChildren().clear();

        image_edit.getChildren().add(new Label("Rotate"));
        Label label = new Label("0");
        image_edit.getChildren().add(label);
        Slider slider = new Slider(0,360,0);
        image_edit.getChildren().add(slider);

        rotate = Integer.parseInt(label.getText());

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            label.textProperty().setValue(
                    String.valueOf(newValue.intValue()));
            rotate = Integer.parseInt(label.getText());
            try {
                add_image();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private final DecimalFormat df = new DecimalFormat("#.##");


    @FXML
    public void hue(ActionEvent event) {
        image_vbox.getChildren().clear();
        image_edit.getChildren().clear();

        image_edit.getChildren().add(new Label("Hue"));
        Label label = new Label("0");
        image_edit.getChildren().add(label);
        Slider slider = new Slider(-1.0,1.0,0);
        image_edit.getChildren().add(slider);

        hue = Integer.parseInt(label.getText());

        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            label.textProperty().setValue(
                    String.valueOf(df.format(newValue.doubleValue())));
            hue = Double.parseDouble(label.getText());
            try {
                add_image();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    public void saturation(ActionEvent event) {
        image_vbox.getChildren().clear();
        image_edit.getChildren().clear();

        image_edit.getChildren().add(new Label("Saturation"));
        Label label = new Label("0");
        image_edit.getChildren().add(label);
        Slider slider = new Slider(-1.0,1.0,0);
        image_edit.getChildren().add(slider);

        saturation = Integer.parseInt(label.getText());

        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            label.textProperty().setValue(
                    String.valueOf(df.format(newValue.doubleValue())));
            saturation = Double.parseDouble(label.getText());
            try {
                add_image();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void brightness(ActionEvent event) {
        image_vbox.getChildren().clear();
        image_edit.getChildren().clear();

        image_edit.getChildren().add(new Label("Brightness"));
        Label label = new Label("0");
        image_edit.getChildren().add(label);
        Slider slider = new Slider(-1.0,1.0,0);
        image_edit.getChildren().add(slider);

        brightness = Integer.parseInt(label.getText());

        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            label.textProperty().setValue(
                    String.valueOf(df.format(newValue.doubleValue())));
            brightness = Double.parseDouble(label.getText());
            try {
                add_image();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void contrast(ActionEvent event) {
        image_vbox.getChildren().clear();
        image_edit.getChildren().clear();

        image_edit.getChildren().add(new Label("Contrast"));
        Label label = new Label("0");
        image_edit.getChildren().add(label);
        Slider slider = new Slider(-1.0,1.0,0);
        image_edit.getChildren().add(slider);

        contrast = Integer.parseInt(label.getText());

        df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));

        slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            label.textProperty().setValue(
                    String.valueOf(df.format(newValue.doubleValue())));
            contrast = Double.parseDouble(label.getText());
            try {
                add_image();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}