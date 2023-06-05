package com.example.oop_project.gui;

import com.example.oop_project.Main;
import com.example.oop_project.StopsHandler;
import com.example.oop_project.SingletonUser;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

/**
 * Kontroler pre scenu addstop.fxml.
 */
public class AddStopGUI {
    @FXML
    ToggleGroup price;
    @FXML
    ToggleGroup type;
    @FXML
    TextField title_input;
    @FXML
    TextField position_input;
    @FXML
    TextField image_input;
    @FXML
    TextField state_input;
    @FXML
    TextField city_input;
    @FXML
    TextField desc_input;
    @FXML
    Button return_button;
    @FXML
    Button submit_button;
    @FXML
    Label error_label;
    @FXML
    RadioButton eat_radio_button;
    @FXML
    RadioButton fuel_radio_button;
    @FXML
    RadioButton stay_radio_button;
    @FXML
    RadioButton wild_radio_button;
    @FXML
    RadioButton fun_radio_button;
    @FXML
    RadioButton heritage_radio_button;
    @FXML
    RadioButton budget_radio_button;
    @FXML
    RadioButton midrange_radio_button;
    @FXML
    RadioButton luxury_radio_button;

    /**
     * Zabezpecuje pridanie zastavky (na ktorej sa prave v prislusnej scene GUI nachadzame) do cesty.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void stepBack() throws IOException {
        Main m = new Main();
        m.changeScene("FXML/homepage.fxml",null);
    }

    /**
     * Zabezpecuje vytvorenie novej zastavky. Skontroluje vsetky vstupy.
     * V pripade, ze sa na vstupoch objavili chyby, vypise chybove hlasenia a caka na nove vstupy.
     * V opacnom pripade pridava zastavku do databazy.
     *
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @FXML
    private void submit() throws IOException {
        if(title_input.getText().isEmpty()){
            error_label.setText("Title is missing, please fill this field to continue!");
            return;
        }
        else if(position_input.getText().isEmpty()) {
            error_label.setText("Position is missing, please fill this field to continue!");
            try {
                Integer.parseInt(position_input.getText());
            } catch (NumberFormatException e) {
                error_label.setText("Make sure the position is number");
            }
        }
        else if(state_input.getText().isEmpty())
            error_label.setText("State is missing, please fill this field to continue!");
        else if(city_input.getText().isEmpty())
            error_label.setText("City is missing, please fill this field to continue!");
        else if(image_input.getText().isEmpty())
            error_label.setText("Image is missing, please fill this field to continue!");
        else if(desc_input.getText().isEmpty())
            error_label.setText("Description is missing, please fill this field to continue!");
        else {
            RadioButton selectedRadioButton1 = (RadioButton) type.getSelectedToggle();
            if(selectedRadioButton1 == null) {
                error_label.setText("Please select a type");
                return;
            }
            String type = selectedRadioButton1.getText();

            RadioButton selectedRadioButton2 = (RadioButton) price.getSelectedToggle();
            if(selectedRadioButton2 == null){
                error_label.setText("Please select a price range");
                return;
            }
            String price = selectedRadioButton2.getText();

            StopsHandler sh = StopsHandler.getInstance();
            SingletonUser singletonUser = SingletonUser.getInstance();
            sh.addToDatabase(type, title_input.getText(), position_input.getText(), image_input.getText(),price, state_input.getText(), city_input.getText(), Integer.toString(singletonUser.getId()),desc_input.getText());
        }
    }
}
