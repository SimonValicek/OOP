package com.example.oop_project.gui;

import com.example.oop_project.GroupHandler;
import com.example.oop_project.Main;
import com.example.oop_project.SingletonUser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;

/**
 * Kontroler pre scenu creategroup.fxml.
 */
public class CreateGroupGUI {

    @FXML
    TextField title_input;
    @FXML
    Button create_group_button;
    @FXML
    Button return_button;

    /**
     * Vytvori novu skupinu a prida ju do databazy.
     *
     * @throws IOException Vynimka v pripade chybnego I/O.
     */
    @FXML
    private void createGroup() throws IOException {
        GroupHandler groupHandler = GroupHandler.getInstance();
        SingletonUser singletonUser = SingletonUser.getInstance();
        groupHandler.addToDatabase(title_input.getText(), String.valueOf(singletonUser.getId()));
        Main m = new Main();
        m.changeScene("FXML/homepage.fxml", null);
    }

    /**
     * Zabezpecuje pridanie zastavky (na ktorej sa prave v prislusnej scene GUI nachadzame) do cesty.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void stepBack() throws IOException {
        Main m = new Main();
        m.changeScene("FXML/homepage.fxml", null);
    }
}
