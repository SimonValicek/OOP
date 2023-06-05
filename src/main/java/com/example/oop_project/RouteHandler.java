package com.example.oop_project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Trieda RouteHandler implementuje rozhranie Handler a predstavuje nastroj na pracu s cestami.
 * Implementuje metody, zabezpecujuce pracu s objektami reprezentujucimi cesty (typ Route) a databazou pre cesty (RoutesDatabase).
 */
public class RouteHandler implements Handler{

    /**
     * Zadefinovanie inštancie triedy RouteHandler.
     */
    private static RouteHandler instance = null;

    /**
     * Konstruktor na vytvorenie inštancie triedy RouteHandler.
     */
    private RouteHandler(){
    }

    /**
     * Metoda na ziskanie inštancie typu RouteHandler - ak taka neexistuje, vytvori sa nova volanim konstruktora.
     *
     * @return Inštancia typu RouteHandler.
     */
    public synchronized static RouteHandler getInstance(){
        if(instance==null)
            instance = new RouteHandler();
        return instance;
    }

    /**
     * Metoda na pridanie noveho zaznamu do databazy ciest.
     *
     * @param args Nazov, meno a typ cesty.
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    @Override
    public void addToDatabase(String... args) throws IOException {
        String title = args[0];
        String id = args[1];
        String isSolo = args[2];
        RoutesDatabase rd = RoutesDatabase.getInstance();
        rd.setData((rd.getData().size()+1)+";"+title+";"+isSolo+";"+id+";"+"8,6,3");
    }

    /**
     * Metoda na ziskanie ID cesty na zaklade nazvu cesty.
     *
     * @param title Nazov cesty typu String.
     * @return ID cesty typu String.
     * @throws FileNotFoundException Vynimka pre pripad, ze sa nenasiel subor predstavujuci databazu ciest.
     */
    public String getIdFromTitle(String title) throws FileNotFoundException {
        RoutesDatabase rd = RoutesDatabase.getInstance();
        for (String line : rd.getData()) {
            String[] str = line.split(";");
            if (str[1].equals(title))
                return str[0];
        }
    return null;
    }

    /**
     * Metoda na pridanie zastavky do cesty.
     *
     * @param routeId ID cesty typu String, do ktorej sa ma zastavka pridat.
     * @param stopId ID zastavky typu String, ktoru sa ma do cesty pridat.
     * @return Hodnota true/false, podla toho, ci sa pridavanie podarilo uspesne vykonat.
     * @throws IOException Vynimka v pripade chybneho I/O.
     */
    public boolean addStop(String routeId, String stopId) throws IOException {
        RoutesDatabase rd =RoutesDatabase.getInstance();
        for (String line : rd.getData()) {
            String[] str = line.split(";");
            System.out.println("addstop  "+routeId+stopId);
            if (routeId.equals(str[0])){
                    String[] stops= str[4].split(",");
                    for(String stop:stops){
                        if(stop.equals(stopId))
                            return false;
                    }
                    String temp = str[0]+";"+str[1]+";"+str[2]+";"+str[3]+";"+str[4]+","+stopId;
                    rd.rewrite(temp,Integer.parseInt(routeId));
                }
            }
        return true;
        }

    /**
     * Metoda na ziskanie vsetkych ciest daneho uzivatela.
     *
     * @return ArrayList ciest typu Route pre prihlaseneho pouzivatela.
     * @throws FileNotFoundException Vynimka v pripade, ze sa nenasiel subor predstavujuci databazu ciest.
     */
    public ArrayList<Route> getMine() throws FileNotFoundException {
        ArrayList<Route> routes = getAll();
        ArrayList<Route> myRoutes = new ArrayList<>();
        SingletonUser singletonUser = SingletonUser.getInstance();
        String id = String.valueOf(singletonUser.getId());

        for (Route route : routes) {
            if ((route instanceof SoloRoute && String.valueOf(((SoloRoute)route).getOwner().getId()).equals(id)) ||
                    (route instanceof GroupRoute && String.valueOf(((GroupRoute)route).getGroup().getPassengerIds()).contains(id)) ||
                        (route instanceof GroupRoute && String.valueOf(((GroupRoute)route).getGroup().getOwner().getId()).equals(id))){
                myRoutes.add(route);
            }
        }
        return myRoutes;
    }

    /**
     * Metoda na ziskanie vsetkych zastavok.
     *
     * @return ArrayList vsetkych zastavok typu Route, ktore su ulozene v databaze.
     * @throws FileNotFoundException Vynimka v pripade, ze sa nenasiel subor predstavujuci databazu ciest.
     */
    @Override
    public ArrayList<Route> getAll() throws FileNotFoundException {
        RoutesDatabase rd = RoutesDatabase.getInstance();
        ArrayList<Route> routes = new ArrayList<>();
        rd.getData().forEach((line) -> {
            String[] s = line.split(";");
            RouteFactory rf = new RouteFactory();
            try {
                Route route = rf.createRoute(s);
                routes.add(route);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        return routes;
    }
}
