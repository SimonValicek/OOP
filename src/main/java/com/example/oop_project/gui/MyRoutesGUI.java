package com.example.oop_project.gui;

import com.example.oop_project.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Kontroler pre scenu myroutes.fxml.
 */
public class MyRoutesGUI {
    @FXML
    VBox v_box;
    @FXML
    Button return_button;

    /**
     * Inicializuje obrazovku a vykresli vsetky cesty, ktore su priradene k danemu pouzivatelovi.
     * Rozhoduje, ci je uzivatel vlastnik cesty. V pripade ze ano, povoluje tlacidlo Start, po ktoreho stlaceni sa spusta nove okno so simulaciou a v povodnom okne sa prepina na predoslou scenu.
     * V pripade ze nie, uzivatel nema moznost spustit simulaciu.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void initialize() throws IOException {
        RouteHandler rh = RouteHandler.getInstance();
        SingletonUser singletonUser = SingletonUser.getInstance();

        rh.getMine().forEach(route->{
            Pane pane = new Pane();
            Label titleLabel = new Label("Title: "+route.getTitle());
            Label roleLabel = new Label();
            Separator line = new Separator(Orientation.HORIZONTAL);
            pane.getChildren().add(titleLabel);
            pane.getChildren().add(roleLabel);
            v_box.getChildren().add(titleLabel);
            v_box.getChildren().add(roleLabel);
            if((route instanceof SoloRoute && String.valueOf(((SoloRoute)route).getOwner().getId()).equals(String.valueOf(singletonUser.getId()))) ||
                    (route instanceof GroupRoute && String.valueOf(((GroupRoute)route).getGroup().getOwner().getId()).equals(String.valueOf(singletonUser.getId())))){
                roleLabel.setText("Role: owner");
                Button startButton = new Button("Start");
                startButton.setOnAction((click)->{
                    Platform.runLater(() -> {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/src/resources/FXML/simulation.fxml"));
                            Parent root = loader.load();
                            SimulationGUI controller = loader.getController();

                            controller.setRoute(route);
                            Scene newScene = new Scene(root);
                            Stage newStage = new Stage();
                            controller.startSimulation();
                            newStage.setResizable(false);
                            newStage.setScene(newScene);
                            newStage.setOnCloseRequest(event -> {
                                event.consume();
                            });
                            newStage.show();
                            Main m = new Main();
                            m.changeScene("FXML/homepage.fxml",null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                });
                v_box.getChildren().add(startButton);
            }
            else
                roleLabel.setText("Role: passenger");
            v_box.getChildren().add(line);
        });
    }

    /**
     * Zabezpecuje pridanie zastavky (na ktorej sa prave v prislusnej scene GUI nachadzame) do cesty.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void stepBack() throws IOException {
        Main m = new Main();
        m.changeScene("FXML/homepage.fxml",null);
    }
}
