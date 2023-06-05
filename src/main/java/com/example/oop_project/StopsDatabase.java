package com.example.oop_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Trieda StopsDatabase implementuje rozhranie Database a predstavuje databázu pre zastávky.
 * Implementuje metódy, zabezpečujúce prácu s databázou zastávok.
 */
public class StopsDatabase implements Database {

    /**
     * ArrayList uchovávajúci jednotlivé záznamy o zastávkach typu String.
     */
    private ArrayList<String> stopsData;

    /**
     * Zadefinovanie inštancie triedy StopsDatabase.
     */
    private static StopsDatabase instance = null;

    /**
     * Konštruktor na vytvorenie inštancie triedy StopsDatabase.
     * Načíta jednotlivé záznamy z databázy a uloží ich do premennej stopsData zadefinovanej vyššie.
     *
     * @throws FileNotFoundException Výnimka v prípade, že sa daný súbor nenájde.
     */
    private StopsDatabase() throws FileNotFoundException {
        stopsData = new ArrayList<>();
        Scanner sc = new Scanner(new File("src/main/resources/com/example/oop_project/database/stops.csv"));
        sc.nextLine();
        while(sc.hasNextLine()){
            stopsData.add(sc.nextLine());
        }
    }

    /**
     * Metóda na získanie inštancie triedy StopsDatabase - ak neexistuje, vytvorí sa nová zavolaním konštruktora.
     *
     * @return Inštancia triedy StopsDatabase.
     * @throws FileNotFoundException Výnimka v prípade, že sa daný súbor nenájde.
     */
    public synchronized static StopsDatabase getInstance() throws FileNotFoundException {
        if(instance==null)
            instance = new StopsDatabase();
        return instance;
    }

    /**
     * Metóda na získanie dát z databázy.
     *
     * @return ArrayList dát typu String.
     */
    @Override
    public ArrayList<String> getData() {
        return this.stopsData;
    }

    /**
     * Metóda na pridanie nových dát.
     * Dáta sú pridané do premennej stopsData.
     * Je zavolaná metóda update na pridanie dát do databázy.
     *
     * @param string Nové dáta typu String.
     * @throws IOException Výnimka v prípade chybného I/O.
     */
    @Override
    public void setData(String string) throws IOException {
        stopsData.add(string);
        update(string);
    }

    /**
     * Metóda na pridanie nových dát do databázy.
     *
     * @param string Nové dáta typu String.
     * @throws IOException Výnimka v prípade chybného I/O.
     */
    private void update(String string) throws IOException {
        FileWriter fw = new FileWriter("src/main/resources/com/example/oop_project/database/stops.csv",true);
        fw.write(string+ System.lineSeparator());
        fw.close();
    }

}
