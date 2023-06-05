package com.example.oop_project.gui;

import com.example.oop_project.Main;
import com.example.oop_project.UserHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

/**
 * Kontroler pre scenu mainpage.fxml.
 */
public class MainpageGUI {

    @FXML
    private Button sign_in_button;
    @FXML
    private Button sign_up_button;
    @FXML
    private TextField username_field;
    @FXML
    private PasswordField password_field;
    @FXML
    private Label error_label;

    /**
     * Stlacenim tlacidla sa presunieme na sceny reprezentujuce GUI pre zaregistrovanie do aplikacie.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void switchToRegistration() throws IOException{
        Main m = new Main();
        m.changeScene("FXML/registration.fxml", null);
    }

    /**
     * Overuje zadane vstupne hodnoty. V pripade uspesneho overenia prihlasuje pouzivatela a prepinada na dalsiu scenu.
     * V opacnom pripade vypisuje error a caka na nove vstupy.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void login() throws IOException {
        UserHandler uh = UserHandler.getInstance();
        if(!uh.verifyCredentials(username_field.getText(),password_field.getText()))
            error_label.setText("Wrong username or password !");
        else{
            Main m = new Main();
            m.changeScene("FXML/homepage.fxml", null);
        }
    }
}