package com.example.oop_project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Trieda StopsHandler implementuje rozhranie Handler a predstavuje nástroj na prácu so zastávkami.
 * Implementuje metódy, zabezpečujúce prácu s objektami reprezentujúcimi zastávky (typ RouteStop) a databázou pre zastávky (StopsDatabase).
 */
public class StopsHandler implements Handler {

    /**
     * Zadefinovanie inštancie triedy StopsHandler.
     */
    private static StopsHandler instance = null;

    /**
     * Konštruktor na vytvorenie inštancie triedy StopsHandler.
     */
    private StopsHandler(){
    }

    /**
     * Metóda na získanie inštancie typu StopsHandler - ak taká neexistuje, vytvorí sa nová volaním konštruktora.
     *
     * @return Inštancia typu StopsHandler.
     */
    public synchronized static StopsHandler getInstance(){
        if(instance==null)
            instance = new StopsHandler();
        return instance;
    }

    /**
     * Predstavuje mapovanie daného reťazca, reprezentujúceho druh zastávky s číslom typu pre danú zastávku.
     *
     */
    private static final Map<String, String> typeMap = Map.of(
            "Eat", "1",
            "Fuel", "2",
            "Stay", "3",
            "Wild", "4",
            "Fun", "5",
            "Heritage", "6"
    );

    /**
     * Metóda zabezpečujúca pridávanie zastávok do databázy.
     *
     * @param args Reťazec preddstavujúci všetky informácie o danej zastávke.
     * @throws IOException Výnimka v prípade chybného I/O.
     */
    @Override
    public void addToDatabase(String... args) throws IOException {
        String type = args[0];
        String title = args[1];
        String position = args[2];
        String image = args[3];
        String price = args[4];
        String state = args[5];
        String city = args[6];
        String userId = args[7];
        String description = args[8];
        StopsDatabase sd = StopsDatabase.getInstance();
        String value = typeMap.getOrDefault(type, "");
        PriceRange pr = PriceRange.valueOf(price);
        sd.setData(sd.getData().size()+1+";"+value+";"+title+";"+position+";"+image+";"+pr+";"+state+";"+city+";"+userId+";"+description);
    }

    /**
     * Metóda zabezpečujúca získanie všetkých zastávok z databázy.
     *
     * @return ArrayList zastávok typu RouteStop.
     * @throws FileNotFoundException Výnimka v prípade, že sa nenašiel súbor reprezentujúci databázu zastávok.
     */
    @Override
    public ArrayList<RouteStop> getAll() throws FileNotFoundException {
        StopsDatabase sd = StopsDatabase.getInstance();
        ArrayList<RouteStop> stops = new ArrayList<>();
        StopsFactory sf = new StopsFactory();
        sd.getData().forEach((line) -> {
            String[] s = line.split(";");
            RouteStop stop = sf.createStop(s);
            stops.add(stop);
        });
        return stops;
    }

    /**
     * Metóda slúžiaca na vytvorenie objektu zastávky typu RouteStop.
     *
     * @param id ID zastávky typu int.
     * @return Nový objekt zastávky typu RouteStop.
     * @throws FileNotFoundException Výnimka v prípade, že sa nenašiel súbor predstavujúci databázu zastávok.
     */
    public RouteStop createStop(int id) throws FileNotFoundException {
        StopsDatabase sd = StopsDatabase.getInstance();
        StopsFactory sf = new StopsFactory();
        for(String line : sd.getData()){
            String[] str = line.split(";");
            if(str[0].equals(String.valueOf(id))){
                RouteStop stop = sf.createStop(str);
                return stop;
            }
        }
        return null;
    }

    /**
     * Metóda vytvárania objektov zastávok.
     * To isté ako predošla metóda, avšak vytvárar viacero zastávok naraz.
     *
     * @param ids IDs predstavujúce identifikátory zastaávok v databáze.
     * @return ArrayList zastávok typu RouteStop.
     * @throws FileNotFoundException Výnimka v prípade, že sa nenašiel súbor predstavujúci databázu zastávok.
     */
    public ArrayList<RouteStop> createStops(String ids) throws FileNotFoundException {
        ArrayList<RouteStop> stopList = new ArrayList<>();
        if (ids == null || ids.isEmpty()) {
            return stopList;
        }
        String[] idArray = ids.split(",");
        for (String id : idArray) {
            try {
                int stopId = Integer.parseInt(id);
                RouteStop stop = createStop(stopId);
                stopList.add(stop);
                } catch (NumberFormatException e) {
            }
        }
        return stopList;
    }

    /**
     * Metóda na preusporiadanie ArrayList-u zastávok podľa kilometra, na ktorom sa nachádzajú - vzostupne.
     *
     * @param route Cesta typu Route, ktorej zastávky sa budú preusporiadavať.
     * @return ArrayList preusporiadaných zastávok typu RouteStop, podľa kilometram, na ktorom sa nachádzajú - vzostupne.
     */
    public ArrayList<RouteStop> orderedStops(Route route){
        ArrayList<RouteStop> stops = new ArrayList<>(route.getStops());
        Collections.sort(stops, Comparator.comparingInt(RouteStop::getPosition));
        return stops;
    }

}
