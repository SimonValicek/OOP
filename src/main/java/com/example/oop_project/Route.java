package com.example.oop_project;

import java.util.ArrayList;

/**
 * Abstraktna trieda reprezentujuca cestu. Uchovava v sebe informacie ako id, nazov, zoznam zastavok.
 */
public abstract class Route {

    /**
     * ID cesty typu int.
     */
    protected int id;

    /**
     * Nazov cesty typu String.
     */
    protected String title;

    /**
     * ArrayList zastavok typu RouteStop pre danu cestu.
     */
    protected ArrayList<RouteStop> stops;

    /**
     * Konstruktor na vytvorenie novej cesty na zaklade danych parametrov.
     * Konstruktor definuje novy - prazdny zoznam zastavok.
     *
     * @param id ID cesty typu int.
     * @param title Nazov cesty typu String.
     */
    public Route(int id, String title) {
        this.id= id;
        this.title=title;
        this.stops=new ArrayList<>();
    }

    /**
     * Metoda na ziskanie nazvu cesty.
     *
     * @return Nazov cesty typu String.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Metoda na ziskanie ID cesty.
     *
     * @return ID cesty typu int.
     */
    public int getId(){
        return id;
    }

    /**
     * Metoda na pridanie zastavok do cesty.
     *
     * @param stops ArrayList zastavok typu RouteStop.
     */
    public void setStops(ArrayList<RouteStop> stops) {
        this.stops = stops;
    }

    /**
     * Metoda na ziskanie zastavok pre danu cestu.
     *
     * @return ArrayList zastavok typu RouteStop danej cesty.
     */
    public ArrayList<RouteStop> getStops() {
        return stops;
    }

    /**
     * Metoda na pridanie nazvu cesty.
     *
     * @param title Novy nazov typu String.
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * Metoda na priradenie ID pre danu cestu.
     *
     * @param id ID typu int.
     */
    public void setId(int id){
        this.id = id;
    }

}
