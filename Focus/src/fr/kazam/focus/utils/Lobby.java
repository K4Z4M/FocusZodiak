package fr.kazam.focus.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class Lobby {

    private String id;
    private String name;
    private int maxPlayers;
    private ArrayList<Player> players;
    private LobbyState lobbyState;
    private String currentArenaString;
    private ArrayList<Player> whitePlayers;
    private ArrayList<Player> lightGrayPlayers;
    private ArrayList<Player> grayPlayers;
    private ArrayList<Player> blackPlayers;
    private ArrayList<Player> redPlayers;
    private ArrayList<Player> orangePlayers;
    private ArrayList<Player> yellowPlayers;
    private ArrayList<Player> limePlayers;
    private ArrayList<Player> greenPlayers;
    private ArrayList<Player> cyanPlayers;
    private ArrayList<Player> lightBluePlayers;
    private ArrayList<Player> bluePlayers;
    private ArrayList<Player> purplePlayers;
    private ArrayList<Player> magentaPlayers;
    private ArrayList<ZodiacPlayer> zodiacPlayers;
    private ArrayList<ArrayList<Player>> teams;
    private HashMap<Player, ItemStack> helmetVierge;
    private HashMap<Player, ItemStack> chestplateVierge;
    private HashMap<Player, ItemStack> leggingsVierge;
    private HashMap<Player, ItemStack> bootsVierge;

    public void setHelmetVierge(Player player, ItemStack item) {
        this.helmetVierge.put(player, item);
    }

    public void setChestplateVierge(Player player, ItemStack item) {
        this.chestplateVierge.put(player, item);
    }
    public void setLeggingsVierge(Player player, ItemStack item) {
        this.leggingsVierge.put(player, item);
    }
    public void setBootsVierge(Player player, ItemStack item) {
        this.bootsVierge.put(player, item);
    }

    public HashMap<Player, ItemStack> getHelmetVierge() {
        return helmetVierge;
    }
    public HashMap<Player, ItemStack> getChestplateVierge() {
        return chestplateVierge;
    }
    public HashMap<Player, ItemStack> getLeggingsVierge() {
        return leggingsVierge;
    }
    public HashMap<Player, ItemStack> getBootsVierge() {
        return bootsVierge;
    }


    public ArrayList<Player> getWhitePlayers() {
        return whitePlayers;
    }

    public ArrayList<Player> getLightGrayPlayers() {
        return lightGrayPlayers;
    }

    public ArrayList<Player> getGrayPlayers() {
        return grayPlayers;
    }

    public ArrayList<Player> getBlackPlayers() {
        return blackPlayers;
    }

    public ArrayList<Player> getRedPlayers() {
        return redPlayers;
    }

    public ArrayList<Player> getOrangePlayers() {
        return orangePlayers;
    }

    public ArrayList<Player> getYellowPlayers() {
        return yellowPlayers;
    }

    public ArrayList<Player> getLimePlayers() {
        return limePlayers;
    }

    public ArrayList<Player> getGreenPlayers() {
        return greenPlayers;
    }

    public ArrayList<Player> getCyanPlayers() {
        return cyanPlayers;
    }

    public ArrayList<Player> getLightBluePlayers() {
        return lightBluePlayers;
    }

    public ArrayList<Player> getBluePlayers() {
        return bluePlayers;
    }

    public ArrayList<Player> getPurplePlayers() {
        return purplePlayers;
    }

    public ArrayList<Player> getMagentaPlayers() {
        return magentaPlayers;
    }

    public Lobby(String id, String name, int maxPlayers) {//TODO add owner
        this.id = id;
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.players = new ArrayList<>();
        this.lobbyState=LobbyState.WAITING;
        this.currentArenaString = "-1";
        this.whitePlayers = new ArrayList<>();
        this.lightGrayPlayers = new ArrayList<>();
        this.grayPlayers = new ArrayList<>();
        this.blackPlayers = new ArrayList<>();
        this.redPlayers = new ArrayList<>();
        this.orangePlayers = new ArrayList<>();
        this.yellowPlayers = new ArrayList<>();
        this.limePlayers = new ArrayList<>();
        this.greenPlayers = new ArrayList<>();
        this.cyanPlayers = new ArrayList<>();
        this.lightBluePlayers = new ArrayList<>();
        this.bluePlayers = new ArrayList<>();
        this.purplePlayers = new ArrayList<>();
        this.magentaPlayers = new ArrayList<>();
        this.zodiacPlayers = new ArrayList<>();
        teams = new ArrayList<>();
        teams.add(whitePlayers);
        teams.add(lightGrayPlayers);
        teams.add(grayPlayers);
        teams.add(blackPlayers);
        teams.add(redPlayers);
        teams.add(orangePlayers);
        teams.add(yellowPlayers);
        teams.add(limePlayers);
        teams.add(greenPlayers);
        teams.add(cyanPlayers);
        teams.add(lightBluePlayers);
        teams.add(bluePlayers);
        teams.add(purplePlayers);
        teams.add(magentaPlayers);
        this.helmetVierge = new HashMap<>();
        this.chestplateVierge = new HashMap<>();
        this.leggingsVierge = new HashMap<>();
        this.bootsVierge = new HashMap<>();

    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        deletePlayerFromTeams(player);
        this.players.remove(player);
        this.zodiacPlayers.removeIf(p -> p.getPlayer().equals(player));
    }

    public void deletePlayerFromTeams(Player player) {
        whitePlayers.remove(player);
        lightGrayPlayers.remove(player);
        grayPlayers.remove(player);
        blackPlayers.remove(player);
        redPlayers.remove(player);
        orangePlayers.remove(player);
        yellowPlayers.remove(player);
        limePlayers.remove(player);
        greenPlayers.remove(player);
        cyanPlayers.remove(player);
        lightBluePlayers.remove(player);
        bluePlayers.remove(player);
        purplePlayers.remove(player);
        magentaPlayers.remove(player);
    }

    public ArrayList<ArrayList<Player>> getTeams() {
        return teams;
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public LobbyState getLobbyState() {
        return lobbyState;
    }

    public String getCurrentArenaString() {
        return currentArenaString;
    }

    public void setLobbyState(LobbyState lobbyState) {
        this.lobbyState = lobbyState;
    }

    public void setCurrentArenaString(String currentArenaString) {
        this.currentArenaString = currentArenaString;
    }
    public ArrayList<ZodiacPlayer> getZodiacPlayers() {
        return zodiacPlayers;
    }

    public void addWhitePlayer(Player player) {
        whitePlayers.add(player);
    }
    public void addLightGrayPlayer(Player player) {
        lightGrayPlayers.add(player);
    }
    public void addGrayPlayer(Player player) {
        grayPlayers.add(player);
    }
    public void addBlackPlayer(Player player) {
        blackPlayers.add(player);
    }
    public void addRedPlayer(Player player) {
        redPlayers.add(player);
    }
    public void addOrangePlayer(Player player) {
        orangePlayers.add(player);
    }
    public void addYellowPlayer(Player player) {
        yellowPlayers.add(player);
    }
    public void addLimePlayer(Player player) {
        limePlayers.add(player);
    }
    public void addGreenPlayer(Player player) {
        greenPlayers.add(player);
    }
    public void addCyanPlayer(Player player) {
        cyanPlayers.add(player);
    }
    public void addBluePlayer(Player player) {
        bluePlayers.add(player);
    }
    public void addLightBluePlayer(Player player) {
        lightBluePlayers.add(player);
    }
    public void addPurplePlayer(Player player) {
        purplePlayers.add(player);
    }
    public void addMagentaPlayer(Player player) {
        magentaPlayers.add(player);
    }

    public boolean everyoneInATeam(){
        return players.size()==(whitePlayers.size()+lightGrayPlayers.size()+ grayPlayers.size()+blackPlayers.size()+ redPlayers.size()+ orangePlayers.size()+yellowPlayers.size()+ limePlayers.size()+ greenPlayers.size()+
                cyanPlayers.size()+bluePlayers.size()+purplePlayers.size()+magentaPlayers.size()+ lightBluePlayers.size());
    }

    public void setZodiacFor(Player p, Zodiac zodiac) {
        for(ZodiacPlayer zp : zodiacPlayers) {
            if(zp.getPlayer().equals(p)) {
                zp.setZodiac(zodiac);
            }
        }
    }

    public void resetZodiacs() {
        this.zodiacPlayers.clear();
    }
}
