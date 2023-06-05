package com.example.oop_project;

import java.util.ArrayList;
import java.util.Collection;


/**
 * Trieda Group reprezentujuca skupinu uzivatelov v ramci projektu.
 * Trieda Group implementuje metody na manipulaciu s informaciami ohladom skupiny.
 */
public class Group {

    /**
     * ID skupiny typu String.
     */
    private String id;

    /**
     * Nazov skupiny typu String.
     */
    private String title;

    /**
     * Vlastnik (zakladatel skupiny) typu User.
     */
    private User owner;

    /**
     * ArrayList cestujucich typu User.
     */
    private ArrayList<User> passengers;

    /**
     * Hodnota energie typu double.
     */
    private double energy;

    /**
     * Hodnota jedla/hladu typu double.
     */
    private double food;

    /**
     * Hodnota pitia/smadu typu double.
     */
    private double drink;

    /**
     * Hodnota pozitku typu double.
     */
    private double enjoy;

    /**
     * Hodnota paliva typu double.
     */
    private double fuel;


    /**
     * Konstruktor na vytvorenie skupiny s prislusnym ID, nazvom a vlasntikom.
     *
     * @param id ID skupiny typu String.
     * @param title Nazov skupiny typu String.
     * @param owner Vlastnik skupiny typu User.
     *
     * + definuje prazdne pole pasazierov a predvolene hodnoty pre skupinu.
     */
    public Group(String id, String title, User owner) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.passengers = new ArrayList<>();
        this.energy = 100;
        this.food = 100;
        this.drink = 100;
        this.enjoy = 100;
        this.fuel = 100;
    }

    /**
     * Metoda na pridanie pasaziera typu User do skupiny.
     *
     * @param passenger Novy cestujuci.
     */
    public void addPassenger(User passenger) {
        this.passengers.add(passenger);
    }

    /**
     * Metoda na ziskanie pasazierov skupiny.
     *
     * @return Cestujuci typu User.
     */
    public ArrayList<User> getPassengers() {
        return this.passengers;
    }

    /**
     * Metoda na ziskanie vlastnika skupiny.
     *
     * @return Vlastnik typu User.
     */
    public User getOwner() {
        return this.owner;
    }

    /**
     * Metoda na ziskanie ID skupiny.
     *
     * @return ID typu String.
     */
    public String getId(){return this.id;}

    /**
     * Metoda na ziskanie nazvu skupiny.
     *
     * @return Nazov typu String.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Metoda na ziskanie zastavok skupiny.
     *
     * @return Vracia null.
     */
    public Collection<Object> getStops() {
        return null;
    }

    /**
     * Metoda na ziskanie IDs pasazierov skupiny.
     *
     * @return String reprezentujuci IDs pasazierov oddelenych ciarkou.
     */
    public String getPassengerIds() {
        StringBuilder sb = new StringBuilder();
        for (User passenger : passengers) {
            sb.append(passenger.getId()).append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Metoda na ziskanie hodnoty energie skupiny.
     *
     * @return Hodnota energie typu double.
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * Metoda na nastavenie novej hodnoty energie.
     *
     * @param energy Nova hodnota energie typu double.
     */
    public void setEnergy(double energy) {
        this.energy = energy;
    }

    /**
     * Metoda na ziskanie hodnoty pitia/smadu skupiny.
     *
     * @return Hodnota pitia/smadu typu double.
     */
    public double getDrink() {
        return drink;
    }

    /**
     * Metoda na nastavenie novej hodnoty pitia/smadu.
     *
     * @param drink Nova hodnota pitia/smadu typu double.
     */
    public void setDrink(double drink) {
        this.drink = drink;
    }

    /**
     * Metoda na ziskanie hodnoty pozitku skupiny.
     *
     * @return Hodnota pozitku typu double.
     */
    public double getEnjoy() {
        return enjoy;
    }

    /**
     * Metoda na nastavenie novej hodnoty pozitku.
     *
     * @param enjoy Nova hodnota pozitku typu double.
     */
    public void setEnjoy(double enjoy) {
        this.enjoy = enjoy;
    }

    /**
     * Metoda na ziskanie hodnoty jedla/hladu skupiny.
     *
     * @return Hodnota jedla/hladu typu double.
     */
    public double getFood() {
        return food;
    }

    /**
     * Metoda na nastavenie novej hodnoty jedla/hladu.
     *
     * @param food Nova hodnota jedla/hladu typu double.
     */
    public void setFood(double food) {
        this.food = food;
    }

    /**
     * Metoda na ziskanie hodnoty paliva skupiny.
     *
     * @return Hodnota paliva typu double.
     */
    public double getFuel() {
        return fuel;
    }

    /**
     * Metoda na nastavenie novej hodnoty paliva.
     *
     * @param fuel Nova hodnota paliva typu double.
     */
    public void setFuel(double fuel) {
        this.fuel = fuel;
    }
}

