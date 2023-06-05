package com.example.oop_project;

import java.io.FileNotFoundException;

/**
 * Trieda RouteFactory sluzi na vytvaranie ciest typu Route.
 */
public class RouteFactory {

    /**
     * Metoda, ktora berie ako vstupny parameter pole retazcov typu String, a na zaklade nich vytvara bud instancie triedy SoloRoute alebo GroupRoute.
     *
     * @param s Pole retazcov reprezentujuce jednotlive parametre danej cesty, vratane typu cesty, ktory sa ma vytvorit.
     * @return Nova vytvorena cesta typu SoloRoute alebo GroupRoute.
     * @throws FileNotFoundException Vynimka pre pripad, ze typ cesty, ktora sa ma vytvorit je iny nez zadefinovane dva typy.
     */
    public static Route createRoute(String[] s) throws FileNotFoundException {
        int id = Integer.parseInt(s[0]);
        String title = s[1];
        StopsHandler sh = StopsHandler.getInstance();

        switch (s[2]) {
            case "1":
                UserHandler uh = UserHandler.getInstance();
                User u = uh.createUser(Integer.parseInt(s[3]));
                SoloRoute sr = new SoloRoute(id, title, u);
                sr.setStops(sh.createStops(s[4]));
                return sr;
            case "0":
                GroupHandler gh = GroupHandler.getInstance();
                Group g = gh.createGroup(Integer.parseInt(s[3]));
                GroupRoute gr = new GroupRoute(id, title,g);
                gr.setStops(sh.createStops(s[4]));
                return gr;
            default:
                throw new IllegalArgumentException("Invalid route type: " + s[1]);
        }
    }
}
