package fr.kazam.focus.utils;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Arena {

    private String id;
    private String name;
    private int maxPlayers;
    private boolean isUsed;
    private boolean hasNightVision;
    private int time;
    private List<Location> spawnLocations;
    private HashMap<Location,Integer> gloryPointsLocations;
    private List<Location> shopLocations;

    public Arena(String id, String name, int maxPlayers, boolean hasNightVision, int time) {
        this.id = id;
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.isUsed = false;
        this.hasNightVision = hasNightVision;
        this.time = time;
        this.spawnLocations = new ArrayList<>();
        this.gloryPointsLocations = new HashMap<>();
        this.shopLocations = new ArrayList<>();
    }

    public Arena(String id, String name, int maxPlayers, boolean hasNightVision, int time, List<Location> spawnLocations, List<Location> shopLocations, HashMap<Location,Integer> gloryPointsLocations) {
        this.id = id;
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.isUsed = false;
        this.hasNightVision = hasNightVision;
        this.time = time;
        this.spawnLocations =spawnLocations;
        this.gloryPointsLocations = gloryPointsLocations;
        this.shopLocations = shopLocations;
    }

    public void useArena(){
        this.isUsed = true;
    }

    public void freeArena(){
        this.isUsed = false;
    }



    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public boolean isHasNightVision() {
        return hasNightVision;
    }

    public int getTime() {
        return time;
    }

    public List<Location> getSpawnLocations() {
        return spawnLocations;
    }

    public HashMap<Location,Integer> getGloryPointsLocations() {
        return gloryPointsLocations;
    }

    public List<Location> getShopLocations() {
        return shopLocations;
    }

    public void addSpawnLocation(Location location){
        spawnLocations.add(location);
    }

    public void addGloryPointsLocation(Location location, int chance){
        gloryPointsLocations.put(location, chance);
    }
    public void addShopLocation(Location location){
        shopLocations.add(location);
    }





}
