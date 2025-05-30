package fr.kazam.focus.commands;

import fr.kazam.focus.Main;
import fr.kazam.focus.utils.Lobby;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CreateLobby implements CommandExecutor {

    private Main plugin;

    public CreateLobby(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(args.length == 2){
            if(isInt(args[1])){
                String id = String.valueOf(plugin.getLobbies().size());
                commandSender.sendMessage(ChatColor.GREEN +"Le Lobby " + args[0] + " avec l'id "+id+" a été créé avec succès");
                plugin.getLobbies().add(new Lobby(id, args[0], Integer.parseInt(args[1])));
            }else{
                commandSender.sendMessage(ChatColor.RED + "Veuillez spécifier un entier pour le nombre de joueurs");
            }
        }else{
            commandSender.sendMessage(ChatColor.RED + "Deux arguments sont attendus pour exécuter cette commande");
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
