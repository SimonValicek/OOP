package com.example.oop_project;

/**
 * Trieda StayStop reprezentuje zastávku, na ktorej sa dá doplniť energia počas cesty.
 * Rozširuje abstraktnú triedu RouteStop a implementuje metódy na doplnenie energie skupine alebo užívateľovi.
 */
public class StayStop extends RouteStop {

    /**
     * Konštruktor na vytvorenie StayStop s konkrétnymi parametrami.
     *
     * @param id Id zastávky.
     * @param title Názov zastávky.
     * @param position Pozícia zastávky - na ktorom kilometri sa v rámci cesty nachádza.
     * @param image Url obrázok zastávky.
     * @param priceRange Cenové rozpätie zastávky.
     * @param state Štát, v ktorom sa zastávka nachádza.
     * @param city Mesto, v ktorom sa zastávka nachádza.
     * @param userId Id používateľa, ktorý zastávku pridal (vytvoril).
     * @param description Popis zastávky.
     */
    public StayStop(int id, String title, int position, String image, PriceRange priceRange, String state,String city, String userId, String description) {
        super(id,title,position,image,priceRange,state,city,userId,description);
    }

    /**
     * Metóda reprezentujúca doplnenie energie pre cestujúcu skupinu.
     *
     * @param group Skupina typu Group, ktorej bola doplnená energia.
     */
    @Override
    public void increase(Group group) {
        group.setEnergy(100);
    }

    /**
     * Metóda reprezentujúca doplnenie energie pre cestujúceho jednotlivca.
     *
     * @param user Užívateľ typu User, ktorému bola doplnená energia.
     */
    @Override
    public void increase(User user) {
        user.setEnergy(100);
    }

}
