package com.example.oop_project.gui;

import com.example.oop_project.*;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;
import java.beans.PropertyChangeEvent;

/**
 * Kontroler pre scenu simulation.fxml.
 */
public class SimulationGUI {
    @FXML
    private AnchorPane main_pane;
    @FXML
    private Label hunger_label;
    @FXML
    private Label thirst_label;
    @FXML
    private Label pleasure_label;
    @FXML
    private Label fuel_label;
    @FXML
    private Label energy_label;
    @FXML
    private Label error_label;
    @FXML
    private Label success_label;

    /**
     * Predstavuje cestu typu Route, ktora sa prave simuluje.
     */
    private Route route;

    /**
     * Predstavuje backend simulacie typu GroupRouteSimulation.
     */
    private GroupRouteSimulation groupSimulation;

    /**
     * Predstavuje backend simulacie typu SoloRouteSimulation.
     */
    private SoloRouteSimulation soloSimulation;

    /**
     * Predstavuje trasu v GUI, po ktorej sa panacik hybe v ramci simulovania.
     */
    private PathTransition pathTransition;

    /**
     * Do premennej route typu Route, ulozi novu cestu, ktora sa prave simuluje.
     *
     * @param route Cesta typu Route, ktorej simulacia prave prebieha.
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * Spusta simulaciu. Zisti typ cesty, zavola prislusnu triedu reprezentujucu backend a komunikuje s nou pomocou event listenerov.
     * Zarpvne vykresluje cestu na GUI a spusta simulaciu (pohyb panacikom) po nej.
     */
    public void startSimulation()  {
        if (route instanceof GroupRoute) {
            groupSimulation = new GroupRouteSimulation((GroupRoute) route);
            groupSimulation.addListener(this::updateLabels);
            groupSimulation.simulate() ;
        }
        else if(route instanceof SoloRoute){
            soloSimulation = new SoloRouteSimulation((SoloRoute) route);
            soloSimulation.addListener(this::updateSoloLabels);
            soloSimulation.simulate();
        }


        Path path = new Path();
        path.getElements().addAll(
                new MoveTo(776, 105),
                new LineTo(746, 149),
                new LineTo(738, 168),
                new LineTo(727, 211),
                new LineTo(721, 226),
                new LineTo(639, 236),
                new LineTo(632, 257),
                new LineTo(575, 293),
                new LineTo(555, 295),
                new LineTo(533, 307),
                new LineTo(480, 308),
                new LineTo(413, 307),
                new LineTo(397, 291),
                new LineTo(338, 298),
                new LineTo(253, 272),
                new LineTo(232, 281),
                new LineTo(201, 303),
                new LineTo(198, 280),
                new LineTo(169, 314),
                new LineTo(141, 326),
                new LineTo(116, 343),
                new LineTo(116, 343)
        );

        Image carImage = new Image("https://cdn.pixabay.com/photo/2017/01/31/20/58/american-west-2027221_960_720.png");
       // Image carImage = new Image("https://ladislavmelisko.sk/wp-content/uploads/2021/06/maestro.png");

        ImageView carImageView = new ImageView(carImage);
        carImageView.setFitWidth(40);
        carImageView.setFitHeight(20);
        main_pane.getChildren().add(carImageView);
        pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(60));
        pathTransition.setNode(carImageView);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();

    }

    /**
     * Meni hodnotu nalepiek (vypisov). Sluzi na updatovania GUI tak, aby vykreslovalo hodnoty ziskavane z backendu simulacie t.j. (jednotlive hodnoty, varovania, informacie o ukonceni cesty...)
     * Spracovava event listenery a robi potrebne operacie nad GUI.
     *
     * @param evt Event typu PropertyChangeEvent.
     */
    private void updateLabels(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if ("paused".equals(propertyName)) {
            RouteStop stop = groupSimulation.getStop();
            if ((boolean) evt.getNewValue()) {
                Platform.runLater(() -> {
                    success_label.setText("You've reached " + stop.getTitle());
                    pathTransition.pause();
                });
            } else {
                    Platform.runLater(() -> {
                        success_label.setText("");
                        pathTransition.play();
                    });
            }
        }
        else if ("error".equals(propertyName)) {
            if ((boolean) evt.getNewValue()) {
                Platform.runLater(() -> {
                    error_label.setText(groupSimulation.getErrorMessage());
                    pathTransition.pause();
                });
            } else {
                Platform.runLater(() -> {
                    error_label.setText("");
                    pathTransition.play();
                });
            }
        } else if ("finish".equals(propertyName)){
            if ((boolean) evt.getNewValue()) {
                Platform.runLater(() -> {
                    success_label.setText("You've successfully finished the route");
                    pathTransition.pause();
                });
            }}

        else {
            handlePropertyChange(evt);
        }
    }


    /**
     * Volana v pripade, ze treba updatnut vypisy hodnot.
     *
     * @param evt Event typu PropertyChangeEvent.
     */

    private void handlePropertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        double newValue = (double) evt.getNewValue();
        long roundedValue = Math.round(newValue);

        switch (propertyName) {
            case "enjoy":
                Platform.runLater(() -> pleasure_label.setText(roundedValue + "%"));
                break;
            case "drink":
                Platform.runLater(() -> thirst_label.setText((100 - roundedValue) + "%"));
                break;
            case "energy":
                Platform.runLater(() -> energy_label.setText(roundedValue + "%"));
                break;
            case "fuel":
                Platform.runLater(() -> fuel_label.setText(roundedValue + "%"));
                break;
            case "food":
                Platform.runLater(() -> hunger_label.setText((100 - roundedValue) + "%"));
                break;

        }
    }



    /////////////////////////////SOLO ROUTE SIMULATION
/**
 * V konecnom dosledku, implementovanie metody nizsie nijak nezefektivnuje beh aplikacie. Naopak, metoda nizsie mohla byt nahradena volanim metody updateLabels.
 */

    /**
     * Meni hodnotu nalepiek (vypisov). Sluzi na updatovania GUI tak, aby vykreslovalo hodnoty ziskavane z backendu simulacie t.j. (jednotlive hodnoty, varovania, informacie o ukonceni cesty...)
     * Spracovava event listenery a robi potrebne operacie nad GUI.
     *
     * @param evt Event typu PropertyChangeEvent.
     */

    private void updateSoloLabels(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if ("paused".equals(propertyName)) {
            RouteStop stop = soloSimulation.getStop();
            if ((boolean) evt.getNewValue()) {
                Platform.runLater(() -> {
                    success_label.setText("You've reached " + stop.getTitle());
                    pathTransition.pause();
                });
            } else {
                Platform.runLater(() -> {
                    success_label.setText("");
                    pathTransition.play();
                });
            }
        }
        else if ("error".equals(propertyName)) {
            if ((boolean) evt.getNewValue()) {
                Platform.runLater(() -> {
                    error_label.setText(soloSimulation.getErrorMessage());
                    pathTransition.pause();
                });
            } else {
                Platform.runLater(() -> {
                    error_label.setText("");
                    pathTransition.play();
                });
            }
        } else if ("finish".equals(propertyName)){
            if ((boolean) evt.getNewValue()) {
                Platform.runLater(() -> {
                    success_label.setText("You've successfully finished the route");
                    pathTransition.pause();
                });
            }}

        else {
            handlePropertyChange(evt);
        }
    }



}
