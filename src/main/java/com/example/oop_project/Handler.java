package com.example.oop_project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Rozhranie Handler reprezentuje nastroj na obsluhu databazy. Implementuje metody na pridavanie do databazy a metodu na ziskanie vsetkych prvkov z databazy.
 */
public interface Handler {

    /**
     * Metoda na pridavanie udajov do databazy.
     *
     * @param args Informacie o udajoch, ktore sa maju pridat do databazy.
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    void addToDatabase(String... args) throws IOException;

    /**
     * Metoda na ziskanie vsetkych udajov z danej databazy.
     *
     * @return ArrayList objektov, predstavujucich jednotlive zaznamy z databazy.
     * @throws FileNotFoundException Vynimka v pripade, ze sa subor s databazou nenasiel.
     */
    ArrayList<?> getAll() throws FileNotFoundException;
}
