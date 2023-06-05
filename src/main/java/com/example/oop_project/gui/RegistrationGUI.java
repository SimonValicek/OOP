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
 * Kontroler pre scenu registration.fxml.
 */
public class RegistrationGUI {

    @FXML
    private TextField username_field;
    @FXML
    private PasswordField password1_field;
    @FXML
    private PasswordField password2_field;
    @FXML
    private Label confirmation_error_label;
    @FXML
    private Button confirmation_button;
    @FXML
    private Button return_button;


    /**
     * Potvrdenie registracie. Volaju sa funkcie na pozadi, ktore overuju, ci registracia prebehla uspesne.
     * Po uspesnej registracii prepinana na novu scenu.
     * Pri neuspesnej registracii vypise error a caka na nove vstupy.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void confirm() throws IOException {
       UserHandler uh = UserHandler.getInstance();
        if(uh.verifyUsername(username_field.getText()))
            confirmation_error_label.setText("User with this name already exists");
        else if(!(uh.checkPassword(username_field.getText(), password1_field.getText(),password2_field.getText())))
            confirmation_error_label.setText("Wrong username or password");
        else{
            Main m = new Main();
            m.changeScene("FXML/homepage.fxml",null);
        }
    }

    /**
     * Zabezpecuje pridanie zastavky (na ktorej sa prave v prislusnej scene GUI nachadzame) do cesty.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void stepBack() throws IOException {
        Main m = new Main();
        m.changeScene("FXML/mainpage.fxml", null);
    }
}
