package com.example.oop_project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Trieda GroupHandler implementuje rozhranie Handler a predstavuje nastroj na pracu so skupinami.
 * Implementuje metody, zabezpecujuce pracu s objektami reprezentujucimi skupiny (typ Group) a databazou pre skupiny (GroupDatabase).
 */
public class GroupHandler implements Handler{

    /**
     * Zadefinovanie instancie triedy GroupHandler.
     */
    private static GroupHandler instance = null;

    /**
     * Konstruktor na vytvorenie instancie triedy GroupHandler.
     */
    private GroupHandler(){
    }

    /**
     * Metoda na ziskanie instancie typu GroupHandler - ak taka neexistuje, vytvori sa nova volanim konstruktora.
     *
     * @return Inštancia typu GroupHandler.
     */
    public synchronized static GroupHandler getInstance(){
        if(instance==null)
            instance = new GroupHandler();
        return instance;
    }

    /**
     * Metoda na pridanie noveho zaznamu do databazy skupin.
     *
     * @param args Nazov a meno pouzivatela.
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    public void addToDatabase(String... args) throws IOException {
        String title = args[0];
        String user = args[1];
        GroupDatabase db = GroupDatabase.getInstance();
        int groupId = (db.getData().size()+1);
        db.setData(groupId+";"+title+";"+user+";"+"none");
        RouteHandler rh = RouteHandler.getInstance();
        rh.addToDatabase(title,String.valueOf(groupId),"0");
    }

    /**
     * Metoda na pridanie clena do skupiny.
     *
     * @param groupId ID typu String reprezentujuce danu skupinu, do ktorej ma byt uzivatel priradeny.
     * @param passengerId ID typu String reprezentujuce pasaziera, ktory ma byt do danej skupiny priradeny.
     * @return Navratova hodnota int, ktora spusta akcie na frontende (v GUI, z ktoreho je volana).
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    public int addMember(String groupId, String passengerId) throws IOException {
        GroupDatabase gd = GroupDatabase.getInstance();
        for (String line : gd.getData()) {
            String[] str = line.split(";");
            if (groupId.equals(str[0])){
                if(passengerId.equals(str[2]))
                    return 0;
                else if(str[3].equals("none")){
                    String replacement = passengerId;
                    str[3] = replacement;
                    String temp = str[0]+";"+str[1]+";"+str[2]+";"+str[3];
                    gd.rewrite(temp,Integer.parseInt(groupId));
                }
                else {
                   String[] passengers= str[3].split(",");
                    for(String passenger:passengers){
                        if(passenger.equals(passengerId))
                            return 1;
                    }
                    String temp = str[0]+";"+str[1]+";"+str[2]+";"+str[3]+","+passengerId;
                    gd.rewrite(temp,Integer.parseInt(groupId));
                }
                return 2;
            }
        }
    return 3;
    }

    /**
     * Metoda na vytvorenie objektu typu Group zo zaznamu v databaze.
     *
     * @param id ID skupiny typu int.
     * @return Vracia objekt typu Group v pripade, ze sa zaznam o danej skupine (podla id) nasiel v databaze. V opacnom pripade vracia null.
     * @throws FileNotFoundException Vynimka v pripade, ze sa subor s databazou nenasiel.
     */
    public Group createGroup(int id) throws FileNotFoundException {
        GroupDatabase gd = GroupDatabase.getInstance();
        UserHandler uh = UserHandler.getInstance();
        for(String line : gd.getData()){
            String[] str = line.split(";");
            if(str[0].equals(String.valueOf(id))){
                Group group = new Group(str[0],str[1],uh.createUser(Integer.parseInt(str[2])));
                uh.createUsers(str[3]).forEach(user->{
                    group.addPassenger(user);
                });
                return group;
            }
        }
        return null;
    }

    /**
     * Pre vsetky zaznamy v databaze skupin vytvori instancie triedy Group. Vracia pole tychto objektov.
     *
     * @return Objekty typu Group, predstavujuce jednotlive skupiny ulozene v databaze.
     * @throws FileNotFoundException Vynimka v pripade, ze sa nenasiel subor s databazou skupin.
     */
    public ArrayList<Group> getAll() throws FileNotFoundException {
        GroupDatabase db = GroupDatabase.getInstance();
        ArrayList<Group> groups = new ArrayList<>();
        db.getData().forEach((line)->{
            String[] s = line.split(";");
            try {
               Group g = createGroup(Integer.parseInt(s[0]));
               groups.add(g);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        return groups;
    }
}

