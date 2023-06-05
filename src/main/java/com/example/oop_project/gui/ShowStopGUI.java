package com.example.oop_project.gui;

import com.example.oop_project.Main;
import com.example.oop_project.RouteStop;
import com.example.oop_project.StopsHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Kontroler pre scenu showstops.fxml.
 */
public class ShowStopGUI {

    /**
     * ArrayList sluziaci na uchovanie zoznamu vsetkych zastavok typu RouteStop z databazy. Inicializacna hodnota null.
     */
    ArrayList<RouteStop> stops = null;

    /**
     * Pomocny index typu int.
     */
    private int currentIndex = 0;

    @FXML
    private Button go_next;
    @FXML
    private Button go_prev;
    @FXML
    private Button show_details;
    @FXML
    private Button add_to_route;
    @FXML
    private Button step_back;
    @FXML
    private ImageView stop_image;
    @FXML
    private Label title_label;
    @FXML
    private HBox h_box;
    @FXML
    private VBox v_box;
    @FXML
    private AnchorPane anchor_pane;

    /**
     * Inicializuje GUI. Nacita vsetky zastavky do pomocnej premennej a vola metodu display.
     * @throws IOException
     */
    @FXML
    private void initialize() throws IOException {
        StopsHandler sh = StopsHandler.getInstance();
        stops = sh.getAll();
        stops.forEach(stop-> System.out.println(stop.getTitle()));
        display();
    }

    /**
     * Aktualizacia indexu a volanie metody display.
     */
    @FXML
    private void goPrev() {
        if (currentIndex > 0) {
            currentIndex--;
        }
        display();
    }

    /**
     * Aktualizacia indexu a volanie metody display.
     */
    @FXML
    private void goNext() {
        if (currentIndex < stops.size() - 1) {
            currentIndex++;
        }
        display();
    }

    /**
     * Sluzi na vykreslenie prislusneho obrazku k zastavke, na zaklade indexu, na ktorom sa nachadzame.
     */
    @FXML
    private void display(){
        RouteStop current = stops.get(currentIndex);
        Image image = new Image(current.getImage());
        stop_image.setImage(image);
        title_label.setText(current.getTitle());
    }

    /**
     * Sluzi na prepnutie sceny na informacie s konkretnou zastavkou, na ktoru sme prave klikli.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void showDetails() throws IOException {
        Main m = new Main();
        RouteStop current = stops.get(currentIndex);
        System.out.println(current.getTitle());
        m.changeScene("FXML/showstopdetails.fxml",current);
    }

    /**
     * Vracia na predoslu scenu.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void stepBack() throws IOException {
        Main m = new Main();
        m.changeScene("FXML/homepage.fxml",null);
    }
}
