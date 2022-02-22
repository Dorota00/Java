module com.agh.microscope {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.agh.microscope to javafx.fxml;
    exports com.agh.microscope;
}