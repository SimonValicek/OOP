package com.example.oop_project;

import java.io.FileNotFoundException;

/**
 * Trieda StopsFactory slúži na vytváranie zastávok typu RouteStop.
 */
public class StopsFactory {

    /**
     * Metóda, ktorá berie ako vstupný parameter pole reťazcov typu String, a na základe nich vytvára inštancie tried (EatStop, FuelStop, StayStop WildStop, FunStop, HeritageStop) - podľa zadaného parametra.
     *
     * @param s Pole reťazcov reprezentujúce jednotlivé parametre danej zastávky, vrátane typu zastávky, ktorý sa má vytvoriť.
     * @return Nová vytvorená zastávka typu (jedno z nasledovných - EatStop, FuelStop, StayStop WildStop, FunStop, HeritageStop)
     */
    public static RouteStop createStop(String[] s) {
        int id = Integer.parseInt(s[0]);
        String title = s[2];
        int position = Integer.parseInt(s[3]);
        String image = s[4];
        PriceRange priceRange = PriceRange.fromValue(s[5]);
        String state = s[6];
        String city = s[7];
        String created_by = s[8];
        String description = s[9];

        switch (s[1]) {
            case "1":
                return new EatStop(id, title, position, image, priceRange, state, city, created_by,description);
            case "2":
                return new FuelStop(id, title, position, image, priceRange, state, city, created_by,description);
            case "3":
                return new StayStop(id, title, position, image, priceRange, state, city, created_by,description);
            case "4":
                return new WildStop(id, title, position, image, priceRange, state, city, created_by,description);
            case "5":
                return new FunStop(id, title, position, image, priceRange, state, city, created_by,description);
            case "6":
                return new HeritageStop(id, title, position, image, priceRange, state, city, created_by,description);
            default:
                throw new IllegalArgumentException("Invalid stop type: " + s[1]);
        }
    }
}
