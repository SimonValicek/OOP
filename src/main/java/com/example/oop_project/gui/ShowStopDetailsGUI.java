package com.example.oop_project.gui;

import com.example.oop_project.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Kontroler pre scenu showstopdetails.fxml.
 */
public class ShowStopDetailsGUI {

    @FXML
    private Button add_to_route;
    @FXML
    private Button step_back;
    @FXML
    private ImageView stop_image;
    @FXML
    private Label stop_title;
    @FXML
    private Label location_label;
    @FXML
    private Label state_label;
    @FXML
    private Label city_label;
    @FXML
    private Label desc_label;
    @FXML
    private Label author_label;
    @FXML
    private Label notification_label;
    @FXML
    private ChoiceBox<String> choice_box;


    /**
     * Po prepnuti na danu scenu inicializuje obrazovku na vypisanie informacii o zastavke, na ktoru sme klikli na predoslej obrazovke.
     *
     * @throws FileNotFoundException Vynimka v pripade, ze sa nenasiel subor predstavujuci databazu zastavok.
     */
    @FXML
    private void initialize() throws FileNotFoundException {
        RouteStop rs = (RouteStop) Main.getSceneParam();
        Image image = new Image(rs.getImage());
        stop_image.setImage(image);
        stop_title.setText(rs.getTitle());
        location_label.setText(Integer.toString(rs.getPosition()));
        state_label.setText(rs.getState());
        city_label.setText(rs.getCity());
        desc_label.setText(rs.getDescription());
        UserDatabase ud = UserDatabase.getInstance();
        author_label.setText(ud.getUsername(rs.getUserId()));
        RouteHandler rh = RouteHandler.getInstance();
        rh.getMine().forEach((route -> {
           choice_box.getItems().add(route.getTitle());
        }));
        System.out.println(rs.getTitle()+"   "+rs.getId());
    }

    /**
     * Vracia na predoslu scenu.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void stepBack() throws IOException {
        Main m = new Main();
        m.changeScene("FXML/showstops.fxml", null);
    }

    /**
     * Zabezpecuje pridanie zastavky (na ktorej sa prave v prislusnej scene GUI nachadzame) do cesty.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void addToRoute() throws IOException {
        RouteStop rs = (RouteStop) Main.getSceneParam();
        RouteHandler rh = RouteHandler.getInstance();
        String selected = choice_box.getSelectionModel().getSelectedItem();
        String routeId = rh.getIdFromTitle(selected);
        System.out.println(rs.getTitle()+"  "+rs.getId()+"   "+routeId);
        if (routeId != null) {
            if(rh.addStop(routeId,String.valueOf(rs.getId()))){
                notification_label.setText("The stop has been added to the route!");
            }
            else{
                notification_label.setText("The stop is already in the route!");
            }
        }
        else
            notification_label.setText("You have to select a route before adding a stop to it!");
    }
}
