package com.example.oop_project;

/**
 * Trieda EatStop reprezentuje zastavku, na ktorej sa da obcerstvit pocas cesty.
 * Rozsiruje triedu RouteStop a implementuje metody na doplnenie hodnoty jedla a pitia skupine alebo uzivatelovi.
 */
public class EatStop extends RouteStop {

    /**
     * Konstruktor na vytvorenie EatStop s konkretnymi parametrami.
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
    public EatStop(int id, String title, int position, String image, PriceRange priceRange, String state, String city, String userId, String description) {
        super(id,title,position,image,priceRange,state,city,userId, description);
    }

    /**
     * Metoda reprezentujuca doplnenie zivin pre cestujucu skupinu (doplnenie levelu jedla a pitia).
     *
     * @param group Skupina, ktorej boli doplnene dane hodnoty.
     */
    @Override
    public void increase(Group group) {
        group.setFood(100);
        group.setDrink(100);
    }

    /**
     * Metoda reprezentujuca doplnenie zivin pre cestujuceho jednotlivca (doplnenie levelu jedla a pitia).
     *
     * @param user Uzivatel, ktoremu boli doplnene dane hodnoty.
     */
    @Override
    public void increase(User user) {
        user.setFood(100);
        user.setDrink(100);
    }

}