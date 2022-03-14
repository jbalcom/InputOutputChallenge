package com.company;

import java.io.*;
import java.util.*;

public class LocationsChallenge implements Map<Integer, LocationChallenge> {
    private static Map<Integer, LocationChallenge> locations = new HashMap<>();

    public static void main(String[] args) throws IOException{
        try(BufferedWriter locFile = new BufferedWriter(new FileWriter("locations.txt"));
            BufferedWriter dirFile = new BufferedWriter(new FileWriter("directions.txt"))){
            for (LocationChallenge locationChallenge : locations.values()){
                locFile.write(locationChallenge.getLocationID() + "," + locationChallenge.getDescription() + "\n");
                for (String direction : locationChallenge.getExits().keySet()){
                    if (!direction.equalsIgnoreCase("Q")) {
                        dirFile.write(locationChallenge.getLocationID() + "," + direction + "," +
                                locationChallenge.getExits().get(direction) + "\n");
                    }
                }
            }
        }
    }

    static {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader("locations_big.txt")))) {
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                int loc = scanner.nextInt();
                scanner.skip(scanner.delimiter());
                String description = scanner.nextLine();
                System.out.println("Imported loc: " + loc + ": " + description);
                Map<String, Integer> tempExit = new LinkedHashMap<String, Integer>();
                locations.put(loc, new LocationChallenge(loc, description, tempExit));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader dirFile = new BufferedReader(new FileReader("directions_big.txt"))) {
            String input;
            while ((input = dirFile.readLine()) != null) {
                String[] data = input.split(",");
                int loc = Integer.parseInt(data[0]);
                String direction = data[1];
                int destination = Integer.parseInt(data[2]);
                System.out.println(loc + ": " + direction + ": " + destination);
                LocationChallenge locationChallenge = locations.get(loc);
                locationChallenge.addExits(direction, destination);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public LocationChallenge get(Object key) {
        return locations.get(key);
    }

    @Override
    public LocationChallenge put(Integer key, LocationChallenge value) {
        return locations.put(key, value);
    }

    @Override
    public LocationChallenge remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends LocationChallenge> map) {
    }

    @Override
    public void clear() {
        locations.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<LocationChallenge> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, LocationChallenge>> entrySet() {
        return locations.entrySet();
    }
}
