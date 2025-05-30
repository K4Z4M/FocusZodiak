package fr.kazam.focus.commands;

import fr.kazam.focus.Main;
import fr.kazam.focus.utils.Arena;
import fr.kazam.focus.utils.Lobby;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CreateArena implements CommandExecutor {

    Main plugin;

    public CreateArena(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length == 4){
            if(isInt(args[1]) && isInt(args[2]) && isInt(args[3])){
                File datasFile = new File(plugin.getDataFolder().getParentFile() + File.separator + "FocusDatas" + File.separator + "datas.yml");
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(datasFile);
                String id = String.valueOf(cfg.getConfigurationSection("arenas.").getKeys(false).size()+1);
                cfg.createSection("arenas."+id);
                cfg.createSection("arenas."+id+".arenaName");
                cfg.createSection("arenas."+id+".maxPlayers");
                cfg.createSection("arenas."+id+".time");
                cfg.createSection("arenas."+id+".hasNV");
                if(Integer.parseInt(args[1])>20){
                    cfg.set("arenas."+id+".maxPlayers", 20);
                }else{
                    cfg.set("arenas."+id+".maxPlayers", Integer.parseInt(args[1]));
                }
                cfg.set("arenas."+id+".arenaName", args[0]);
                cfg.set("arenas."+id+".time", args[3]);
                cfg.set("arenas."+id+".hasNV", args[2]);

                try {
                    commandSender.sendMessage(ChatColor.GREEN +"L'arène " + args[0] + " avec l'id "+id+" a été créé avec succès");
                    boolean hasNV = false;
                    if(Integer.parseInt(args[2])==0){
                        hasNV = true;
                    }
                    plugin.getArenas().add(new Arena(id,args[0],Integer.parseInt(args[1]),hasNV,Integer.parseInt(args[3])));
                    cfg.save(datasFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }else{
                commandSender.sendMessage(ChatColor.RED + "Il y a une erreur sur les arguments spécifiés");
            }
        }else{
            commandSender.sendMessage(ChatColor.RED + "Il n'y a pas 4 arguments");
        }

        return false;
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
