package fr.kazam.focus.commands;

import fr.kazam.focus.Main;
import fr.kazam.focus.utils.Arena;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class AddGloryPointSpawn implements CommandExecutor {

    Main plugin;
    public AddGloryPointSpawn(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length == 2) {
            if(isInt(args[0]) && isInt(args[1])) {
                if(commandSender instanceof Player){
                    Player player = (Player) commandSender;
                    if(isReallyOnGround(player)){
                        File datasFile = new File(plugin.getDataFolder().getParentFile() + File.separator + "FocusDatas" + File.separator + "datas.yml");
                        FileConfiguration cfg = YamlConfiguration.loadConfiguration(datasFile);
                        Location l = player.getLocation();

                        for(String key:cfg.getConfigurationSection("arenas.").getKeys(false)){
                            if(args[0].equals(key)){
                                if(!cfg.isSet("arenas."+key+".gps")){
                                    cfg.createSection("arenas."+key+".gps");
                                }
                                int i = cfg.getConfigurationSection("arenas."+key+".gps").getKeys(false).size();
                                i=i/2;
                                cfg.createSection("arenas."+key+".gps.spawn"+i);
                                cfg.createSection("arenas."+key+".gps.spawnChance"+i);
                                cfg.set("arenas."+key+".gps.spawn"+i,l);
                                int chance = Integer.parseInt(args[1]);
                                if(chance<1){
                                    chance=1;
                                }
                                if(chance>100){
                                    chance=100;
                                }
                                cfg.set("arenas."+key+".gps.spawnChance"+i,chance);
                                player.sendMessage(ChatColor.LIGHT_PURPLE+"Le spawn de point de gloire "+(i+1)+ " a été créé dans l'arène d'ID "+key);
                                try {
                                    for(Arena a:plugin.getArenas()){
                                        if(a.getId().equals(key)){
                                            a.addGloryPointsLocation(l,chance);
                                        }
                                    }
                                    cfg.save(datasFile);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

                            }
                        }
                    }else{
                        commandSender.sendMessage(ChatColor.RED + "Vous devez être sur le sol pour lancer cette commande");
                    }
                }else{
                    commandSender.sendMessage(ChatColor.RED + "La commande doit être lancée par un joueur");
                }
            }else{
                commandSender.sendMessage(ChatColor.RED + "Les deux premiers arguments doivent être des entiers");
            }
        }else{
            commandSender.sendMessage(ChatColor.RED + "Deux arguments sont attendus pour exécuter cette commande");
        }
        return false;
    }

    public static boolean isReallyOnGround(Player p) {
        Location feet = p.getLocation().clone().subtract(0, 0.05, 0);
        Material below = feet.getBlock().getType();
        return below.isSolid();
    }


    private boolean isInt(String arg) {
        try{
            Integer.parseInt(arg);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
}
