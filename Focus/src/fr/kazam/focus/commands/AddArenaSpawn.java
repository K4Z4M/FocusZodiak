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

public class AddArenaSpawn implements CommandExecutor {

    Main plugin;
    public AddArenaSpawn(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length == 1) {
            if(isInt(args[0])){
                if(commandSender instanceof Player){
                    Player player = (Player) commandSender;
                    if(isReallyOnGround(player)){
                        File datasFile = new File(plugin.getDataFolder().getParentFile() + File.separator + "FocusDatas" + File.separator + "datas.yml");
                        FileConfiguration cfg = YamlConfiguration.loadConfiguration(datasFile);
                        Location l = player.getLocation();

                        for(String key:cfg.getConfigurationSection("arenas.").getKeys(false)){
                            if(args[0].equals(key)){
                                if(!cfg.isSet("arenas."+key+".spawns")){
                                    cfg.createSection("arenas."+key+".spawns");
                                }
                                if(cfg.getConfigurationSection("arenas."+key+".spawns").getKeys(false).size()<Integer.parseInt(cfg.getString("arenas."+key+".maxPlayers"))){
                                    int i = cfg.getConfigurationSection("arenas."+key+".spawns").getKeys(false).size();
                                    cfg.createSection("arenas."+key+".spawns.spawn"+i);
                                    cfg.set("arenas."+key+".spawns.spawn"+i,l);
                                    try {
                                        player.sendMessage(ChatColor.LIGHT_PURPLE+"Le point d'apparition "+(i+1)+ " a été créé dans l'arène d'ID "+key);
                                        for(Arena a:plugin.getArenas()){
                                            if(a.getId().equals(key)){
                                                a.addSpawnLocation(l);
                                            }
                                        }
                                        cfg.save(datasFile);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }else{
                                    commandSender.sendMessage(ChatColor.RED + "Vous avez atteint la limite maximale de spawn dans cette arène");
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
                commandSender.sendMessage(ChatColor.RED + "L'argument doit être un entier");
            }
        }else{
            commandSender.sendMessage(ChatColor.RED + "Un seul argument est attendu pour exécuter cette commande");
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
