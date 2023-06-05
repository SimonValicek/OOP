package com.example.oop_project;

/**
 * Trieda User reprezentujúca používateľa/jednotlivca v rámci projektu.
 * Trieda User implementuje metódy na manipuláciu s informáciami ohľadom používateľa/jednotlivca.
 */
public class User {

    /**
     * ID používateľa typu int.
     */
    private int id;

    /**
     * Meno používateľa typu String.
     */
    private String username;

    /**
     * Hodnota energie typu double.
     */
    private double energy;

    /**
     * Hodnota jedla/hladu typu double.
     */
    private double food;

    /**
     * Hodnota pitia/smädu typu double.
     */
    private double drink;

    /**
     * Hodnota pôžitku typu double.
     */
    private double enjoy;

    /**
     * Hodnota paliva typu double.
     */
    private double fuel;

    /**
     * Konštruktor na vytvorenie používateľa s príslušným ID a menom.
     *
     * @param id ID používateľa typu String.
     * @param username Meno používateľa typu String.
     */
    public User(int id, String username) {
        this.id=id;
        this.username=username;
        this.energy=100;
        this.food=100;
        this.drink=100;
        this.enjoy=100;
        this.fuel=100;
    }

    /**
     * Metóda na získanie ID používateľa.
     *
     * @return ID typu int.
     */
    public int getId() {
        return id;
    }

    /**
     * Metóda na získanie mena používateľa.
     *
     * @return Meno typu String.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Metóda na nastavenie novej hodnoty paliva používateľa.
     *
     * @param fuel Nová hodnota paliva typu double.
     */
    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    /**
     * Metóda na získanie hodnoty paliva používateľa.
     *
     * @return Hodnota paliva typu double.
     */
    public double getFuel() {
        return fuel;
    }

    /**
     * Metóda na nastavenie novej hodnoty jedla/hladu používateľa.
     *
     * @param food Nová hodnota jedla/hladu typu double.
     */
    public void setFood(double food) {
        this.food = food;
    }

    /**
     * Metóda na nastavenie novej hodnoty pôžitku používateľa.
     *
     * @param enjoy Nová hodnota pôžitku typu double.
     */
    public void setEnjoy(double enjoy) {
        this.enjoy = enjoy;
    }

    /**
     * Metóda na nastavenie novej hodnoty pitia/smädu používateľa.
     *
     * @param drink Nová hodnota pitia/smädu typu double.
     */
    public void setDrink(double drink) {
        this.drink = drink;
    }

    /**
     * Metóda na nastavenie novej hodnoty energie používateľa.
     *
     * @param energy Nová hodnota energie typu double.
     */
    public void setEnergy(double energy) {
        this.energy = energy;
    }

    /**
     * Metóda na získanie hodnoty pitia/smädu používateľa.
     *
     * @return Hodnota pitia/smädu typu double.
     */
    public double getDrink() {
        return drink;
    }

    /**
     * Metóda na získanie hodnoty energie používateľa.
     *
     * @return Hodnota energie typu double.
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * Metóda na získanie hodnoty pôžitku používateľa.
     *
     * @return Hodnota pôžitku typu double.
     */
    public double getEnjoy() {
        return enjoy;
    }

    /**
     * Metóda na získanie hodnoty jedla/hladu používateľa.
     *
     * @return Hodnota jedla/hladu typu double.
     */
    public double getFood() {
        return food;
    }
}
