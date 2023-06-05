package com.example.oop_project;

/**
 * Trieda SingletonUser predstavuje aktualne prihlaseneho uzivatela a uchovava udaje o nom, ako id, meno a heslo. Implementuje niekolko metod na operaciu nad prihlasenym uzivatelom.
 */
public class SingletonUser {

    /**
     * ID prihlaseneho pouzivatela typu int.
     */
    private int id;

    /**
     * Meno prihlaseneho pouzivatela typu String.
     */
    private String username;

    /**
     * Heslo prihlaseneho pouzivatela typu String.
     */
    private String password;

    /**
     * Zadefinovanie instancie triedy SingletonUser.
     */
    private static SingletonUser instance = null;

    /**
     * Konstruktor na vytvorenie instancie triedy SingletonUser.
     */
    private SingletonUser() {
    }

    /**
     * Metoda na ziskanie instancie typu SingletonUser - ak taka neexistuje, vytvori sa nova volanim konstruktora.
     *
     * @return Instancia typu SingletonUser.
     */
    public synchronized static SingletonUser getInstance() {
        if (instance == null)
            instance = new SingletonUser();
        return instance;
    }

    /**
     * Metoda na ziskanie ID prave prihlaseneho pouzivatela.
     *
     * @return ID prave prihlaseneho pouzivatela typu int.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Metoda na nastaveenie id, mena a hesla pre prave prihlaseneho pouzivatela.
     *
     * @param id ID typu int.
     * @param username Meno typu String.
     * @param password Heslo typu String.
     */
    public void setCredentials(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
