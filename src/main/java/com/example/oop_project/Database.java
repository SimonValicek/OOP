package com.example.oop_project;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Rozhranie Database reprezentuje databazu a implementuje jej dve hlavne metody, spolocne pre vsetky databazy
 */
public interface Database {

    /**
     * Vracia data z databazy
     *
     * @return ArrayList reprezentujuci data databazy
     */
    ArrayList<String> getData();

    /**
     * Uklada data do databazy
     *
     * @param string Data na pridanie do databazy
     * @throws IOException Pre pripad, ze sa vyskytne I/O error pri pridavani dat do databazy
     */
    void setData(String string) throws IOException;
}