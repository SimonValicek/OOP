package com.example.oop_project;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * Trieda GroupRouteSimulation implementuje rozhranie RouteSimulation, a metody potrebne pre simulaciu projektu pomocou GUI.
 */
public class GroupRouteSimulation implements RouteSimulation {

    /**
     * Cesta typu GroupRoute, ktora sa ma simulovat.
     */
    private GroupRoute route;

    /**
     * Premenna typu PropertyChangeSupport, sluziaca na pridanie event listenerov.
     */
    private PropertyChangeSupport pcs;

    /**
     * Premenna uchovavajuca errorove hlasenie - typu String.
     */
    private String errorMessage = "";

    /**
     * Premenna uchovavajuca aktualnu zastavku, na ktorej sa simulacia nachadza - typu RouteStop.
     */
    private RouteStop currentStop;

    /**
     * List event listenerov typu PropertyChangeListener.
     */
    private List<PropertyChangeListener> listeners = new ArrayList<>();

    /**
     * Metoda na pridanie event listenera.
     *
     * @param listener Event listener typu PropertyChangeSupport.
     */
    public void addListener(PropertyChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * Konstruktor triedy GroupRouteSimulation so zadanymi parametrami.
     *
     * @param route Objekt typu GroupRoute predstavujuci skupinovu cestu.
     */
    public GroupRouteSimulation(GroupRoute route) {
        this.route = route;
        this.pcs = new PropertyChangeSupport(this);
    }

    /**
     * Metoda na ziskanie errorovej spravy z premennej errorMessage typu String.
     *
     * @return Sprava s konkretnym errorom typu String.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Metoda na nastavenie novej errorovej spravy typu String.
     *
     * @param errorMessage Nova errorova sprava typu String.
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Metoda na nastavenie novej aktualnej zastavky typu RouteStop.
     *
     * @param stop Nova - aktualna zastavka typu RouteStop, na ktorej sa simulacia nachadza.
     */
    public void setStop(RouteStop stop) {
        this.currentStop=stop;
    }

    /**
     * Metoda na ziskanie aktualnej zastavky, na ktorej sa simulacia nachadza.
     *
     * @return Aktualna zastavka typu RouteStop.
     */
    public RouteStop getStop(){
        return this.currentStop;
    }

    /**
     * Metoda na spracovanie eventu.
     *
     * @param propertyName Nazov, reprezentujuci dej, ktory sa ma vykonat (error, pause...) - typu String.
     * @param oldValue Povodna hodnota typu boolean, ktora sa ma zmenit.
     * @param newValue Nova hodnota typu boolean, na ktoru sa ma zmenit povodna hodnota.
     */
    private void emitEventBoolean(String propertyName, boolean oldValue, boolean newValue) {
        PropertyChangeEvent event = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
        for (PropertyChangeListener listener : listeners) {
            listener.propertyChange(event);
        }
    }

    /**
     * Metoda na spracovanie eventu.
     *
     * @param propertyName Nazov, reprezentujuci dej, ktory sa ma vykonat (error, pause...) - typu String.
     * @param oldValue Povodna hodnota typu double, ktora sa ma zmenit.
     * @param newValue Nova hodnota typu double, na ktoru sa ma zmenit povodna hodnota.
     */
    private void emitEventDouble(String propertyName, double oldValue, double newValue) {
        PropertyChangeEvent event = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
        for (PropertyChangeListener listener : listeners) {
            listener.propertyChange(event);
        }
    }

    /**
     * Metoda zabezpecujuca vyskakovanie okien s hlaskami (varovaniami) o pridani zastavky do cesty - z roznych dovodov.
     *
     * @param stop Zastavka typu RouteStop, ktora je odporucana na pridanie do cesty na zaklade jej vzdialenosti a typu varovania.
     * @return Vracia true, ak sa zastavka pridala, false ak sme sa rozhodli varovanie ignorovat - pomocou GUI.
     */
    private boolean showDialogAndWait(RouteStop stop) {
        final CountDownLatch latch = new CountDownLatch(1);
        AtomicBoolean addStopClicked = new AtomicBoolean(false);

        Platform.runLater(() -> {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);

            Button addStop = null;
            if (stop != null) {
                addStop = new Button("Add stop");
                addStop.setOnAction(event -> {
                    dialog.close();
                    latch.countDown();
                    addStopClicked.set(true);
                });
            }

            Button closeButton = new Button("Ignore");
            closeButton.setOnAction(event -> {
                dialog.close();
                latch.countDown();
            });

            HBox buttonBox = new HBox(10);
            if (addStop != null) {
                buttonBox.getChildren().add(addStop);
            }
            buttonBox.getChildren().add(closeButton);
            buttonBox.setAlignment(Pos.CENTER);

            String labelText = (stop != null) ? "Add " + stop.getTitle() + " to the route even though it may not help." : "There is no other stop on the route";
            Label label = new Label(labelText);
            label.setPadding(new Insets(10, 0, 20, 0));

            VBox contentBox = new VBox(10);
            contentBox.getChildren().addAll(label, buttonBox);
            contentBox.setAlignment(Pos.CENTER);

            StackPane dialogLayout = new StackPane(contentBox);
            dialogLayout.setStyle("-fx-background-color: #F0F0F0;");
            StackPane.setAlignment(contentBox, Pos.CENTER);

            Scene dialogScene = new Scene(dialogLayout, 300, 150);

            dialog.setScene(dialogScene);
            dialog.show();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return addStopClicked.get();
    }

    /**
     * Metoda na vypocet najblizsej dostupnej zastavky na doplnenie paliva s vynimkou tych, ktore sa v ceste uz nachadzaju.
     *
     * @param allStops List so vsetkymi zastavkami z databazy - typu RouteStop.
     * @param stops List so vsetkymi zastavkami pre aktualnu cestu - typu RouteStop.
     * @param position Pozicia, na ktorej sa prave nachadzame v ramci simulacie - typu int.
     * @return V pripade, ze existuje zastavka na doplnenie paliva, ktora este nie je sucastou cesty, a ktoru sme este nepresvihli, vrati ju. V opacnom pripade vracia null.
     */
    public Optional<RouteStop> findFirstMissingFuelStop(List<RouteStop> allStops, List<RouteStop> stops, int position) {
        Set<Integer> stopIds = stops.stream().map(RouteStop::getId).collect(Collectors.toSet());

        return allStops.stream()
                .filter(stop -> stop instanceof FuelStop && !stopIds.contains(stop.getId()) && stop.getPosition() >= position)
                .sorted(Comparator.comparing(RouteStop::getPosition))
                .findFirst();
    }

    /**
     * Metoda na vypocet najblizsej dostupnej zastavky na doplnenie hladu/smadu s vynimkou tych, ktore sa v ceste uz nachadzaju.
     *
     * @param allStops List so vsetkymi zastavkami z databazy - typu RouteStop.
     * @param stops List so vsetkymi zastavkami pre aktualnu cestu - typu RouteStop.
     * @param position Pozicia, na ktorej sa prave nachadzame v ramci simulacie - typu int.
     * @return V pripade, ze existuje zastavka na doplnenie hladu/smadu, ktora este nie je sucastou cesty, a ktoru sme este nepresvihli, vrati ju. V opacnom pripade vracia null.
     */
    public Optional<RouteStop> findFirstMissingEatStop(List<RouteStop> allStops, List<RouteStop> stops, int position) {
        Set<Integer> stopIds = stops.stream().map(RouteStop::getId).collect(Collectors.toSet());

        return allStops.stream()
                .filter(stop -> stop instanceof EatStop && !stopIds.contains(stop.getId()) && stop.getPosition() >= position)
                .sorted(Comparator.comparing(RouteStop::getPosition))
                .findFirst();
    }


    /**
     * Metoda na vypocet najblizsej dostupnej zastavky na doplnenie energie s vynimkou tych, ktore sa v ceste uz nachadzaju.
     *
     * @param allStops List so vsetkymi zastavkami z databazy - typu RouteStop.
     * @param stops List so vsetkymi zastavkami pre aktualnu cestu - typu RouteStop.
     * @param position Pozicia, na ktorej sa prave nachadzame v ramci simulacie - typu int.
     * @return V pripade, ze existuje zastavka na doplnenie energie, ktora este nie je sucastou cesty, a ktoru sme este nepresvihli, vrati ju. V opacnom pripade vracia null.
     */
    public Optional<RouteStop> findFirstMissingStayStop(List<RouteStop> allStops, List<RouteStop> stops, int position) {
        Set<Integer> stopIds = stops.stream().map(RouteStop::getId).collect(Collectors.toSet());

        return allStops.stream()
                .filter(stop -> stop instanceof StayStop && !stopIds.contains(stop.getId()) && stop.getPosition() >= position)
                .sorted(Comparator.comparing(RouteStop::getPosition))
                .findFirst();
    }


    /**
     * Metoda na vypocet najblizsej dostupnej zastavky na doplnenie hodnoty poziadavku s vynimkou tych, ktore sa v ceste uz nachadzaju.
     *
     * @param allStops List so vsetkymi zastavkami z databazy - typu RouteStop.
     * @param stops List so vsetkymi zastavkami pre aktualnu cestu - typu RouteStop.
     * @param position Pozicia, na ktorej sa prave nachadzame v ramci simulacie - typu int.
     * @return V pripade, ze existuje zastavka na doplnenie hodnoty poziadavku, ktora este nie je sucastou cesty, a ktoru sme este nepresvihli, vrati ju. V opacnom pripade vracia null.
     */
    public Optional<RouteStop> findFirstMissingPleasureStop(List<RouteStop> allStops, List<RouteStop> stops, int position) {
        Set<Integer> stopIds = stops.stream().map(RouteStop::getId).collect(Collectors.toSet());

        return allStops.stream()
                .filter(stop -> (stop instanceof FunStop || stop instanceof WildStop || stop instanceof HeritageStop)
                        && !stopIds.contains(stop.getId()) && stop.getPosition() >= position)
                .sorted(Comparator.comparing(RouteStop::getPosition))
                .findFirst();
    }

    /**
     * Metoda, ktora vracia index danej zastavky v ramci cesty.
     *
     * @param stops CopyOnWriteArrayList zastaviek typu RouteStop, ktore su sucastou nasej cesty.
     * @param stop Zastavka, ktoru sa chystame do cesty pridat - pocas jej simulacie.
     * @return Index typu int, na ktorom sa dana zastavka v ramci nasej novej cesty nachadza. V pripade chyby vrati velkost pola zastavok.
     */
    public int getIndex(CopyOnWriteArrayList<RouteStop> stops, RouteStop stop) {
        int position = stop.getPosition();

        for (int i = 0; i < stops.size(); i++) {
            RouteStop currentStop = stops.get(i);

            if (currentStop.getPosition() > position) {
                return i;
            }
        }

        return stops.size();
    }

    /**
     * Metoda reprezentujuca backend pre graficku simulaciu cesty.
     * Vyuziva druhy thread, na ktorom spusta simulaciu a pomocou ktorej si vymiena informacie s GUI, ohladom priebehu simulovania cesty.
     * Su v nej vyuzite volania vsetkych vyssie zadefinovanych metod.
     * Spusta simulaciu, zastavuje simulacie, prepocitava hodnoty hladu, smadu, pozitku, paliva a energie v pravidelnych casovych intervaloch.
     * While cyklus prechadza pole zastavok, postupne - jednu po druhej, a operuje nad nimi.
     * Ak klesne niektora z hodnot pod urcitu uroven (vopred definovanu), metoda vyhadzuje error a caka sa na vstup pouzivatela prostrednictvom GUI.
     * V pripade, ze cela simulacia prebehne uspesne, vyzobrazuje sa prostrednictvom GUI sprava o uspesnej simulacii, pomocou implementovania vynimky.
     * V pripade, ze niektora z hodnot sa dostane pod 0, simulacia sa ukoncuje neuspesne, takisto prostrednictvom vynimky a error vypisu.
     */
    public void simulate() {
        route.getGroup().getPassengers().forEach(user -> System.out.println(user.getUsername()));
        Group group = route.getGroup();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        scheduler.schedule(() -> {
            StopsHandler sh = StopsHandler.getInstance();
            ArrayList<RouteStop> allStops = null;
            try {
                allStops = new ArrayList<>(sh.getAll());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            CopyOnWriteArrayList<RouteStop> stops = new CopyOnWriteArrayList<>(sh.orderedStops(route));
            stops.forEach(stop->
                System.out.println(stop.getTitle()));
                System.out.println("The initializing size of the array is "+stops.size());
            double timePerKilometer = 60.0 / 3940.0;
            long startTime = System.currentTimeMillis();
            int i = 0;

            boolean shownEatError = false;
            boolean shownEnergyError = false;
            boolean shownFuelError = false;
            boolean shownPleasureError = false;
            while (i < stops.size()) {
                RouteStop stop = stops.get(i);
                setStop(stop);
                System.out.println(stop.getPosition());
                double targetTime = stop.getPosition() * timePerKilometer;
                double elapsedTime;
                double updateTime = 0;

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
                String formattedDateTime = now.format(formatter);
                System.out.println("Current start time with milliseconds: " + formattedDateTime);

                do {
                    elapsedTime = (System.currentTimeMillis() - startTime) / 1000.0;
                  //  System.out.println(elapsedTime/timePerKilometer);

                    if ((elapsedTime - updateTime) > 0.2) {
                        updateTime = elapsedTime;
                        double oldEnjoy = group.getEnjoy();
                        double oldDrink = group.getDrink();
                        double oldEnergy = group.getEnergy();
                        double oldFuel = group.getFuel();
                        double oldFood = group.getFood();

                        group.setEnjoy(oldEnjoy - 0.78);
                        group.setDrink(oldDrink - 0.65);
                        group.setEnergy(oldEnergy - 0.45);
                        group.setFuel(oldFuel - 0.55);
                        group.setFood(oldFood - 0.5);



                            if (oldEnjoy < 30 && !shownPleasureError) {
                                shownPleasureError = true;
                                setErrorMessage("Warning: Enjoyment level too low.");
                                emitEventBoolean("error", false, true);
                                double popUpStart = System.currentTimeMillis();
                                RouteStop firstMissingPleasureStop = findFirstMissingPleasureStop(allStops, stops, stop.getPosition()).orElse(null);
                                boolean value =   showDialogAndWait(firstMissingPleasureStop);
                                if(value) {
                                    int index = getIndex(stops, firstMissingPleasureStop);
                                    stops.add(index,firstMissingPleasureStop);
                                    System.out.println("The "+firstMissingPleasureStop.getTitle()+" was added at "+index+". position. The size of the new array is "+stops.size());
                                }
                                else
                                    System.out.println("close button was clicked");
                                double popUpEnd = System.currentTimeMillis();
                                emitEventBoolean("error", true, false);
                                startTime += (popUpEnd - popUpStart);
                            } else if (oldDrink < 30 && !shownEatError) {
                                shownEatError = true;
                                setErrorMessage("Warning: Thirst level too high.");
                                emitEventBoolean("error", false, true);
                                double popUpStart = System.currentTimeMillis();
                                RouteStop firstMissingEatStop = findFirstMissingEatStop(allStops, stops, stop.getPosition()).orElse(null);
                                boolean value =   showDialogAndWait(firstMissingEatStop);
                                if(value) {
                                    int index = getIndex(stops, firstMissingEatStop);
                                    stops.add(index, firstMissingEatStop);
                                    System.out.println("The " + firstMissingEatStop.getTitle() + " was added at " + index + ". position. The size of the new array is "+stops.size());
                                }
                                else
                                    System.out.println("close button was clicked");
                                double popUpEnd = System.currentTimeMillis();
                                emitEventBoolean("error", true, false);
                                startTime += (popUpEnd - popUpStart);
                            } else if (oldEnergy < 40 && !shownEnergyError) {
                                shownEnergyError = true;
                                setErrorMessage("Warning: Energy level too low.");
                                emitEventBoolean("error", false, true);
                                double popUpStart = System.currentTimeMillis();
                                RouteStop firstMissingStayStop = findFirstMissingStayStop(allStops, stops, stop.getPosition()).orElse(null);
                                boolean value =   showDialogAndWait(firstMissingStayStop );
                                if(value) {
                                    int index = getIndex(stops, firstMissingStayStop);
                                    stops.add(index, firstMissingStayStop);
                                    System.out.println("The " + firstMissingStayStop.getTitle() + " was added at " + index + ". position. The size of the new array is "+stops.size());
                                }
                                else
                                    System.out.println("close button was clicked");
                                double popUpEnd = System.currentTimeMillis();
                                emitEventBoolean("error", true, false);
                                startTime += (popUpEnd - popUpStart);
                            } else if (oldFuel < 40 && !shownFuelError) {
                                shownFuelError = true;
                                setErrorMessage("Warning: Fuel level too low.");
                                emitEventBoolean("error", false, true);
                                double popUpStart = System.currentTimeMillis();
                                RouteStop firstMissingFuelStop = findFirstMissingFuelStop(allStops, stops, stop.getPosition()).orElse(null);
                                boolean value =   showDialogAndWait(firstMissingFuelStop  );
                                if(value)
                                {
                                    int index = getIndex(stops, firstMissingFuelStop);
                                    stops.add(index, firstMissingFuelStop);
                                    System.out.println("The " + firstMissingFuelStop.getTitle() + " was added at " + index + ". position. The size of the new array is "+stops.size());
                                }
                                else
                                    System.out.println("close button was clicked");
                                double popUpEnd = System.currentTimeMillis();
                                emitEventBoolean("error", true, false);
                                startTime += (popUpEnd - popUpStart);
                            } else if (oldFood < 30 && !shownEatError) {
                                shownEatError = true;
                                setErrorMessage("Warning: Hunger level too high.");
                                emitEventBoolean("error", false, true);
                                double popUpStart = System.currentTimeMillis();
                                RouteStop firstMissingEatStop = findFirstMissingEatStop(allStops, stops, stop.getPosition()).orElse(null);
                                boolean value =   showDialogAndWait(firstMissingEatStop);
                                if(value)
                                {
                                    int index = getIndex(stops, firstMissingEatStop);
                                    stops.add(index, firstMissingEatStop);
                                    System.out.println("The " + firstMissingEatStop.getTitle() + " was added at " + index + ". position. The size of the new array is "+stops.size());
                                }
                                else
                                    System.out.println("close button was clicked");
                                double popUpEnd = System.currentTimeMillis();
                                emitEventBoolean("error", true, false);
                                startTime += (popUpEnd - popUpStart);
                            }


                        if (oldDrink < 0 || oldEnergy < 0 || oldEnjoy < 0 || oldFood < 0 || oldFuel < 0) {
                            setErrorMessage("You've died");
                            emitEventBoolean("error", false, true);

                            scheduler.shutdown();
                            throw new RuntimeException("The user has died.");

                        }
                        emitEventDouble("enjoy", oldEnjoy, group.getEnjoy());
                        emitEventDouble("drink", oldDrink, group.getDrink());
                        emitEventDouble("energy", oldEnergy, group.getEnergy());
                        emitEventDouble("fuel", oldFuel, group.getFuel());
                        emitEventDouble("food", oldFood, group.getFood());
                    }

                } while (elapsedTime < targetTime);

                stop.increase(group);
                LocalDateTime end = LocalDateTime.now();
                DateTimeFormatter formatte = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
                String formattedDateTim = end.format(formatte);
                System.out.println("Current end time with milliseconds: " + formattedDateTim);

                stop.increase(route.getGroup());
                emitEventBoolean("paused", false, true);
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                startTime += TimeUnit.SECONDS.toMillis(3);

                emitEventBoolean("paused", true, false);
                i++;
            }

            emitEventBoolean("finish", false, true);

            scheduler.shutdown();
            throw new RuntimeException("The route has ended");
        }, 0, TimeUnit.MILLISECONDS);

        scheduler.shutdown();

    }
}