package com.example.oop_project;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Trieda GroupDatabase implementuje rozhranie Database a predstavuje databazu pre skupiny.
 * Implementuje metody, zabezpecujuce pracu s databazou skupin.
 */
public class GroupDatabase implements Database{


    /**
     * ArrayList uchovavajuci jednotlive zaznamy o skupinach typu String.
     */
    private ArrayList<String> groupsData;

    /**
     * Zadefinovanie instancie triedy GroupDatabase.
     */
    private static GroupDatabase instance = null;

    /**
     * Konstruktor na vytvorenie instancie triedy GroupDatabase.
     * Nacita jednotlive zaznamy z databazy a ulozi ich do premennej groupsData zadefinovanej vysie.
     *
     * @throws FileNotFoundException Vynimka v pripade, ze sa dany subor nenajde.
     */
    private GroupDatabase() throws FileNotFoundException {
        groupsData = new ArrayList<>();
        Scanner sc = new Scanner(new File("src/main/resources/com/example/oop_project/database/groups.csv"));
        sc.nextLine();
        while(sc.hasNextLine()){
            groupsData.add(sc.nextLine());
        }
    }

    /**
     * Metoda na ziskanie instancie triedy GroupDatabase - ak neexistuje, vytvori sa nova zavolanim konstruktora.
     *
     * @return Instancia triedy GroupDatabase.
     * @throws FileNotFoundException Vynimka v pripade, ze sa dany subor nenajde.
     */
    public synchronized static GroupDatabase getInstance() throws FileNotFoundException {
        if(instance==null)
            instance = new GroupDatabase();
        return instance;
    }

    /**
     * Metoda na ziskanie dat z databazy.
     *
     * @return ArrayList dat typu String.
     */
    @Override
    public ArrayList<String> getData() {
        return this.groupsData;
    }

    /**
     * Metoda na pridanie novych dat.
     * Data su pridane do premennej groupsData.
     * Je zavolana metoda update na pridanie dat do databazy.
     *
     * @param string Nove data typu String.
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @Override
    public void setData(String string) throws IOException {
        groupsData.add(string);
        update(string);
    }

    /**
     * Metoda zabezpecujuca prepis dat v databaze a v premennej groupsData.
     *
     * @param string Nove data typu String.
     * @param line Predstavuje riadok typu int, na ktorom sa budu data prepisovat.
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    public void rewrite(String string, int line) throws IOException {
        Path filePath = Paths.get("src/main/resources/com/example/oop_project/database/groups.csv");
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        lines.set(line, string);
        Files.write(filePath, lines, StandardCharsets.UTF_8);
        groupsData.set(line-1, string);
    }

    /**
     * Metoda na pridanie novych dat do databazy.
     *
     * @param string Nove data typu String.
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    private void update(String string) throws IOException {
        FileWriter fw = new FileWriter("src/main/resources/com/example/oop_project/database/groups.csv",true);
        fw.write(string+ System.lineSeparator());
        fw.close();
    }

}
