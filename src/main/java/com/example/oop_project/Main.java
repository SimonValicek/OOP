package com.example.oop_project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.util.Objects;
import java.io.IOException;


/**
 * Trieda Main rozsiruje triedu javafx.application.Application a sluzi ako vstupny bod pred JavaFX aplikaciu,
 * Nastavuje uvodnu scenu (Scene), manazuje scenove parametre a osetruje zmeny v ramci scen.
 */
public class Main extends Application {

    /**
     * Staticky objekt typu Object.
     * Sluzi na ukladanie parametrov pre scenu.
     */
    private static Object sceneParam;

    /**
     * Staticky objekt typu Stage.
     * Uchovava odkaz na hlavnu stranku JavaFX aplikacie (Stage).
     */
    private static Stage stg;

    /**
     * Inicializuje uvodnu GUI obrazovku aplikacie "mainpage.fxml".
     * Prekonava metodu start triedy javafx.application.Application.
     *
     * @param stage Objekt typu Stage.
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        stage.setResizable(false);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/src/resources/FXML/mainpage.fxml"), "Resource not found: FXML/mainpage.fxml"));

        stage.setTitle("Route 66 Trip Planner");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    /**
     * Metoda na ulozenie objektu typu Object do statickej premennej sceneParam.
     *
     * @param param Objekt, typu Object, ktory bude ulozeny do sceneParam.
     */
    public static void setSceneParam(Object param) {
        sceneParam = param;
    }

    /**
     * Metoda na ziskanie objektu typu Object, ulozeneho v premennej sceneParam.
     *
     * @return Objekt typu Object.
     */
    public static Object getSceneParam() {
        return sceneParam;
    }

    /**
     * Metoda na prepnutie sceny v ramci GUI.
     *
     * @param fxml Nova scena, na ktoru sa ma prepnut.
     * @param data Data, ktore chceme poslat na druhu scenu.
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    public void changeScene(String fxml, Object data) throws IOException{
        if(data!=null){
            setSceneParam(data);
            System.out.println(data.getClass());
        }
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        stg.getScene().setRoot(pane);
    }

    /**
     * Hlavna metoda, spustajuca JavaFX aplikaciu.
     *
     * @param args Pole argumentov typu String.
     */
    public static void main(String[] args) {
        launch(args);
    }
}