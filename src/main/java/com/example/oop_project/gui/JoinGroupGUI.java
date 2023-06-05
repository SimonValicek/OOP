package com.example.oop_project.gui;

import com.example.oop_project.GroupHandler;
import com.example.oop_project.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import java.io.IOException;

/**
 * Kontroler pre scenu joingroup.fxml.
 */
public class JoinGroupGUI {

    @FXML
    ScrollPane scr_pane;
    @FXML
    HBox h_box;
    @FXML
    Button return_button;
    @FXML
    Label success_label;

    /**
     * Inicializuje tlacidla v GUI, ktore predstavuju nazvy vsetkych skupin z databazy skupin. Kliknutim na niektore z nich, presmeruje uzivatela na novu scenu, ktora zobrazuje informacie o prislusnej (zakliknutej) skupine.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void initialize() throws IOException {
        Object sceneParam = Main.getSceneParam();
        if (sceneParam instanceof String) {
            String string = (String) sceneParam;
            if (!string.isEmpty()) {
                success_label.setText(string);
            }
        }
        GroupHandler gh = GroupHandler.getInstance();
        gh.getAll().forEach((group) -> {
            Button button = new Button();
            button.setText(group.getTitle());
            button.setId(group.getId());
            button.setStyle("-fx-background-color: #f2f2f2; -fx-text-fill: #000000;");
            button.setPrefHeight(h_box.getHeight());
            button.setOnAction((click)->{
                Main m = new Main();
                try {
                    m.changeScene("FXML/singlegroupinfo.fxml", group);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            h_box.getChildren().add(button);
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
        m.changeScene("FXML/homepage.fxml", null);
    }
}