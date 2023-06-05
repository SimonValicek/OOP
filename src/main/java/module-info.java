module com.example.oop_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.oop_project to javafx.fxml;
    exports com.example.oop_project;
    exports com.example.oop_project.gui;
    opens com.example.oop_project.gui to javafx.fxml;
}