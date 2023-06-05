package com.example.oop_project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Trieda UserHandler implementuje rozhranie Handler a predstavuje nástroj na prácu s používateľmi.
 * Implementuje metódy, zabezpečujúce prácu s objektami reprezentujúcimi používateľov (typ User) a databázou pre používateľov (UserDatabase).
 */
public class UserHandler implements Handler {

    /**
     * Zadefinovanie inštancie triedy UserHandler.
     */
    private static UserHandler instance = null;

    /**
     * Konštruktor na vytvorenie inštancie triedy UserHandler.
     */
    private UserHandler(){
    }

    /**
     * Metóda na získanie inštancie typu UserHandler - ak taká neexistuje, vytvorí sa nová volaním konštruktora.
     *
     * @return Inštancia typu UserHandler.
     */
    public synchronized static UserHandler getInstance(){
        if(instance==null)
            instance = new UserHandler();
        return instance;
    }

    /**
     * Metóda na pridanie nového záznamu do databázy používateľov.
     *
     * @param args Meno a heslo používateľa.
     * @throws IOException Výnimka v prípade chybného I/O.
     */
    @Override
    public void addToDatabase(String... args) throws IOException {
        String username = args[0];
        String password = args[1];
        SingletonUser singletonUser = SingletonUser.getInstance();
        UserDatabase db = UserDatabase.getInstance();
        db.setData((db.getData().size()+1)+";"+username+";"+password);
        singletonUser.setCredentials((db.getData().size()),username,password);
    }


    /**
     * Metóda, ktorá sa v projekte nevyužíva, nakoľko neexistuje prípad, kedy by bolo potrebné vytiahnuť všetkých užívateľov z databázy naraz.
     *
     * @return Vrati vsetky zastavky v podobe ArrayListu.
     */
    @Override
    public ArrayList<?> getAll() {
        return null;
    }

    /**
     * Metóda na vytvorenie objektu typu User, predstavujúci konkrétneho používateľa.
     *
     * @param id ID používateľa typu int.
     * @return AK sa záznam pre dané ID v databáze nachádzal, vracia objekt typu User, inak null.
     * @throws FileNotFoundException Výnimka v prípade, že sa nenašiel súbor predstavujúci databázu používateľov.
     */
    public User createUser(int id) throws FileNotFoundException {
        UserDatabase ud = UserDatabase.getInstance();
        for(String line : ud.getData()) {
            String[] str = line.split(";");
            if (str[0].equals(String.valueOf(id))) {
                User user = new User(Integer.parseInt(str[0]),str[1]);
                return user;
            }
        }
        return null;
    }

    /**
     * Metódan na vytvorenie viacerých objektov typu User.
     *
     * @param ids ID používateľov, pre ktorých chceme vytvoriť objekty typu User.
     * @return ArrayList používateľov typu User.
     * @throws FileNotFoundException Výnimka v prípade, že sa súbor predstavujúci databázu používateľov nenašiel.
     */
    public ArrayList<User> createUsers(String ids) throws FileNotFoundException {
        ArrayList<User> userList = new ArrayList<>();
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            if (id.matches("\\d+")) {
                User user = createUser(Integer.parseInt(id));
                userList.add(user);
            }
        }
        return userList;
    }

    /**
     * Metóda na kontrolu zhody hesiel pri registrácií.
     *
     * @param username Meno používateľa typu String.
     * @param p1 Heslo používateľa typu String.
     * @param p2 Kontrolné heslo používateľa typu String.
     * @return Vracia true ak registrácia prebehla úspešne, false ak sa heslá nezhodovali.
     * @throws IOException Výnimka v prípade chybného I/O.
     */
    public boolean checkPassword(String username,String p1, String p2) throws IOException {
        if(p1.equals(p2)){
            addToDatabase(username,p1);
            return true;
        }
        return false;
    }

    /**
     * Metóda na verifikáciu prihlasovacích údajov. Hľadá v databáze užívateľov, či daný záznam exisutje. Slúži na prihlasovanie sa do systému.
     *
     * @param username Meno používateľa typu String.
     * @param password Heslo používateľa typu String.
     * @return V prípade úspešného prihlásenia vracia true, inak false.
     * @throws FileNotFoundException Výnimka v prípade, že sa súbor predstavujúci databázu používateľov nenašiel.
     */
    public boolean verifyCredentials(String username, String password) throws FileNotFoundException {
        UserDatabase db = UserDatabase.getInstance();
        AtomicBoolean temp = new AtomicBoolean(false);
        db.getData().forEach((line)->{
            String[] s = line.split(";");
            if (s[1].equals(username) && s[2].equals(password)){
                temp.set(true);
                SingletonUser singletonUser = SingletonUser.getInstance();
                singletonUser.setCredentials(Integer.parseInt(s[0]), username, password);
                return;
            }
        });
        return temp.get();
    }

    /**
     * Metóda slúži na overovanie mena pri registrácii. Jej úlohou je zistiť, či používateľské meno, pod ktorým sa chce daná osoba registrovať je voľné.
     *
     * @param username Meno používateľa typu String.
     * @return Vracia true v prípade, že dané meno už v databáze existuje, false v prípade, že neexistuje.
     * @throws FileNotFoundException Výnimka v prípade, že sa súbor predstavujúci databázu používateľov nenašiel.
     */
    public boolean verifyUsername(String username) throws FileNotFoundException {
        UserDatabase db = UserDatabase.getInstance();
        AtomicBoolean temp = new AtomicBoolean(false);
        db.getData().forEach((line)->{
            String[] string = line.split(";");
            if(string[1].equals(username)){
                temp.set(true);
                return;
            }
        });
        if(temp.get())
            return true;
        return false;
    }
}