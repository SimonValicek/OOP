package com.example.oop_project;

/**
 * Trieda FuelStop reprezentuje zastavku, na ktorej sa da doplnit palivo pocas cesty.
 * Rozsiruje abstraktnu triedu RouteStop a implementuje metody na doplnenie hodnoty paliva skupine alebo uzivatelovi.
 */
public class FuelStop extends RouteStop {

    /**
     * Konstruktor na vytvorenie FuelStop s konkretnymi parametrami.
     *
     * @param id Id zastavky.
     * @param title Nazov zastavky.
     * @param position Pozicia zastavky - na ktorom kilometri sa v ramci cesty nachadza.
     * @param image Url obrazok zastavky.
     * @param priceRange Cenove rozpatie zastavky.
     * @param state Stat, v ktorom sa zastavka nachadza.
     * @param city Mesto, v ktorom sa zastavka nachadza.
     * @param userId Id pouzivatela, ktory zastavku pridal (vytvoril).
     * @param description Popis zastavky.
     */
    public FuelStop(int id, String title, int position, String image, PriceRange priceRange, String state,String city, String userId, String description) {
        super(id,title,position,image,priceRange,state,city,userId,description);
    }

    /**
     * Metoda reprezentujuca doplnenie paliva pre cestujucu skupinu.
     *
     * @param group Skupina, ktorej bola doplnena dana hodnota.
     */
    @Override
    public void increase(Group group) {
        group.setFuel(100);
    }

    /**
     * Metoda reprezentujuca doplnenie paliva pre cestujuceho jednotlivca.
     *
     * @param user Uzivatel, ktoremu bola doplnena dana hodnota.
     */
    @Override
    public void increase(User user) {
        user.setFuel(100);
    }
}