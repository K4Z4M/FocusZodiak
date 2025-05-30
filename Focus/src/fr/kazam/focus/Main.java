package fr.kazam.focus;

import fr.kazam.focus.commands.*;
import fr.kazam.focus.utils.Arena;
import fr.kazam.focus.utils.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main extends JavaPlugin {

    private static Main plugin;
    private ArrayList<Lobby> lobbies = new ArrayList<>();
    private ArrayList<Player> players = new ArrayList<>();
    private HashMap<Player, Integer> lobbyPlayers = new HashMap<>();
    private List<Arena> arenas = new LinkedList<>();
    private Team team = null;
    @Override
    public void onEnable() {
        plugin = this;
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new FocusPlayerListener(this),this);
        File pluginFile = new File(getDataFolder().getParentFile() + File.separator + "FocusDatas");
        File datasFile = new File(getDataFolder().getParentFile() + File.separator + "FocusDatas" + File.separator + "datas.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(datasFile);
        if(!pluginFile.exists()) {
            pluginFile.mkdir();
            try {
                datasFile.createNewFile();
                config.createSection("arenas");
                config.save(datasFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        initArenas();

        Objects.requireNonNull(getCommand("addArenaSpawn")).setExecutor(new AddArenaSpawn(this));
        Objects.requireNonNull(getCommand("addGlorypointSpawn")).setExecutor(new AddGloryPointSpawn(this));
        Objects.requireNonNull(getCommand("addShopSpawn")).setExecutor(new AddShopSpawn(this));
        Objects.requireNonNull(getCommand("createArena")).setExecutor(new CreateArena(this));
        Objects.requireNonNull(getCommand("createLobby")).setExecutor(new CreateLobby(this));
        Objects.requireNonNull(getCommand("joinLobby")).setExecutor(new JoinLobby(this));
        Objects.requireNonNull(getCommand("joinTeam")).setExecutor(new JoinTeam(this));
        Objects.requireNonNull(getCommand("startGame")).setExecutor(new StartGame(this));



    }

    private void initArenas() {
        File datasFile = new File(getDataFolder().getParentFile() + File.separator + "FocusDatas" + File.separator + "datas.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(datasFile);
        for(String key:cfg.getConfigurationSection("arenas.").getKeys(false)){
            ArrayList<Location> spawnLocations = new ArrayList<>();
            ArrayList<Location> shopLocation = new ArrayList<>();
            HashMap<Location,Integer> gPLocation = new HashMap<>();
            if(cfg.isSet("arenas."+key+".spawns") && cfg.isSet("arenas."+key+".shops") && cfg.isSet("arenas."+key+".hasNV") && cfg.isSet("arenas."+key+".time") && cfg.isSet("arenas."+key+".maxPlayers") && cfg.isSet("arenas."+key+".arenaName")){
                String arenaName = cfg.getString("arenas."+key+".arenaName");
                int time = cfg.getInt("arenas."+key+".time");
                boolean hasNV = cfg.getBoolean("arenas."+key+".hasNV");
                int maxPlayers = cfg.getInt("arenas."+key+".maxPlayers");
                for(int i = 0; i<cfg.getConfigurationSection("arenas."+key+".spawns").getKeys(false).size(); i++){
                    spawnLocations.add((Location) cfg.get("arenas."+key+".spawns.spawn"+i));
                }
                for(int i = 0; i<cfg.getConfigurationSection("arenas."+key+".shops").getKeys(false).size(); i++){
                    shopLocation.add((Location) cfg.get("arenas."+key+".shops.spawn"+i));
                }
                if(cfg.isSet("arenas."+key+".gps")){
                    for(int i = 0; i<cfg.getConfigurationSection("arenas."+key+".gps").getKeys(false).size(); i++){
                        gPLocation.put((Location) cfg.get("arenas."+key+".gps.spawn"+i),cfg.getInt("arenas."+key+".gps.spawnChance"+i));
                    }
                }
                arenas.add(new Arena(key,arenaName,maxPlayers,hasNV,time,spawnLocations,shopLocation,gPLocation));
            }
        }
    }

    @Override
    public void onDisable() {
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public List<Arena> getArenas() {
        return arenas;
    }

    public ArrayList<Lobby> getLobbies() {
        return lobbies;
    }

    public HashMap<Player, Integer> getLobbyPlayers() {
        return lobbyPlayers;
    }


    public ItemStack giveGloryPoints(int amount){
        ItemStack i = new ItemStack(Material.OMINOUS_TRIAL_KEY);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.LIGHT_PURPLE+ "Point de Gloire");
        im.setLore(Arrays.asList(ChatColor.LIGHT_PURPLE+ "Utilisez un "+ChatColor.GOLD+"Point de Gloire " + ChatColor.LIGHT_PURPLE+"sur un "+ChatColor.GOLD+"Crystal." ));
        i.setItemMeta(im);
        i.setAmount(amount);
        return i;
    }

}