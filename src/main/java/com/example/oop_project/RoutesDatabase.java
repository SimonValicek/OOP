package com.example.oop_project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Trieda RoutesDatabase implementuje rozhranie Database a predstavuje databazu pre cesty.
 * Implementuje metody, zabezpecujuce pracu s databazou ciest.
 */
public class RoutesDatabase implements Database{

    /**
     * ArrayList uchovavajuci jednotlive zaznamy o cestach typu String.
     */
    private ArrayList<String> routesData;

    /**
     * Zadefinovanie instancie triedy RoutesDatabase.
     */
    private static RoutesDatabase instance = null;

    /**
     * Konstruktor na vytvorenie instancie triedy RoutesDatabase.
     * Nacita jednotlive zaznamy z databazy a ulozi ich do premennej routesData zadefinovanej vyssie.
     *
     * @throws FileNotFoundException Vynimka v pripade, ze sa dany subor nenajde.
     */
    private RoutesDatabase() throws FileNotFoundException {
        routesData = new ArrayList<>();
        Scanner sc = new Scanner(new File("src/main/resources/com/example/oop_project/database/routes.csv"));
        sc.nextLine();
        while(sc.hasNextLine()){
            routesData.add(sc.nextLine());
        }
    }

    /**
     * Metoda na ziskanie instancie triedy RoutesDatabase - ak neexistuje, vytvori sa nova zavolanim konstruktora.
     *
     * @return Inštancia triedy RoutesDatabase.
     * @throws FileNotFoundException
     */
    public synchronized static RoutesDatabase getInstance() throws FileNotFoundException {
        if(instance==null)
            instance = new RoutesDatabase();
        return instance;
    }

    /**
     * Metoda na ziskanie dat z databazy.
     *
     * @return ArrayList dat typu String.
     */
    @Override
    public ArrayList<String> getData() {
        return this.routesData;
    }

    /**
     * Metoda na pridanie novych dat.
     * Data su pridane do premennej routesData.
     * Je zavolana metoda update na pridanie dat do databazy.
     *
     * @param string Nove data typu String.
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @Override
    public void setData(String string) throws IOException {
        routesData.add(string);
        update(string);
    }

    /**
     * Metoda zabezpecujuca prepis dat v databaze a v premennej routesData.
     *
     * @param string Nove data typu String.
     * @param line Predstavuje riadok typu int, na ktorom sa budu data prepisovat.
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    public void rewrite(String string, int line) throws IOException {
        System.out.println("rewrite  "+string+"  "+line);
        Path filePath = Paths.get("src/main/resources/com/example/oop_project/database/routes.csv");
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        lines.set(line, string);
        Files.write(filePath, lines, StandardCharsets.UTF_8);
        routesData.set(line-1, string);
    }

    /**
     * Metoda na pridanie novych dat do databazy.
     *
     * @param string Nove data typu String.
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    private void update(String string) throws IOException {
        FileWriter fw = new FileWriter("src/main/resources/com/example/oop_project/database/routes.csv",true);
        fw.write(string+ System.lineSeparator());
        fw.close();
    }
}
