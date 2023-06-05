package com.example.oop_project.gui;

import com.example.oop_project.Main;
import com.example.oop_project.RouteHandler;
import com.example.oop_project.SingletonUser;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

/**
 * Kontroler pre scenu travelalone.fxml.
 */
public class TravelAloneGUI {

    @FXML
    Button return_button;
    @FXML
    Button create_route;
    @FXML
    TextField title_input;
    @FXML
    Label error_label;

    /**
     * Sluzi na vytvorenie cesty typu SoloRoute z GUI.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void createRoute() throws IOException {
        if(title_input.getText().isEmpty()){
            error_label.setText("Title is missing, please fill this field to continue!");
            return;
        }
        else {
            RouteHandler rh = RouteHandler.getInstance();
            SingletonUser singletonUser = SingletonUser.getInstance();
            rh.addToDatabase(title_input.getText(), String.valueOf(singletonUser.getId()), "1");
            Main m = new Main();
            m.changeScene("FXML/homepage.fxml", null);
        }
    }

    /**
     * Naviguje spat na predoslu scen.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void stepBack () throws IOException {
        Main m = new Main();
        m.changeScene("FXML/homepage.fxml", null);
    }

}
