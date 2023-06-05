package com.example.oop_project.gui;

import com.example.oop_project.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Kontroler pre scenu singlegroupinfo.fxml.
 */
public class SingleGroupInfoGUI {
    @FXML
    private Text title_text;
    @FXML
    private Text owner_text;
    @FXML
    private Text desc_text;
    @FXML
    private Text members_text;
    @FXML
    private Text stops_text;
    @FXML
    private Button join_button;
    @FXML
    private Label result_label;
    @FXML
    private Button return_button;


    /**
     * Inicializuje scenu po jej nacitani.
     * Z predoslej sceny sa posle Object typu Group, prostrednictvom Main triedy, ktoreho informacie sa na aktualnej scene zobrazia.
     * Inymi slovami, na aktualnu scenu sa z predoslej sceny posiela objekt skupiny, na ktoru sme prave klikli.
     *
     * @throws FileNotFoundException Vynimka v pripade, ze sa nenasiel subor, predstavujuci databazu skupin.
     */
    @FXML
    private void initialize() throws FileNotFoundException {
        Group group = (Group) Main.getSceneParam();
        desc_text.setText(group.getId());
        title_text.setText(group.getTitle());
        owner_text.setText(group.getOwner().getUsername());
        String str = "";
        for (User passenger : group.getPassengers()) {
            str += passenger.getUsername() + ", ";
        }
        members_text.setText(str);
        SingletonUser singletonUser = SingletonUser.getInstance();
        System.out.println(singletonUser.getId());
        }

    /**
     * Sluzi na pridanie aktualne prihlaseneho pouzivatela do aktualne vyobrazenej skupiny.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void joinGroup() throws IOException {
        Group group = (Group) Main.getSceneParam();
        GroupHandler gh = GroupHandler.getInstance();
        SingletonUser singletonUser = SingletonUser.getInstance();
        Main m = new Main();
        switch(gh.addMember(group.getId(),Integer.toString(singletonUser.getId()))){
            case 0:
                result_label.setText("You can't join the group as passenger because you are the owner");
                break;
            case 1:
                result_label.setText("You can't join the group as passenger because you are already one of them");
                break;
            case 2:
                m.changeScene("FXML/joingroup.fxml", "You have been successfully added to the group "+group.getTitle() );
                break;
            case 3:
                result_label.setText("Unexpected error occurred");
                break;
        }
    }

    /**
     * Vracia na predoslu scen.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void stepBack() throws IOException {
        Main m = new Main();
        m.changeScene("FXML/joingroup.fxml", null);
    }
}
