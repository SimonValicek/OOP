package com.example.oop_project;

import java.util.ArrayList;

/**
 * Trieda SoloRoute rozširuje abstraktnú triedu Route. Predstavuje cestu pre jednotlivca. Sú v nej uchované všetky potrebné informácie o danej ceste.
 * Implementuje metódy na prácu s cestou, na získanie a pridávanie informácií o ceste.
 */
public class SoloRoute extends Route{

    /**
     * Používateľ typu User, ktorý predstavuje konkrétneho používateľa z databázy skupín, ktorému daná cesta patrí.
     */
    private User owner;

    /**
     * Konštruktor na vytvorenie cesty pre jednotlivca so zadanými parametrami.
     *
     * @param id ID cesty typu String.
     * @param title Názov cesty typu String.
     * @param owner Užívateľ, ktorému cesta patrí typu User.
     */
    public SoloRoute(int id, String title, User owner) {
        super(id, title);
        this.owner=owner;
    }

    /**
     * Nastavenie nových zastávok v rámci cesty.
     *
     * @param stops ArrayList reprezentujúci zastávky typu RouteStop.
     */
    @Override
    public void setStops(ArrayList<RouteStop> stops) {
        super.setStops(stops);
    }

    /**
     * Metóda na získanie používateľa, ktorý danú cestu vlastní.
     *
     * @return Používateľ vlastniaci cestu, typu User.
     */
    public User getOwner(){
        return this.owner;
    }
}
