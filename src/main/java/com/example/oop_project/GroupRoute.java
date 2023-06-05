package com.example.oop_project;

import java.util.ArrayList;

/**
 * Trieda GroupRoute rozsiruje abstraktnu triedu Route. Predstavuje cestu pre skupinu uzivatelov. Su v nej uchovane vsetky potrebne informacie o danej ceste.
 * Implementuje metody na pracu s cestou, na ziskanie a pridavanie informacii o ceste.
 */
public class GroupRoute extends Route{

    /**
     * Skupina typu Group, ktora predstavuje konkretnu skupiny z databazy skupin, ktorej dana cesta patri.
     */
    private Group group;

    /**
     * Konstruktor na vytvorenie cesty pre skupinu so zadanymi parametrami.
     *
     * @param id ID cesty typu String.
     * @param title Nazov cesty typu String.
     * @param group Skupina, ktorej cesta patri, typu Group.
     */
    public GroupRoute(int id, String title,  Group group) {
        super(id, title);
        this.group = group;
    }

    /**
     * Nastavenie novych zastavok v ramci cesty.
     *
     * @param stops ArrayList reprezentujuci zastavky typu RouteStop.
     */
    @Override
    public void setStops(ArrayList<RouteStop> stops) {
        super.setStops(stops);
    }

    /**
     * Metoda na ziskanie skupiny, ktora danu cestu vlastni.
     *
     * @return Skupina vlastniaca cestu, typu Group.
     */
    public Group getGroup() {
        return this.group;
    }
}
