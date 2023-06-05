package com.example.oop_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Trieda UserDatabase implementuje rozhranie Database a predstavuje databázu pre používateľov.
 * Implementuje metódy, zabezpečujúce prácu s databázou používateľov.
 */
public class UserDatabase implements Database{

    /**
     * ArrayList uchovávajúci jednotlivé záznamy o používateľoch typu String.
     */
    private ArrayList<String> usersData;

    /**
     * Zadefinovanie inštancie triedy UserDatabase.
     */
    private static UserDatabase instance = null;

    /**
     * Konštruktor na vytvorenie inštancie triedy UserDatabase.
     * Načíta jednotlivé záznamy z databázy a uloží ich do premennej usersData zadefinovanej vyššie.
     *
     * @throws FileNotFoundException Výnimka v prípade, že sa daný súbor nenájde.
     */
    private UserDatabase() throws FileNotFoundException {
        usersData = new ArrayList<>();
        Scanner sc = new Scanner(new File("src/main/resources/com/example/oop_project/database/users.csv"));
        sc.nextLine();
        while(sc.hasNextLine()){
            usersData.add(sc.nextLine());
            }
        }

    /**
     * Metóda na získanie inštancie triedy UserDatabase - ak neexistuje, vytvorí sa nová zavolaním konštruktora.
     *
     * @return Inštancia triedy UserDatabase.
     * @throws FileNotFoundException Výnimka v prípade, že sa daný súbor nenájde.
     */
    public synchronized static UserDatabase getInstance() throws FileNotFoundException {
        if(instance==null)
            instance = new UserDatabase();
        return instance;
    }

    /**
     * Metóda na získanie dát z databázy.
     *
     * @return ArrayList dát typu String.
     */
    @Override
    public ArrayList<String> getData() {
        return this.usersData;
    }

    /**
     * Metóda na pridanie nových dát.
     * Dáta sú pridané do premennej usersData.
     * Je zavolaná metóda update na pridanie dát do databázy.
     *
     * @param string Nové dáta typu String.
     * @throws IOException Výnimka v prípade chybného I/O.
     */
    @Override
    public void setData(String string) throws IOException {
        usersData.add(string);
        update(string);
    }

    /**
     * Metóda na získanie mena používateľa na základe zadaného id.
     *
     * @param id ID typu String reprezentujúce ID používateľa v databáze.
     * @return Meno používateľa typu String. Ak dané ID v databáze neexistuje, vracia null.
     */
    public String getUsername(String id) {
        for (String line : usersData) {
            String[] str = line.split(";");
            if (id.equals(str[0]))
                return str[1];
        }
        return null;
    }

    /**
     * Metóda na pridanie nových dát do databázy.
     *
     * @param string Nové dáta typu String.
     * @throws IOException Výnimka v prípade chybného I/O.
     */
    private void update(String string) throws IOException {
        FileWriter fw = new FileWriter("src/main/resources/com/example/oop_project/database/users.csv",true);
        fw.write(string+ System.lineSeparator());
        fw.close();
    }
}
