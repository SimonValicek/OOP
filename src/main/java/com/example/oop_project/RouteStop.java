package com.example.oop_project;

/**
 * Abstraktna trieda reprezentujuca zastavku na ceste. Uchovava v sebe informacie ako id, nazov, stat, mesto, url obrazku, poziciu, cenovy rozsah, id pouzivatela, ktory ju vytvoril a popis.
 */
public abstract class RouteStop {

    /**
     * ID zastavky typu int.
     */
    protected int id;

    /**
     * Nazov zastavky typu String.
     */
    protected String title;

    /**
     * Nazov statu, v ktorom sa zastavka nachadza - typu String.
     */
    protected String state;

    /**
     * Nazov mesta, v ktorom sa zastavka nachadza - typu String.
     */
    protected String city;

    /**
     * URL obrazka typu String.
     */
    protected String image;

    /**
     * Kilometer, na ktorom sa zastavka nachadza v ramci cesty - typu int.
     */
    protected int position;

    /**
     * Cenove rozpatie typu PriceRange.
     */
    protected PriceRange priceRange;

    /**
     * ID pouzivatela, ktory zastavku pridal - typu String.
     */
    protected String userId;

    /**
     * Popis zastavky typu String.
     */
    protected String description;

    /**
     * Konstruktor na vytvorenie novej zastavky na zaklade danych parametrov.
     *
     * @param id ID zastavky typu int.
     * @param title Nazov zastavky typu String.
     * @param position Kilometer, na ktorom sa zastavka nachadza v ramci cesty - typu int.
     * @param image URL obrazok zastavky typu String.
     * @param priceRange Cenove rozpatie typu PriceRange.
     * @param state Stat, v ktorom sa zastavka nachadza - typu String.
     * @param city Mesto, v ktorom sa zastavka nachadza - typu String.
     * @param userId ID pouzivatela, ktory pridal zastavku - typu String.
     * @param description Popis zastavky typu String.
     */
    public RouteStop(int id, String title, int position, String image, PriceRange priceRange, String state,String city,String userId, String description) {
        this.id= id;
        this.title=title;
        this.position = position;
        this.image=image;
        this.priceRange=priceRange;
        this.state=state;
        this.city=city;
        this.userId=userId;
        this.description=description;
    }

    /**
     * Metoda na upravu parametrov skupiny - zvysenie hodnot (food,drink,enjoy,fuel,energy)
     *
     * @param group Skupina typu Group, ktorej parametre sa maju upravit/modifikovat.
     */
    public abstract void increase(Group group);

    /**
     * Metoda na upravu parametrov jednotlivca/pouzivatela - zvysenie hodnot (food,drink,enjoy,fuel,energy)
     *
     * @param user Jednotlivec/pouzivatel typu User, ktoreho parametre sa maju upravit/modifikovat.
     */
    public abstract void increase(User user);

    /**
     * Metoda na ziskanie nazvu zastavky.
     *
     * @return Nazov zastavky typu String.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Metoda na ziskanie ID zastavky.
     *
     * @return ID zastavky typu int.
     */
    public int getId(){
        return id;
    }

    /**
     * Metoda na ziskanie pozicie zastavky.
     *
     * @return Pozicia zastavky typu int.
     */
    public int getPosition(){
        return position;
    }

    /**
     * Metoda na ziskanie obrazku zastavky.
     *
     * @return URL odkaz obrazka typu String.
     */
    public String getImage(){
        return image;
    }

    /**
     * Metoda na ziskanie statu, v ktorom sa zastavka nachadza.
     *
     * @return Nazov statu typu String.
     */
    public String getState(){
        return state;
    }

    /**
     * Metoda na ziskanie mesta, v ktorom sa zastavka nachadza.
     *
     * @return Nazov mesta typu String.
     */
    public String getCity(){
        return city;
    }

    /**
     * Metoda na ziskanie ID pouzivatela, ktory zastavku vytvoril.
     *
     * @return ID pouzivatela typu String.
     */
    public String getUserId() {return userId;}

    /**
     * Metoda na ziskanie popisu zastavky.
     *
     * @return Popis zastavky typu String.
     */
    public String getDescription() {return description;}

    /**
     * Metoda na nastavenie noveho ID zastavky.
     *
     * @param id Nove ID zastavky typu int.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metoda na nastavenie noveho obrazku zastavky.
     *
     * @param image Novy URL odkaz obrazka typu String.
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Metoda na nastavenie novej pozicie zastavky.
     *
     * @param position Nova pozicia zastavky typu int.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Metoda na nastavenie noveho nazvu statu, v ktorom sa zastavka nachadza.
     *
     * @param state Novy nazov statu typu String.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Metoda na nastavenie noveho nazvu mesta, v ktorom sa zastavka nachadza.
     *
     * @param city Novy nazov mesta typu String.
     */
    public void setCity(String city) {this.city=city;}

    /**
     * Metoda na nastavenie noveho nazvu zastavky.
     *
     * @param title Novy nazov zastavky typu String.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Metoda na nastavenie noveho popisu zastavky.
     *
     * @param description Novy popis zastav
     */
    public void setDescription(String description) {this.description=description;}
}
