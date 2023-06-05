package com.example.oop_project.gui;

import com.example.oop_project.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;

/**
 * Kontroler pre scenu homepage.fxml.
 */
public class HomepageGUI {

    @FXML
    Button create_group;
    @FXML
    Button join_group;
    @FXML
    Button travel_alone;
    @FXML
    Button return_button;
    @FXML
    Button add_stop_button;
    @FXML
    Button show_stops_button;
    @FXML
    Button my_routes_button;

    /**
     * Presunie na scenu, na ktorej ma uzivatel moznost vytvorit novu skupinu.
     *
     * @throws IOException Vynimka v pripade chybnego I/O.
     */
    public void createGroup() throws IOException {
        Main m = new Main();
        m.changeScene("FXML/creategroup.fxml", null);
    }

    /**
     * Presunie na scenu, na ktorej ma uzivatel moznost pridat sa do skupiny.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    public void joinGroup() throws IOException {
        Main m = new Main();
        m.changeScene("FXML/joingroup.fxml", null);
    }

    /**
     * Presunie na scenu, na ktorej ma uzivatel moznost vytvorenia vlastnej cesty pre jednotlivca.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    public void travelAlone() throws IOException {
        Main m = new Main();
        m.changeScene("FXML/travelalone.fxml", null);
    }

    /**
     * Zabezpecuje pridanie zastavky (na ktorej sa prave v prislusnej scene GUI nachadzame) do cesty.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    public void stepBack() throws IOException {
        Main m = new Main();
        m.changeScene("FXML/mainpage.fxml", null);
    }

    /**
     * Presunie na scenu, na ktorej ma uzivatel moznost vytvorenia novej zastavky.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    public void addStop() throws IOException {
        Main m = new Main();
        m.changeScene("FXML/addstop.fxml", null);
    }

    /**
     * Presunie na scenu, na ktorej ma uzivatel moznost vidiet/preklikat sa cez vsetky zastavky z databazy zastavok.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    public void showStops() throws IOException {
        Main m = new Main();
        m.changeScene("FXML/showstops.fxml", null);
    }

    /**
     * Presunie na scenu, na ktorej ma uzivatel moznost vidiet vsetky cesty, ktorych sa ucastni.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    public void showMyRoutes() throws IOException {
        Main m = new Main();
        m.changeScene("FXML/myroutes.fxml", null);
    }
}
